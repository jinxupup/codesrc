package com.jjb.unicorn.batch.partition;

import java.io.File;

import com.jjb.unicorn.batch.FileHeader;
import com.jjb.unicorn.batch.YakResource;


/**
 * <p>Description: 本地Step分片使用资源，封装自{@link YakResource}，增加文件头和文件体的类型，此对象直接使用new关键字创建，不依赖Spring容器</p>
 * @ClassName: LocalPartitionResource
 * @author lixing
 * @date 2016年1月4日 下午1:11:24
 * @version 1.0
 */
public class YakLocalPartitionResource<Header extends FileHeader, Detail> extends YakResource {

	/**
	 * <p>文件头Class定义</p>
	 */
	protected Class<Header> fileHeaderClass;

	/**
	 * <p>文件内容Class定义</p>
	 */
	protected Class<Detail> fileDetailClass;
	
	/**
	 * <p>文件编码，默认为{@code GBK}</p>
	 */
	protected String charset = "GBK";
	
	public YakLocalPartitionResource(File file, String resourceDate) {
		super(file, resourceDate);
	}

	public Class<Header> getFileHeaderClass() {
		return fileHeaderClass;
	}

	public void setFileHeaderClass(Class<Header> fileHeaderClass) {
		this.fileHeaderClass = fileHeaderClass;
	}

	public Class<Detail> getFileDetailClass() {
		return fileDetailClass;
	}

	public void setFileDetailClass(Class<Detail> fileDetailClass) {
		this.fileDetailClass = fileDetailClass;
	}

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}
	
}
