package com.test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;

/**
 * 编码转换器
 * 问题描述：
 * 在用eclipse引入工程的时候，引入张孝祥老师java高级特性的代码，文件格式是ANSI格式的， 包含中文，如果把这些代码
 * 引入到工程里面，工程编码格式是utf-8,会出现乱码，所以在引入之前，先将所有的ANSI的文件，改成UTF-8编码格式
 * 于是写了一个工具类，将工程源代码路径给程序代码，该工具类会生成UTF-8文件编码格式的文件，存放到目的路径
 * @author D.H
 *
 */
public class Utf8ToGBK {
	public static void main(String[] args) {
		String srcDir = "D:\\workspace_1117-pboc\\cis-1.0.0";   //工程源代码
		String destDir = "D:\\workspace_1117-pboc\\cis-1.0.0-gbk";   //转换编码格式后的存放目录，该目录可以不存在
		createNewFile(srcDir, destDir);
		System.out.println("转换文件编码成功！！");
	}
	
	
	
	/**
	 * 生成新的文件夹
	 * @param srcDir 源目录，一定是文件夹，不能这个工具类就没意义了，单个文件，直接另存为就完事了
	 * @param destDir 目的目录
	 */
	public static void createNewFile(String srcDir, String destDir){
		File srcFile = new File(srcDir);
		if(srcFile.isDirectory()){
			String name = srcFile.getName();
			File directory = new File(destDir+"/" + name);
			directory.mkdirs();
			
			File[] files = srcFile.listFiles();
			if(files!=null && files.length>0){
				for(File f : files){
					createNewFile(f.getAbsolutePath(), directory.getAbsolutePath());
				}
			}
		}else if(srcFile.isFile()){
			String filename = srcFile.getName();
			if(filename!=null && !filename.equals("")){
				if(filename.endsWith(".java")){   //如果是java文件
					//改文件的编码
					covert(srcFile, new File(destDir + "/" + filename));
				}else{
					//其他编码，直接原样保存
					saveDirectly(srcFile, new File(destDir + "/" + filename));
				}
			}
		}else{
			System.out.println("不是正确的目录");
		}
	}
	
	/**
	 * 文件编码格式转换，转成UTF-8格式文件
	 * @param srcDir 
	 * @param destDir 
	 */
	private static String covert(File srcFile ,File destFile) {
		BufferedReader reader = null;
		BufferedWriter writer = null;//定义一个流
		try {
			InputStreamReader isr  = new InputStreamReader(new FileInputStream(srcFile), Charset.forName("UTF-8"));
			reader = new BufferedReader(isr);   //此时获取到的reader就是整个文件的缓存流
			FileOutputStream fos = new FileOutputStream(destFile);
			writer = new BufferedWriter(new OutputStreamWriter(fos, Charset.forName("GBK")));
			
			String str = "";
			while ((str = reader.readLine())!= null){ // 判断最后一行不存在，为空结束循环
				writer.write(str + "\r\n");
			};
			System.out.println("创建UTF-8格式文件：" + destFile.getName());
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try {
				if(writer!=null) writer.close();
				if(reader!=null) reader.close();
			} catch (Exception e2) {
			}
		}
		return null;
	}
	
	/**
	 * 针对不是.java结尾的文件，直接在目的目录保存文件
	 * @param srcFile
	 * @param destFile
	 * @return
	 */
	private static String saveDirectly(File srcFile ,File destFile) {
		BufferedReader reader = null;
		BufferedWriter writer = null;//定义一个流
		try {
			InputStreamReader isr  = new InputStreamReader(new FileInputStream(srcFile));
			reader = new BufferedReader(isr);   //此时获取到的reader就是整个文件的缓存流
			FileOutputStream fos = new FileOutputStream(destFile);
			writer = new BufferedWriter(new OutputStreamWriter(fos));
			
			String str = "";
			while ((str = reader.readLine())!= null){ // 判断最后一行不存在，为空结束循环
				writer.write(str + "\r\n");
			};
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try {
				if(writer!=null) writer.close();
				if(reader!=null) reader.close();
			} catch (Exception e2) {
			}
		}
		return null;
	}
	
}
