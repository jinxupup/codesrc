package com.jjb.unicorn.batch.partition;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.text.MessageFormat;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.core.scope.context.StepSynchronizationManager;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.file.ResourceAwareItemReaderItemStream;
import org.springframework.batch.item.support.AbstractItemCountingItemStreamItemReader;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.Resource;

import com.jjb.unicorn.batch.FileHeader;
import com.jjb.unicorn.batch.LineItem;
import com.jjb.unicorn.facility.cstruct.CStruct;



/**
 * <p>Description: 基于Yak内部实体文件拆分Reader类，直接把一个文件拆分成多个文件供step使用，只能使用{@link YakLocalPartitionResource}资源类</p>
 * <p>场景：初始文件为e:/a/b/name将在本类中拆分成多个e:/a/b/name.yak/name_stepname</p>
 * @ClassName: YakLocalPartitionResourceItemWriter
 * @author lixing
 * @date 2015年12月31日 下午3:23:49
 * @version 1.0
 */
public class YakLocalPartitionResourceItemReader<H extends FileHeader, D> extends AbstractItemCountingItemStreamItemReader<LineItem<D>>
													implements ResourceAwareItemReaderItemStream<LineItem<D>>, Partitioner, BeanNameAware, InitializingBean {

	private Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * <p>内部默认换行符</p>
	 */
	protected final static String LINE_FEED = "\n";
	
	/**
	 * <p>临时文件夹后缀</p>
	 */
	private final static String SUFFIX = ".yak";
	
	/**
	 * <p>需要读取文件资源</p>
	 */
	protected YakLocalPartitionResource<H, D> resource;
	
	/**
	 * <p>读取文件头部处理器</p>
	 */
	protected CStruct<H> headerStruct;
	
	/**
	 * <p>读取文件体处理器</p>
	 */
	protected CStruct<D> detailStruct;
	
	/**
	 * <p>头部字节长度</p>
	 */
	protected int headerBytesLength;
	
	/**
	 * <p>每条记录字节长度</p>
	 */
	protected int detailBytesLength;
	
	private ByteBuffer detailBuffer;
	
	protected byte[] linefeedBuff;
	
	
	/**
	 * 跟踪当前处理的行号，仅用于显示，且首行数据为0行
	 */
	private int currentLineNo;
	
	/**
	 * <p>文件读取Stream</p>
	 */
	protected FileInputStream readFileInputStream;
	
	/**
	 * <p>文件读取通道</p>
	 */
	protected FileChannel readFileChannel;
	
	private int minPartitionSize = 1000;
	
	private int maxPartitionSize = Integer.MAX_VALUE;
	
	/**
	 * <p>Title: partition</p> 
	 * <p>目的：分区，计算各分区大小同时拆分文件</p>
	 * <p>承诺：</p>
	 * @param gridSize
	 * @return 
	 * @see org.springframework.batch.core.partition.support.Partitioner#partition(int) 
	 */
	@Override
	public Map<String, ExecutionContext> partition(int gridSize) {

		// 用于支持分区
		FileInputStream fis = null;
		try {
			// 取文件大小计算网格规模
			fis = new FileInputStream(resource.getFile());
			FileChannel channel = fis.getChannel();
			long size = channel.size();
			int total = (int) ((size - headerBytesLength) / detailBytesLength);
			int partitionSize = total / gridSize + 1; // 这里加1是为了避免出现因为整除而产生非常小的片的情况
			partitionSize = Math.max(partitionSize, minPartitionSize); // 最小分片大小
			partitionSize = Math.min(partitionSize, maxPartitionSize); // 最大分片大小

			logger.info("文件[{}]大小[{}]字节，总记录数[{}]，网格数[{}]，网格大小[{}]", resource.getFile().getAbsolutePath(), size, total, gridSize, partitionSize);

			// 开始分partition，注意最后一个partition不要漏行
			Map<String, ExecutionContext> result = new TreeMap<String, ExecutionContext>(); // 为了排序，使用TreeMap
			
			File splitFile = new File(resource.getFile().getParent() + File.separator + resource.getFilename() + SUFFIX);
			if(splitFile.exists()){
				logger.warn("拆分临时文件夹[{}]已存在，旧文件夹将被删除。", splitFile.getAbsolutePath());
				splitFile.delete();
			}
			
			splitFile.mkdirs();
			FileChannel inChannel = null;
			try {
				inChannel = fis.getChannel();

				// 这里实际上已经不参考 gridSize，而是根据partitionSize来计算
				for (int i = 0, restLines = total; restLines > 0; i++, restLines -= partitionSize) {
					String partName = MessageFormat.format("part{0,number,000}", i);

					int skip = i * partitionSize;

					ExecutionContext ec = new ExecutionContext();
					ec.putInt("skip", skip);
					ec.putInt("limit", partitionSize);
					ec.putString("name", partName);
					result.put(partName, ec);

					FileOutputStream output = null;
					try {
						File outputSplit = new File(splitFile.getAbsolutePath() + File.separator + resource.getFilename() + partName);
						if (outputSplit.exists()) {
							logger.warn("拆分临时文件[{}]已存在，旧文件将被删除。", outputSplit.getAbsolutePath());
							outputSplit.delete();
						}
						outputSplit.createNewFile();
						output = new FileOutputStream(outputSplit);
						FileChannel outChannel = output.getChannel();
						inChannel.transferTo(headerBytesLength + (skip * detailBytesLength), partitionSize * detailBytesLength, outChannel);
						//writeFile(inChannel, outChannel, outputSplit, skip, partitionSize);
						outChannel.close();
					} finally {
						if (null != output)
							output.close();
					}
				}
			} finally {
				if (null != inChannel)
					inChannel.close();
			}
			logger.info("文件[{}]最终切片网格数量[{}]", resource.getFile().getAbsolutePath(), result.size());

			return result;
		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		} finally {
			IOUtils.closeQuietly(fis);
		}
	}

	/**
	 * <p>目的：把通道中的所有字节写入文件</p>
	 * <p>承诺：</p>
	 * @param channel
	 * @param splitFile
	 */
	public void writeFile(FileChannel inChannel, FileChannel outChannel, File splitFile, long position, long count) {
		RandomAccessFile out = null;
		FileChannel localChannel = null;
		try {
			out = new RandomAccessFile(splitFile, "rw");
			localChannel = out.getChannel();
			inChannel.transferTo(position, count, outChannel);
			localChannel.close();
			out.close();
		} catch (Exception e) {
			logger.error("写本地文件失败!", e);
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					
				}
			}
			if (localChannel != null) {
				try {
					localChannel.close();
				} catch (IOException e) {
					
				}
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void setResource(Resource resource) {
		if (resource instanceof YakLocalPartitionResource<?, ?>) {
			this.resource = (YakLocalPartitionResource<H, D>) resource;
		} else {
			throw new IllegalArgumentException("本地Step切片必须使用YakLocalPartitionResource资源");
		}
		
	}

	@Override
	protected LineItem<D> doRead() throws Exception {
		LineItem<D> lineItem = new LineItem<D>();

		detailBuffer.clear();

		int read = readFileChannel.read(detailBuffer);
		if (read == -1)
			return null;	//文件读完
		if (read != detailBytesLength)
		{
			logger.warn("文件最后一行不完整，忽略。");
			return null;
		}

		detailBuffer.flip();
		D detail = detailStruct.parseByteBuffer(detailBuffer);
		
		// 验证换行
		detailBuffer.get(linefeedBuff);
		if (!LINE_FEED.equals(new String(linefeedBuff)))
			throw new IllegalArgumentException("换行符不正确:" + resource);

		lineItem.setLineNumber(currentLineNo++);
		lineItem.setLineObject(detail);
		lineItem.setValid(true);
		return lineItem;
	}

	@Override
	protected void doOpen() throws Exception {
		String stepName = StepSynchronizationManager.getContext().getStepExecution().getStepName();
		String[] stepNames = stepName.split(":");
		readFileInputStream = new FileInputStream(resource.getFile().getParent() + File.separator + resource.getFilename() + SUFFIX + File.separator + resource.getFilename() + stepNames[1]);
		readFileChannel = readFileInputStream.getChannel();
		currentLineNo = 0;
	}

	@Override
	protected void jumpToItem(int itemIndex) throws Exception {
		
		long position = (long) detailBytesLength * itemIndex;  //防超过2G文件
		
		logger.info("断点续批，跳转到第[{}]字节，第[{}]项。", position, itemIndex);
		
		// 直接跳过字节
		readFileChannel.position(position);

		currentLineNo = itemIndex;
	}
	
	@Override
	public void update(ExecutionContext executionContext) throws ItemStreamException {
		super.update(executionContext);
		logger.info("已处理到批量步骤[{}]文件的第[{}]项", StepSynchronizationManager.getContext().getStepExecution().getStepName(), currentLineNo);
	}
	
	@Override
	protected void doClose() throws Exception {
		IOUtils.closeQuietly(readFileInputStream);
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		detailStruct = new CStruct<D>(resource.getFileDetailClass(), resource.getCharset());
		if (resource.getFileHeaderClass() != null) {
			headerStruct = new CStruct<H>(resource.getFileHeaderClass(), resource.getCharset());
			headerBytesLength = headerStruct.getByteLength() + LINE_FEED.length();
		}
		detailBytesLength = detailStruct.getByteLength() + LINE_FEED.length();
		detailBuffer = ByteBuffer.allocate(detailBytesLength);
		linefeedBuff = new byte[LINE_FEED.length()];
	}
	
	@Override
	public void setBeanName(String name) {
		setName(name);
	}

	@BeforeStep
	void beforeStep(StepExecution stepExecution) {
		ExecutionContext ec = stepExecution.getExecutionContext();
		if (ec.containsKey("limit"))
			setMaxItemCount(ec.getInt("limit"));
	}
	
	/**
	 * 最小分区大小，避免因为数据集太小而分了过多的区，默认值为1000
	 * @param minPartitionSize
	 */
	public void setMinPartitionSize(int minPartitionSize) {
		this.minPartitionSize = minPartitionSize;
	}

	/**
	 * 最大分区大小，与线程池配合使用，避免分片过大导致单个步骤时间过长，在产生断批时浪费时间的问题。
	 * 默认值为 Integer.MAX_VALUE，即不限制
	 * @param maxPartitionSize
	 */
	public void setMaxPartitionSize(int maxPartitionSize) {
		this.maxPartitionSize = maxPartitionSize;
	}
	
}
