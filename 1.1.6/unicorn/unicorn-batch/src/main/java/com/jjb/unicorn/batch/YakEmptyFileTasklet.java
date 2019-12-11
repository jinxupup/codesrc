package com.jjb.unicorn.batch;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.core.io.Resource;

import com.jjb.unicorn.facility.cstruct.CStruct;


/**
 * <p>Description: Yak内部专用创造空文件Tasklet，默认写入配置资源定义只含文件头的空文件</p>
 * @ClassName: YakEmptyFileTasklet
 * @author jjb
 * @date 2016年1月14日 下午3:42:23
 * @version 1.0
 */
public class YakEmptyFileTasklet<Header extends FileHeader, Detail> implements Tasklet {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	private Resource targetResource;
	
	private Class<Header> fileHeaderClass;
	
	protected String charset = "gbk";
	
	private CStruct<Header> headerStruct;
	
	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		
		createFile();
		
		return RepeatStatus.FINISHED;
	}
	
	private void createFile() throws Exception {
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
		try {
			FileChannel mergeChannel = output.getChannel();
			inputFileHeader(mergeChannel, fileHeaderClass, charset);
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
	 * <p>目的：直接写入不包含任何文件内容的文件头</p>
	 * <p>承诺：不检查文件头格式</p>
	 * @param fileChannel
	 */
	private void inputFileHeader(FileChannel fileChannel, Class<Header> fileHeaderClass, String charset) {
		try {
			if(null != fileHeaderClass) {
				Header header = fileHeaderClass.newInstance();
				header.detailSize = 0;
				header.filename = targetResource.getFilename();
				header.recordCount = 0;
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
	
	@PostConstruct
	public void init() {
		if(null != fileHeaderClass) {
			headerStruct = new CStruct<Header>(fileHeaderClass, charset);
		}
	}

	public void setTargetResource(Resource targetResource) {
		this.targetResource = targetResource;
	}

	public void setFileHeaderClass(Class<Header> fileHeaderClass) {
		this.fileHeaderClass = fileHeaderClass;
	}
	
}
