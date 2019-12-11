package com.jjb.unicorn.batch;

import java.util.Iterator;
import java.util.List;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

/**
 * 基于Key的无状态简单Reader，适合于带处理状态位的记录或是处理完后删除的记录。这个无法并发
 * @author jjb
 *
 * @param <KEY>
 * @param <INFO>
 */
public abstract class KeyBasedReader<KEY, INFO> implements ItemReader<INFO> {

	protected abstract List<KEY> loadKeys();
	
	protected abstract INFO loadItemByKey(KEY key);
	
	private Iterator<KEY> keyIterator;

	@Override
	public INFO read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
		
		if (keyIterator == null)
			keyIterator = loadKeys().iterator();

		if (!keyIterator.hasNext())
		{
			//清除引用
			keyIterator = null;
			return null;
		}
		return loadItemByKey(keyIterator.next());
	}

}
