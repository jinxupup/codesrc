package com.jjb.unicorn.batch.partition;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.text.MessageFormat;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.core.io.Resource;

import com.jjb.unicorn.batch.FileHeader;
import com.jjb.unicorn.facility.cstruct.CStruct;


/**
 * <p>Description: 完成Yak内部文件合并，默认为把临时文件夹(file.yak)中的所有文件合并为一个与文件夹同名的文件(file)，同时生成对应文件头</p>
 * @ClassName: YakLocalPartitionResourceMerge
 * @author jjb
 * @date 2015年12月31日 下午5:29:23
 * @version 1.0
 */
public class YakLocalPartitionResourceMerge<Header extends FileHeader, Detail> {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	/**
	 * <p>目的：从指定文件映射关系中把所有文件合并入指定文件，Resource只允许是YakLocalPartitionResource的子类</p>
	 * <p>承诺：映射关系必须为指定文件.yak</p>
	 * @param targetResource
	 * @throws IOException
	 */
	public void mergeFile(Resource target) throws IOException {
		
		if (!(target instanceof YakLocalPartitionResource<?, ?>)) {
			logger.warn("不是YakLocalPartitionResource类或其子类，不允许进行合并");
			throw new IOException("不允许合并的文件类型" + target.getClass().getCanonicalName() + "中断批量");
		}
		
		@SuppressWarnings("unchecked")
		YakLocalPartitionResource<Header, Detail> targetResource = (YakLocalPartitionResource<Header, Detail>) target;
		File tempFile = new File(targetResource.getFile().getAbsoluteFile().getParent() + File.separator + targetResource.getFilename() + ".yak");
		
		File mergeTargetFile = new File(targetResource.getFile().getAbsolutePath());
		File mergeTempFile = new File(targetResource.getFile().getAbsoluteFile().getParent() + File.separator + "tmp_" + targetResource.getFilename());
		if (mergeTempFile.exists()) {
			logger.warn("输出临时文件[{}]已存在，旧文件将被删除。", mergeTempFile.getAbsolutePath());
			mergeTempFile.delete();
		}
		if (!mergeTempFile.getParentFile().exists()) {
			logger.debug("输出文件[{}]目录不存在，创建目录。", mergeTempFile.getParent());
			mergeTempFile.getParentFile().mkdirs();
		}
		mergeTempFile.createNewFile();
		FileOutputStream output = new FileOutputStream(mergeTempFile);
		FileInputStream input = null;
		long dataSize = 0;
		try {
			FileChannel mergeChannel = output.getChannel();
			inputFileHeader(mergeChannel, targetResource.getFileHeaderClass(), targetResource.getCharset());
			if (!tempFile.exists() || !tempFile.isDirectory()) {
				logger.info("未找到需要合并的文件夹[{}]分片Step没有进行任何处理，自动生成空文件", tempFile.getAbsolutePath());
			} else {
				logger.info("合并文件夹[{}]下所有文件", tempFile.getAbsolutePath());
				File[] files = tempFile.listFiles();
				for (File tempfile : files) {
					logger.info("子文件[{}]合并入文件[{}]", tempfile.getName(), mergeTargetFile.getName());
					input = new FileInputStream(tempfile);
					FileChannel channel = input.getChannel();
					try {
						long fileSize = channel.size();
						channel.transferTo(0, channel.size(), mergeChannel);
						dataSize += fileSize;
					} finally {
						channel.close();
					}
				}
			}
			updateFileHeaderAndClose(targetResource, mergeChannel, dataSize);
			mergeChannel.close();
		} finally {
			output.close();
			if (null != input) {
				input.close();
			}
		}
		
		if (mergeTargetFile.exists()) {
			logger.warn("输出文件[{}]已存在，旧文件将被删除。", mergeTargetFile.getAbsolutePath());
			mergeTargetFile.delete();
		}
		
		if (!mergeTempFile.renameTo(mergeTargetFile)) {
			logger.error("文件最终命名失败", new IOException("文件最终命名失败"));
			throw new IOException("文件最终命名失败");
		}
	}

	
	/**
	 * <p>目的：写入目标文件之前先写入文件头占位</p>
	 * <p>承诺：不检查文件头格式</p>
	 * @param fileChannel
	 */
	private void inputFileHeader(FileChannel fileChannel, Class<Header> fileHeaderClass, String charset) {
		try {
			if(null != fileHeaderClass) {
				Header header = fileHeaderClass.newInstance();
				CStruct<Header> headerStruct = new CStruct<Header>(fileHeaderClass, charset);
				ByteBuffer buffer = ByteBuffer.allocate(headerStruct.getByteLength() + 1);
				headerStruct.writeByteBuffer(header, buffer);
				buffer.put((byte) '\n');
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
	private void updateFileHeaderAndClose(YakLocalPartitionResource<Header, Detail> target, FileChannel fileChannel, long dataSize) throws ItemStreamException {
		//最终更新文件头
		try {
			fileChannel.position(0);
			Header header = target.getFileHeaderClass().newInstance();
			//回调生成文件头
			CStruct<Detail> detailStruct = new CStruct<Detail>(target.getFileDetailClass(), target.getCharset());
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
				
			header.recordCount = (int) lineCount;
				
			CStruct<Header> headerStruct = new CStruct<Header>(target.getFileHeaderClass(), target.getCharset());
			ByteBuffer buffer = ByteBuffer.allocate(headerStruct.getByteLength() + 1);
			headerStruct.writeByteBuffer(header, buffer);
			buffer.put((byte) '\n');
			buffer.flip();
			fileChannel.write(buffer);
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

}
