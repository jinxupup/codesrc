package com.jjb.unicorn.batch;

import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import com.jjb.unicorn.base.dao.BaseDao;


public class JpaItemPersistWriter<T> implements ItemWriter<T> {
	
	@Autowired
	private BaseDao<T> baseDao;
	
	@Override
	public void write(List<? extends T> items) throws Exception
	{
		for (T item : items)
		{
			if (item instanceof List<?>)
			{
				for (Object sub : (List<?>)item) {
					baseDao.savePlain(sub);
				}
			}
			else {
				baseDao.savePlain(item);
			}
		}
	}

}
