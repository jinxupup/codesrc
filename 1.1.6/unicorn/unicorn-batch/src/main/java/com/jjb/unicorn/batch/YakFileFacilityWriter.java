package com.jjb.unicorn.batch;

import java.io.IOException;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;

import com.jjb.unicorn.base.dao.BaseDao;


public class YakFileFacilityWriter<T> implements ItemWriter<T>
{

	@Autowired
	private BaseDao<T> baseDao;
	
	private String filename;
	
//	private CStruct<T> detailStruct;
	
//	private ByteBuffer detailBuffer;
	
	private Class<T> fileDetailClass;

	private String charset = "gbk";	//转换目标代码
	
	private String batchNumber;

	@SuppressWarnings("unchecked")  //理由如下
	@Override
	public void write(List<? extends T> items) throws Exception {
		for (T item : items)
		{
			if(item instanceof Iterable) 
			{
				//加入对Iterable类型的Item的支持，其实这么写是违反泛型的语义的。
				//这样List<? extends D> items就是错的，但为了开发方便，就这么处理了。
				for (T itemitem : (Iterable<T>)item)
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
	
	private void doWriteItem(T item) throws IOException {
//		YakFileItem entity = new YakFileItem();
//		entity.setBatchNumber(batchNumber);
//		entity.setFilename(filename);
//		
//		detailBuffer.clear();
//		detailStruct.writeByteBuffer(item, detailBuffer);
//		
//		entity.setLine(new String(detailBuffer.array(), charset));
//
//		baseDao.savePlain(entity);
	}
	
	@PostConstruct
	public void init()
	{
//		detailStruct = new CStruct<T>(fileDetailClass, charset);
//		detailBuffer = ByteBuffer.allocate(detailStruct.getByteLength());
	}

	public String getFilename() {
		return filename;
	}

	@Required
	public void setFilename(String filename) {
		this.filename = filename;
	}

	public Class<T> getFileDetailClass() {
		return fileDetailClass;
	}
	@Required
	public void setFileDetailClass(Class<T> fileDetailClass) {
		this.fileDetailClass = fileDetailClass;
	}

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	public String getBatchNumber() {
		return batchNumber;
	}

	public void setBatchNumber(String batchNumber) {
		this.batchNumber = batchNumber;
	}
}
