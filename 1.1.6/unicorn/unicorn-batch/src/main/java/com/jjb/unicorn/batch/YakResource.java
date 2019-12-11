package com.jjb.unicorn.batch;

import java.io.File;

import org.springframework.core.io.FileSystemResource;

/**
 * <p>Description: 简单封装{@link FileSystemResource}，为文件导出判断对应的日期类型</p>
 * @ClassName: YakResource
 * @author jjb
 * @date 2015年12月1日 下午12:17:58
 * @version 1.0
 */
public class YakResource extends FileSystemResource {

	/**
	 * <p>该文件所对应的日期类型</p>
	 */
	private String resourceDate;
	
	public YakResource(File file, String resourceDate) {
		super(file);
		this.resourceDate = resourceDate;
	}
	
	public YakResource(String path, String resourceDate) {
		super(path);
		this.resourceDate = resourceDate;
	}

	public String getResourceDate() {
		return resourceDate;
	}

	@Override
	public String toString() {
		return "计划获取日期为[" + resourceDate + "]的文件[" + super.getPath() + "]";
	}
	
}
