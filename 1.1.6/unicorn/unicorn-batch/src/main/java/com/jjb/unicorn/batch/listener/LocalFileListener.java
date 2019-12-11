package com.jjb.unicorn.batch.listener;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public interface LocalFileListener {

	/**
	 * 创建本地文件
	 * @param sourceFile 目标文件
	 * @param floor 根据目标文件产生本地文件时保留的文件层级数(从下往上)
	 * 		   例如: 0 表示只取文件名;1 表示取文件父目录
	 */
	public void createFile(File sourceFile);
	
	/**
	 * 创建/找到本地文件,并根据position的值找到准备写入的位置
	 * @param sourceFile
	 * @param position
	 * @param floor
	 */
	public void createFile(File sourceFile,long position);
	
	/**
	 * 将文件输出管道对接到本地文件中
	 * @param channel
	 * @throws IOException 
	 */
	public void writeFile(FileChannel channel);
	
	public void writeFile(StringBuilder builder);
	
	public void writeFile(ByteBuffer byteBuffer);
	
	public void close();
	
	/**
	 * @param channel
	 * @param index
	 */
	public void writeFile(FileChannel channel,long position,long count,int index);
}