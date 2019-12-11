package com.jjb.unicorn.batch.partition;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
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

import com.jjb.unicorn.batch.FileHeader;
import com.jjb.unicorn.facility.cstruct.CStruct;



/**
 * <p>Description: 基于Yak内部实体文件拆分writer类，直接写成多个实体文件，最终一起合并，只能使用{@link YakLocalPartitionResource}资源类</p>
 * <p>场景：最终文件为e:/a/b/name将在本类中生成e:/a/b/name.yak/*最终使用合并步骤把name.yak文件夹下所有文件合并为name</p>
 * @ClassName: YakLocalPartitionResourceItemWriter
 * @author lixing
 * @date 2015年12月31日 下午3:23:49
 * @version 1.0
 */
public class YakLocalPartitionResourceItemWriter<H extends FileHeader, D> extends ExecutionContextUserSupport
													implements ResourceAwareItemWriterItemStream<D>, BeanNameAware {

	private Logger logger = LoggerFactory.getLogger(getClass());

	private final static String STATE_KEY = "state";
	
	private final static String COLON = ":";
	
	private final static String SUFFIX = ".yak";
	
	private final static String UNDERLINE = "_";
	
	private YakLocalPartitionResource<H, D> resource;

	private FileChannel fileChannel;
	/**
	 * <p>输出临时目录，如果合并默认合并此目录下所有文件</p>
	 */
	private File outputTempFile;

	/**
	 * <p>当前step写文件的真实路径名，位于outputTempFile文件夹下</p>
	 */
	private String outPutItemFileName;
	
	/**
	 * <p>当前step所写的临时文件，文件名为outPutItemFileName + .yak</p>
	 */
	private File outPutItemTempFile;
	
	/**
	 * <p>当前持有的文件</p>
	 */
	private RandomAccessFile outputRandomAccessFile;
	
	/**
	 * <p>当前step写入文件状态</p>
	 */
	private State state;

	private CStruct<D> detailStruct;
	
	private ByteBuffer detailBuffer;
	

	/**
	 * <p>Title: open</p> 
	 * <p>目的：step之前创建临时文件，这里step拆分已经完成</p>
	 * <p>承诺：这里进行临时文件夹、实体文件的创建</p>
	 * @param executionContext 执行上下文
	 * @throws ItemStreamException 
	 * @see org.springframework.batch.item.ItemStream#open(org.springframework.batch.item.ExecutionContext) 
	 */
	@Override
	public void open(ExecutionContext executionContext) throws ItemStreamException {
		String key = getKey(STATE_KEY);
		if (executionContext.containsKey(key)) {
			// 减少java对象的串行化，State改为字符串，冒号分割，以便运行维护
			state = State.parseState((String) executionContext.get(key));
		} else {
			state = new State();
		}

		try {
			outputTempFile = new File(resource.getFile().getAbsolutePath() + SUFFIX);
			if (!outputTempFile.exists()) {
				outputTempFile.mkdirs();
			}
			
			String stepName = StringUtils.replace(StepSynchronizationManager.getContext().getStepExecution().getStepName(), COLON , UNDERLINE);
			outPutItemFileName = outputTempFile.getAbsolutePath() + File.separator + resource.getFilename() + UNDERLINE + stepName;
			
			outPutItemTempFile = new File(outPutItemFileName + SUFFIX);
			if (!outPutItemTempFile.exists()) {
				outPutItemTempFile.createNewFile();
			}
			
			outputRandomAccessFile = new RandomAccessFile(outPutItemTempFile, "rw");
			fileChannel = outputRandomAccessFile.getChannel();

			// 截取上次断点位置，如果是第一次运行，也可以这么处理，这样保证覆盖原有文件
			fileChannel.truncate(state.lastPosition);
			fileChannel.position(state.lastPosition);

		} catch (Exception e) {
			logger.error("打开文件出错", e);
			throw new ItemStreamException(e);
		}
	}

	@Override
	public void update(ExecutionContext executionContext) throws ItemStreamException {
		Assert.notNull(state);

		try {
			if (fileChannel != null && fileChannel.isOpen()) {
				state.lastPosition = fileChannel.position();
				// 存入context
				// 减少java对象的串行化，State改为字符串，竖线分割，以便运行维护
				executionContext.put(getKey(STATE_KEY), state.convertToStr());
			}
		} catch (IOException e) {
			throw new ItemStreamException("更新文件状态出错", e);
		}

	}

	/**
	 * <p>Title: close</p> 
	 * <p>目的：关闭文件，把文件从之前的临时文件改名为实体文件，这里不对文件夹改名，依然在临时文件夹中处理</p>
	 * <p>承诺：如果存在需要写的文件，将删除存在的文件</p>
	 * @throws ItemStreamException 
	 * @see org.springframework.batch.item.ItemStream#close() 
	 */
	@Override
	public void close() throws ItemStreamException {
		
		IOUtils.closeQuietly(fileChannel);
		IOUtils.closeQuietly(outputRandomAccessFile);

		if (StepSynchronizationManager.getContext().getStepExecution().getExitStatus().equals(ExitStatus.COMPLETED)) {
			// 成功处理，改文件名为最终文件
			File outPutItemFile = new File(outPutItemFileName);
			if (outPutItemFile.exists()) {
				logger.warn("输出文件[{}]已存在，将被删除。", outPutItemFile.getAbsolutePath());
				outPutItemFile.delete();
			}
			outPutItemTempFile.renameTo(outPutItemFile);
		}
	}

	@SuppressWarnings("unchecked")
	// 理由见下
	@Override
	public void write(List<? extends D> items) throws Exception {
		for (D item : items) {
			if (item instanceof Iterable) {
				// 加入对Iterable类型的Item的支持，其实这么写是违反泛型的语义的。
				// 这样List<? extends D> items就是错的，但为了开发方便，就这么处理了。
				for (D itemitem : (Iterable<D>) item) {
					doWriteItem(itemitem);
				}
			} else {
				doWriteItem(item);
			}
		}
	}

	private void doWriteItem(D item) throws IOException {
		detailBuffer.clear();

		detailStruct.writeByteBuffer(item, detailBuffer);
		detailBuffer.put((byte) '\n');
		detailBuffer.flip();
		fileChannel.write(detailBuffer);

		state.count++;
	}

	@PostConstruct
	public void init() {
		detailStruct = new CStruct<D>(resource.getFileDetailClass(), resource.getCharset());
		detailBuffer = ByteBuffer.allocate(detailStruct.getByteLength() + 1);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void setResource(Resource resource) {
		this.resource = (YakLocalPartitionResource<H, D>) resource;
	}

	@SuppressWarnings("serial")
	private static class State implements Serializable {
		private long lastPosition = 0;
		private int count = 0;

		/**
		 * 将State对象转换为字符串 减少java对象的串行化，State改为字符串，竖线分割，以便运行维护
		 */
		private String convertToStr() {
			return lastPosition + COLON + count;
		}

		/**
		 * 将字符串转换为State对象 减少java对象的串行化，State改为字符串，竖线分割，以便运行维护
		 */
		private static State parseState(String value) {
			State state = new State();
			if (null == value) {
				return state;
			}

			String[] values = value.trim().split(COLON);
			if (values.length == 2) {
				state.lastPosition = Long.parseLong(values[0].trim());
				state.count = Integer.parseInt(values[1].trim());
			}

			return state;
		}
	}

	@Override
	public void setBeanName(String name) {
		// 默认使用bean id作为name
		setName(name);
	}
	
	public Class<D> getFileDetailClass() {
		return resource.getFileDetailClass();
	}

	public String getCharset() {
		return resource.getCharset();
	}

}
