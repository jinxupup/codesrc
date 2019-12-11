/**
 * @author H.X
 */
package com.jjb.unicorn.batch;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.text.MessageFormat;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;

import com.jjb.unicorn.facility.cstruct.CStruct;

/**
 * 只能基于所有的源文件都不包含文件头并且文件数据结构是一致的情况下进行文件合并，
 * 合并后，如果指定了fileHeaderClass则输出新的文件头
 * 
 * @author jjb resources中的resource已经被改为了.yak，所以这里合并的时候修改目录名
 *         文件合并配置需要注意，不能将A+B合并到A或B，只能A+B=C
 */
public class FileMergeTask<H extends FileHeader, D> implements Tasklet {

	private Logger logger = LoggerFactory.getLogger(getClass());
	private List<Resource> resources;
	
	private Resource target;
	private boolean mergeFolder = true;// 默认合并方式为文件夹下的所有文件

//	@Value("#{env['databak']}")
//	private String localPath;
//	
//	@Value("#{env['batchWorkDirRoot']}")
//	private String batchWorkDir;
	
	@Value("#{env['retryInterval']?:5000}")
	private int retryInterval;

	private Class<H> fileHeaderClass;
	
	private Class<D> fileDetailClass;
	
	/**
	 * 使用CStruct输出时的编码
	 */
	protected String charset = "gbk";

	private CStruct<D> detailStruct;
	private CStruct<H> headerStruct;
	
	@PostConstruct
	public void init()
	{
		if(null != fileDetailClass) {
			detailStruct = new CStruct<D>(fileDetailClass, charset);
		}
		
		if(null != fileHeaderClass) {
			headerStruct = new CStruct<H>(fileHeaderClass, charset);
		}
	}
	
	@Override
	public RepeatStatus execute(StepContribution contribution,
			ChunkContext chunkContext) throws Exception {
		if (mergeFolder) {
			for (Resource r : resources) {
				merge(r);
			}
		} else {
			mergeFile();
		}
		return RepeatStatus.FINISHED;
	}

	/**
	 * 将多个文件合并为一个文件，不检查格式的一致性
	 * 
	 * @throws IOException
	 */
	private void mergeFile() throws IOException {

//		LocalFileListener fileListener =  new SimpleLocalFileListener(localPath,batchWorkDir);

		File to = target.getFile();

		String targetFileName = to.getAbsolutePath();
		String tmpFileName = to.getAbsoluteFile().getParent() + "/" + "tmp_"
				+ to.getName();

//		fileListener.createFile(to);

		File tmpFile = new File(tmpFileName);
		FileOutputStream os = new FileOutputStream(tmpFile);

		try {
			long dataSize = 0;
			FileChannel out = os.getChannel();
			
			//放一个空文件头
			inputFileHeader(out);
			
			for (Resource r : resources) {
				FileInputStream is = new FileInputStream(r.getFile());
				try {
					FileChannel in = is.getChannel();
					long fileSize = in.size();
					in.transferTo(0, fileSize, out);
					dataSize += fileSize;
//					fileListener.writeFile(in);
				} finally {
					if (is != null) {
						is.close();
					}
				}
			}
			
			updateFileHeaderAndClose(out, dataSize);
		} finally {
			if (os != null) {
				os.close();
			}
		}
		File target = new File(targetFileName);
		if (target.exists()) {
			logger.warn("File {} exists ,delete it", targetFileName);
			target.delete();
		}
		boolean flag = tmpFile.renameTo(target);
		if (!flag) {
			logger.error("文件最终命名失败!", new IOException("文件最终命名失败!"));
			throw new IOException("文件最终命名失败!");
		}
	}
	
	/**
	 * 写入目标文件之前先写入文件头占位
	 * @param fileChannel
	 */
	private void inputFileHeader(FileChannel fileChannel) {
		try {
			if(null != fileHeaderClass && null != fileDetailClass) {
				H header = fileHeaderClass.newInstance();
				ByteBuffer buffer = ByteBuffer.allocate(headerStruct.getByteLength() + 1);
				headerStruct.writeByteBuffer(header, buffer);
				buffer.put((byte)'\n');
				buffer.flip();
				fileChannel.write(buffer);
			}
		} catch (IllegalAccessException e) {
			throw new ItemStreamException("写入文件头出错", e);
		} catch (InstantiationException e) {
			throw new ItemStreamException("写入文件头出错", e);
		} catch (IOException e) {
			throw new ItemStreamException("文件IO出错", e);
		}
	}
	
	/**
	 * 在关闭目标文件之前更新文件头
	 * @param fileChannel	目标文件
	 * @param dataSize	不含文件头的数据大小,单位为byte
	 * @throws ItemStreamException
	 */
	private void updateFileHeaderAndClose(FileChannel fileChannel, long dataSize) throws ItemStreamException {
		//最终更新文件头
		try {
			if(null != fileHeaderClass && null != fileDetailClass) {
				fileChannel.position(0);
				H header = fileHeaderClass.newInstance();
				//回调生成文件头
				header.detailSize = detailStruct.getByteLength() + 1;
				header.filename = target.getFilename();
				
				long lineCount = 0;
				if(dataSize % header.detailSize == 0) {
					lineCount = dataSize / header.detailSize;
				} else {
					lineCount = (dataSize / header.detailSize) + 1;
					String errorMsg = MessageFormat.format(
							"合并前的文件有问题，文件大小不能被每行大小整除！文件大小:{0}, 每行记录大小:{1}, 目标文件:{2}",
							dataSize, header.detailSize, target.getFile().getAbsolutePath());
					logger.error(errorMsg);
					throw new ItemStreamException(errorMsg);
				}
				
				logger.info(MessageFormat.format("文件数据大小为:{0}, 每行记录大小为:{1}, 总共行数为:{2}, 目标文件:{3}", 
						dataSize, header.detailSize, lineCount, target.getFile().getAbsolutePath()));
				
				header.recordCount = (int)lineCount;
				
				ByteBuffer buffer = ByteBuffer.allocate(headerStruct.getByteLength() + 1);
				headerStruct.writeByteBuffer(header, buffer);
				buffer.put((byte)'\n');
				buffer.flip();
				fileChannel.write(buffer);
			}
		} catch (IllegalAccessException e) {
			throw new ItemStreamException("写入文件头出错", e);
		} catch (InstantiationException e) {
			throw new ItemStreamException("写入文件头出错", e);
		} catch (IOException e) {
			throw new ItemStreamException("文件IO出错", e);
		} finally {
			IOUtils.closeQuietly(fileChannel);
		}
	}

	/**
	 * 合并给定目录下的所有文件，新文件名为resource的文件名，目录为resource.yak
	 * 
	 * @param r
	 * @throws IOException
	 */
	public void merge(Resource r) throws IOException {

//		LocalFileListener fileListener = new SimpleLocalFileListener(localPath,batchWorkDir);

		File f = new File(r.getFile().getAbsoluteFile().getParent() + "/"
				+ r.getFilename() + ".yak");
		
		while(!f.exists()){
			try {
				logger.debug("File {} not exists ,waiting for {} seconds",f.getName(),retryInterval/1000);
				Thread.sleep(retryInterval);
			} catch (InterruptedException e) {
				logger.warn("Interrupted ",e);
			}
		}
		
		if (f.isDirectory()) {
			String targetFileName = r.getFile().getAbsolutePath();
			String tmpFileName = r.getFile().getAbsoluteFile().getParent()
					+ "/" + "tmp_" + r.getFilename();
			File merge = new File(tmpFileName);
			File target = new File(targetFileName);
			
//			fileListener.createFile(target);

			FileOutputStream os = new FileOutputStream(merge);
			FileInputStream is = null;
			try {
				long dataSize = 0;
				FileChannel mergeChannel = os.getChannel();
				
				//放一个空文件头
				inputFileHeader(mergeChannel);
				
				File[] files = f.listFiles();
				for (File t : files) {
					logger.info("merging {}", t.getName());
					is = new FileInputStream(t);
					FileChannel channel = is.getChannel();
					long fileSize = channel.size();
					channel.transferTo(0, fileSize, mergeChannel);
					dataSize += fileSize;
//					fileListener.writeFile(channel);
					channel.close();
				}
				
				updateFileHeaderAndClose(mergeChannel, dataSize);
			} finally {
				os.close();
				if (is != null) {
					is.close();
				}
			}
			
			if (target.exists()) {
				logger.warn("File {} exists ,delete it", targetFileName);
				target.delete();
			}
			boolean flag = merge.renameTo(target);
			if (!flag) {
				logger.error("文件最终命名失败!", new IOException("文件最终命名失败!"));
				throw new IOException("文件最终命名失败!");
			}
		}
	}

	public List<Resource> getResources() {
		return resources;
	}

	public void setResources(List<Resource> resources) {
		this.resources = resources;
	}

	public Resource getTarget() {
		return target;
	}

	public void setTarget(Resource target) {
		this.target = target;
	}

	public boolean isMergeFolder() {
		return mergeFolder;
	}

	public void setMergeFolder(boolean mergeFolder) {
		this.mergeFolder = mergeFolder;
	}

//	public String getLocalPath() {
//		return localPath;
//	}
//
//	public void setLocalPath(String localPath) {
//		this.localPath = localPath;
//	}

/*	public static void main(String[] args) throws IOException {

		testMeger();
//		testMegerResource();
	}

	private static void testMegerResource() throws IOException {
		// 准备测试文件
		List<File> files = new ArrayList<File>();
		files.add(new File("D:/temp/cps/2015/aaaa"));
		files.add(new File("D:/temp/cps/2015/aaaa.yak"));
		for (File file : files) {
			if (!file.exists()) {
				file.mkdirs();
			}
		}

		files.clear();
		files.add(new File("D:/temp/cps/2015/aaaa.yak/1111.txt"));
		files.add(new File("D:/temp/cps/2015/aaaa.yak/2222.txt"));
		files.add(new File("D:/temp/cps/2015/aaaa.yak/3333.txt"));
		for (File file : files) {
			if (!file.exists()) {
				file.createNewFile();
			}
		}

		// 组装测试数据
		List<Resource> list = new ArrayList<Resource>();
		list.add(new UrlResource("file:\\D:/temp/cps/2015/aaaa.yak/1111.txt"));
		list.add(new UrlResource("file:\\D:/temp/cps/2015/aaaa.yak/2222.txt"));
		list.add(new UrlResource("file:\\D:/temp/cps/2015/aaaa.yak/3333.txt"));

		FileMergeTask fmt = new FileMergeTask();
		fmt.setFileHeaderClass(com.jjb.unicorn.batch.FileHeader.class);
		fmt.setFileDetailClass(FileDetail.class);
		fmt.init();
//		fmt.setLocalPath("E:/temp/fengliming");
//		fmt.batchWorkDir="D:/temp/cps/2015";
		fmt.setTarget(new UrlResource("file:\\D:/temp/cps/2015/aaaa"));
		fmt.setResources(list);
		fmt.mergeFile();
	}
	
	public static class FileDetail
	{
		@CChar( value = 12, order = 100 )
		public String orgId;
	}

	private static void testMeger() throws IOException {
		// 准备测试文件
		List<File> files = new ArrayList<File>();
		files.add(new File("D:/temp/cps/2015/aaaa"));
		files.add(new File("D:/temp/cps/2015/aaaa.yak"));
		for (File file : files) {
			if (!file.exists()) {
				file.mkdirs();
			}
		}
		files.clear();
		files.add(new File("D:/temp/cps/2015/aaaa.yak/1111.txt"));
		files.add(new File("D:/temp/cps/2015/aaaa.yak/2222.txt"));
		files.add(new File("D:/temp/cps/2015/aaaa.yak/3333.txt"));
		for (File file : files) {
			if (!file.exists()) {
				file.createNewFile();
			}
		}

		// 组装测试数据
		List<Resource> list = new ArrayList<Resource>();
		list.add(new UrlResource("file:\\D:/temp/cps/2015/aaaa.yak/1111.txt"));
		list.add(new UrlResource("file:\\D:/temp/cps/2015/aaaa.yak/2222.txt"));
		list.add(new UrlResource("file:\\D:/temp/cps/2015/aaaa.yak/3333.txt"));

		FileMergeTask fmt = new FileMergeTask();
		fmt.setFileHeaderClass(com.jjb.unicorn.batch.FileHeader.class);
		fmt.setFileDetailClass(FileDetail.class);
		fmt.init();
//		fmt.setLocalPath("E:/temp/fengliming");
		fmt.setTarget(new UrlResource("file:\\D:/temp/cps/2015/aaaa"));
		fmt.setResources(list);
		fmt.mergeFile();
	}*/

	public Class<H> getFileHeaderClass() {
		return fileHeaderClass;
	}

	public void setFileHeaderClass(Class<H> fileHeaderClass) {
		this.fileHeaderClass = fileHeaderClass;
	}

	public Class<D> getFileDetailClass() {
		return fileDetailClass;
	}

	public void setFileDetailClass(Class<D> fileDetailClass) {
		this.fileDetailClass = fileDetailClass;
	}
}
