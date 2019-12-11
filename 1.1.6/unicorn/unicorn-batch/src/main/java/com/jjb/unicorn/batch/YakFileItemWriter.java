package com.jjb.unicorn.batch;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.scope.context.StepSynchronizationManager;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.file.ResourceAwareItemWriterItemStream;
import org.springframework.batch.item.util.ExecutionContextUserSupport;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.core.io.Resource;
import org.springframework.util.Assert;

import com.jjb.unicorn.facility.cstruct.CStruct;


/**
 * 标准格式的文件输出writer，并且默认使用gbk格式编码.
 * 
 *  不指定fileHeaderClass则不输出文件头.
 * 
 * @author jjb
 *
 * @param <H>
 * @param <D>
 */
public class YakFileItemWriter<H extends FileHeader, D> extends ExecutionContextUserSupport 
				implements ResourceAwareItemWriterItemStream<D>, BeanNameAware {
	
	private static final String STATE_KEY = "state";

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	private Resource resource;
	
	private FileChannel fileChannel;
	
	private RandomAccessFile outputRandomAccessFile;
	
	private File outputTempFile;
	
	private State state;
	

	private Class<H> fileHeaderClass;
	
	private Class<D> fileDetailClass;

	/**
	 * 使用CStruct输出时的编码
	 */
	protected String charset = "gbk";

	private CStruct<D> detailStruct;
	private CStruct<H> headerStruct;
	private ByteBuffer detailBuffer;

	@Override
	public void open(ExecutionContext executionContext) throws ItemStreamException
	{
		String key = getKey(STATE_KEY);
		if (executionContext.containsKey(key)) {
			//减少java对象的串行化，State改为字符串，竖线分割，以便运行维护
			state = State.parseState((String)executionContext.get(key));
		} else {
			state = new State();
		}

		try
		{
			outputTempFile = new File(resource.getFile().getAbsolutePath() + ".yak");
			if (!outputTempFile.getParentFile().exists()) {
				outputTempFile.getParentFile().mkdirs();
			}
			
			outputRandomAccessFile = new RandomAccessFile(outputTempFile, "rw");
			fileChannel = outputRandomAccessFile.getChannel();
			
			//截取上次断点位置，如果是第一次运行，也可以这么处理，这样保证覆盖原有文件
			fileChannel.truncate(state.lastPosition);
			fileChannel.position(state.lastPosition);
			
			//放一个空文件头
			if (state.lastPosition == 0 && null != fileHeaderClass)
			{
				H header = fileHeaderClass.newInstance();
				ByteBuffer buffer = ByteBuffer.allocate(headerStruct.getByteLength() + 1);
				headerStruct.writeByteBuffer(header, buffer);
				buffer.put((byte)'\n');
				buffer.flip();
				fileChannel.write(buffer);
			}
		} 
		catch (Exception e)
		{
			logger.error("打开文件出错", e);
			throw new ItemStreamException(e);
		}
	}

	@Override
	public void update(ExecutionContext executionContext) throws ItemStreamException {
		Assert.notNull(state);
		
		try
		{
			if (fileChannel != null && fileChannel.isOpen())
			{
				state.lastPosition = fileChannel.position();
				//存入context
				//减少java对象的串行化，State改为字符串，竖线分割，以便运行维护
				executionContext.put(getKey(STATE_KEY), state.convertToStr());
			}
		}
		catch (IOException e)
		{
			throw new ItemStreamException("更新文件状态出错", e);
		}
		
	}

	@Override
	public void close() throws ItemStreamException
	{
		try
		{
			if (fileChannel != null && fileChannel.isOpen() && null != fileHeaderClass)
			{
				//最终更新文件头
				fileChannel.position(0);
				H header = fileHeaderClass.newInstance();
				//TODO 回调生成文件头
				header.detailSize = detailStruct.getByteLength() + 1;
				header.filename = resource.getFilename();
				header.recordCount = state.count;
				
				ByteBuffer buffer = ByteBuffer.allocate(headerStruct.getByteLength() + 1);
				headerStruct.writeByteBuffer(header, buffer);
				buffer.put((byte)'\n');
				buffer.flip();
				fileChannel.write(buffer);
				
			}
		}
		catch (IllegalAccessException e)
		{
			throw new ItemStreamException("写入文件头出错", e);
		}
		catch (InstantiationException e)
		{
			throw new ItemStreamException("写入文件头出错", e);
		}
		catch (IOException e) {
			throw new ItemStreamException("文件IO出错", e);
		}
		finally
		{
			IOUtils.closeQuietly(fileChannel);
			IOUtils.closeQuietly(outputRandomAccessFile);
		}

		if (StepSynchronizationManager.getContext().getStepExecution().getExitStatus().equals(ExitStatus.COMPLETED))
		{
			//成功处理，改文件名为最终文件
			try
			{
				if (resource.exists())
				{
					logger.warn("输出文件[{}]已存在，将被删除。", resource.getURL());
					resource.getFile().delete();
				}
				outputTempFile.renameTo(resource.getFile());
			} 
			catch (IOException e)
			{
				throw new ItemStreamException("最终文件改名失败", e);
			}
		}
	}

	@SuppressWarnings("unchecked")	//理由见下
	@Override
	public void write(List<? extends D> items) throws Exception {
		for (D item : items)
		{
			if (item instanceof Iterable)
			{
				//加入对Iterable类型的Item的支持，其实这么写是违反泛型的语义的。
				//这样List<? extends D> items就是错的，但为了开发方便，就这么处理了。
				for (D itemitem : (Iterable<D>)item)
				{
					doWriteItem(itemitem);
				}
			}
			else
			{
				doWriteItem(item);
			}
		}
	}

	private void doWriteItem(D item) throws IOException {
		detailBuffer.clear();
		
		detailStruct.writeByteBuffer(item, detailBuffer);
		detailBuffer.put((byte)'\n');
		detailBuffer.flip();
		fileChannel.write(detailBuffer);
		
		state.count++;
	}
	
	@PostConstruct
	public void init()
	{
		detailStruct = new CStruct<D>(fileDetailClass, charset);
		if(null != fileHeaderClass) {
			headerStruct = new CStruct<H>(fileHeaderClass, charset);
		}
		detailBuffer = ByteBuffer.allocate(detailStruct.getByteLength() + 1);
	}
	
	@Override
	public void setResource(Resource resource) {
		this.resource = resource;
	}
	
	@SuppressWarnings("serial")
	private static class State implements Serializable
	{
		private long lastPosition = 0;
		private int count = 0;
		
		/**
		 * 将State对象转换为字符串
		 * 减少java对象的串行化，State改为字符串，竖线分割，以便运行维护
		 */
		private String convertToStr() {
			return lastPosition + ":" + count;
		}
		
		/**
		 * 将字符串转换为State对象
		 * 减少java对象的串行化，State改为字符串，竖线分割，以便运行维护
		 */
		private static State parseState(String value) {
			State state = new State();
			if(null == value) {
				return state;
			}
			
			String [] values = value.trim().split(":");
			if(values.length == 2) {
				state.lastPosition = Long.parseLong(values[0].trim());
				state.count = Integer.parseInt(values[1].trim());
			}
			
			return state;
		}
	}
	
	/**
	 * test
	 * @param args
	 */
	public static void main(String[] args) {
		State state = new State();
		state.count = 123;
		state.lastPosition = 4567890123L;
		
		State state2 = State.parseState(state.convertToStr());
	}

	public void setFileHeaderClass(Class<H> fileHeaderClass) {
		this.fileHeaderClass = fileHeaderClass;
	}

	public void setFileDetailClass(Class<D> fileDetailClass) {
		this.fileDetailClass = fileDetailClass;
	}

	@Override
	public void setBeanName(String name) {
		//默认使用bean id作为name
		setName(name);
	}

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}
}
