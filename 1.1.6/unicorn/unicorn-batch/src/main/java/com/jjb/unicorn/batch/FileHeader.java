package com.jjb.unicorn.batch;

import com.jjb.unicorn.facility.cstruct.CChar;


public class FileHeader
{
	@CChar(value=50, order=10)
	public String filename;
	
	/**
	 * 明细记录长度字节数，含行尾换行
	 */
	@CChar(value=6, order=20, zeroPadding = true)
	public int detailSize;
	
	@CChar(value=10, order=30, zeroPadding = true)
	public int recordCount;
}
