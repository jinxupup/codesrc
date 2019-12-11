package com.jjb.unicorn.batch;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.batch.item.support.AbstractItemCountingItemStreamItemReader;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.jjb.unicorn.base.dao.BaseDao;
import com.jjb.unicorn.infrastructure.YakKeyContext;


/**
 * 基于主键模式的读取reader，注意，这里不限于数据库表，理论上可以是任何数据实体，Key与实体也不一定有数据库的关系。
 * 
 * 优化由于删数据而导致步骤结束，遗漏剩余数据的处理。
 * @author jjb
 * 
 * @author F.h
 * @since 2.7.0
 *
 * @param <KEY> 主键对象类型
 * @param <INFO> 处理实体类型
 */
public abstract class KeyBasedStreamReader<KEY,INFO> extends AbstractItemCountingItemStreamItemReader<INFO> implements ItemStreamReader<INFO>, Partitioner, BeanNameAware {
	
	private Logger logger = LoggerFactory.getLogger(getClass());

	private List<KEY> allKeys;
	
	private Iterator<KEY> keyIterator;
	
	private final String KEY_CONTEXT_KEY = "keyContextId";
	
	@Autowired
	private BaseDao<YakKeyContext> baseDao;
	
	private int minPartitionSize = 1000;
	
	/**
	 * 默认不限制分片大小
	 */
	private int maxPartitionSize = Integer.MAX_VALUE;
	
	protected abstract List<KEY> loadKeys();
	
	protected abstract INFO loadItemByKey(KEY key);
	
	@Override
	protected INFO doRead() throws Exception {
		if (!keyIterator.hasNext())
			return null;
		INFO info = loadItemByKey(keyIterator.next());
		while(info == null) {
			if (!keyIterator.hasNext())
				return null;
			setCurrentItemCount(getCurrentItemCount() + 1);
			info = loadItemByKey(keyIterator.next());
		}
		return info;
	}

	@Override
	protected void doOpen() throws Exception
	{
		//allKeys应该在beforeStep里加载或直接从ExecutionContext读取

		keyIterator = allKeys.iterator();
		
	}
	
	@Override
	public void update(ExecutionContext executionContext) throws ItemStreamException {
		super.update(executionContext);
		
		//仅为打日志
		logger.info("已处理到游标的第{}项", getCurrentItemCount());
	}
	
	@Override
	protected void jumpToItem(int itemIndex) throws Exception {
		keyIterator = allKeys.subList(itemIndex, allKeys.size()).iterator();
	}

	@Override
	protected void doClose() throws Exception {
		//清空缓存，以防万一
		keyIterator = null;
		allKeys = null;
	}
	
	@Override
	public Map<String, ExecutionContext> partition(int gridSize)
	{
		//用于支持分区
		List<KEY> keys = loadKeys();

		//取文件大小计算网格规模
		int total = keys.size();
		
		int partitionSize = total / gridSize + 1;	//这里加1是为了避免出现因为整除而漏项的情况
		partitionSize = Math.max(partitionSize, minPartitionSize);
		partitionSize = Math.min(partitionSize, maxPartitionSize);

		logger.info("总记录数[{}]，网格数[{}]，网格大小[{}]", total, gridSize, partitionSize);
		
		//开始分partition，注意最后一个partition不要漏行
		Map<String, ExecutionContext> result = new TreeMap<String, ExecutionContext>();	//为了排序，使用TreeMap
		int rest = total;
		int i = 0;
		while (rest > 0)
		{
			ExecutionContext ec = new ExecutionContext();
			ArrayList<KEY> subList = new ArrayList<KEY>(keys.subList(i * partitionSize, Math.min((i + 1) * partitionSize, total)));	//动态subList不可串行化
			
			YakKeyContext context = createNewKeyContext(subList);
			
			ec.putLong("keyContextId", context.getContextId());

			result.put(MessageFormat.format("part{0,number,000}", i), ec); 
			rest -= partitionSize;
			i++;
		}
		logger.info("实际网格数量[{}]", result.size());
		
		return result;
	}

	@BeforeStep
	void beforeStep(StepExecution stepExecution)
	{
		//由于继承了 AbstractItemCountingItemStreamReader，doOpen里拿不到ExecutionContext，所以只能在这里处理
		//这里的处理在事务之外
		ExecutionContext ec = stepExecution.getExecutionContext();
		if (ec.containsKey(KEY_CONTEXT_KEY))
		{
			//如果有Partitioner或断点续批
			long contextId = ec.getLong(KEY_CONTEXT_KEY);
			YakKeyContext param = new YakKeyContext();
			param.setContextId(Integer.parseInt(String.valueOf(contextId)));
			YakKeyContext context = baseDao.queryByKey(param);
			allKeys = byteToList(context.getKeyList());
		}
		else
		{
			//如果没有Partitioner，就在这里把所有主键加载，并且写入ExecutionContext
			//但这个ExecutionContext会在第一次update时写入数据库，这个操作是在chunk的事务之外的
			allKeys = loadKeys();
			ec.putLong(KEY_CONTEXT_KEY, createNewKeyContext(new ArrayList<KEY>(allKeys)).getContextId());
			logger.info("加载[{}]条主键信息", allKeys.size());
		}
	}

	@Transactional
	private YakKeyContext createNewKeyContext(ArrayList<KEY> subList) {
		YakKeyContext context = new YakKeyContext();
		
		context.setKeyList(listToArray(subList));
		baseDao.save(context);
		return context;
	}
	
	private byte[] listToArray (ArrayList<KEY> subList) {
		Object []objs = subList.toArray();
		ByteArrayOutputStream byteArray = new ByteArrayOutputStream ();
		ObjectOutputStream out = null;
		try {
			out = new ObjectOutputStream(byteArray);
			out.writeObject(objs);
		} catch (IOException e) {
			return new byte [0];
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					;
				}
			}
			
			if (byteArray != null) {
				try {
					byteArray.close();
				} catch (IOException e) {
					;
				}
			}
		}
		
		return byteArray.toByteArray();
	}
	
	
	@SuppressWarnings("unchecked")
	private List<KEY> byteToList (byte []buff) {
		ByteArrayInputStream byteArray = new ByteArrayInputStream(buff);
		ObjectInputStream out = null;
		
		try {
			out = new ObjectInputStream (byteArray);
			Object []objs = (Object[]) out.readObject();
			
			return (List<KEY>) Arrays.asList(objs);
			
		} catch (Exception e) {
			return new ArrayList<KEY> ();
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					;
				}
			}
			
			if (byteArray != null) {
				try {
					byteArray.close();
				} catch (IOException e) {
					;
				}
			}
		}
	}
	
	
	@Override
	public void setBeanName(String name) {
		//默认使用bean id作为name
		setName(name);
	}

	public int getMinPartitionSize() {
		return minPartitionSize;
	}

	public void setMinPartitionSize(int minPartitionSize) {
		this.minPartitionSize = minPartitionSize;
	}

	public int getMaxPartitionSize() {
		return maxPartitionSize;
	}

	public void setMaxPartitionSize(int maxPartitionSize) {
		this.maxPartitionSize = maxPartitionSize;
	}

}
