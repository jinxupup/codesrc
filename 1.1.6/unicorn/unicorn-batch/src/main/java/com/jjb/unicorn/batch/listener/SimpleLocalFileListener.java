package com.jjb.unicorn.batch.listener;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleLocalFileListener implements LocalFileListener {

	Logger logger = LoggerFactory.getLogger(getClass());

	private String localPath;
	
	private String batchWorkDir;

	private File localFile;

	private FileChannel fileChannel;

	private RandomAccessFile randomAccessFile;

	public SimpleLocalFileListener(String localPath,String batchWorkDir) {
		this.localPath = localPath.replace("\\", "/");
		this.batchWorkDir = batchWorkDir.replace("\\", "/");
	}

	@Override
	public void createFile(File sourceFile) {

		String fileName = sourceFile.getPath();
		fileName = fileName.replace("\\", "/");
		
		String localFileName = fileName.replace(batchWorkDir, localPath);
		localFile = new File(localFileName);
		if (!localFile.getParentFile().exists()) {
			localFile.getParentFile().mkdirs();
		}
		if (localFile.exists()) {
			localFile.delete();
		}
		try {
			localFile.createNewFile();
		} catch (IOException e) {
			logger.error("创建本地文件失败!", e);
		}
	}

	@Override
	public void writeFile(FileChannel channel) {

		RandomAccessFile out = null;
		FileChannel localChannel = null;
		try {
			out = new RandomAccessFile(localFile, "rw");
			localChannel = out.getChannel();
			localChannel.position(localChannel.size());
			channel.transferTo(0, channel.size(), localChannel);
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

	public String getLocalPath() {
		return localPath;
	}

	public void setLocalPath(String localPath) {
		this.localPath = localPath;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jjb.unicorn.batch.listener.LocalFileListener#writeFile(java.
	 * nio.channels.FileChannel, int)
	 */
	@Override
	public void writeFile(FileChannel channel, long position, long count,
			int index) {
		writeFile(channel);
	}

	@Override
	public void createFile(File sourceFile, long position) {
		
		String fileName = sourceFile.getPath();
		fileName = fileName.replace("\\", "/");
		
		String localFileName = fileName.replace(batchWorkDir, localPath);
		
		localFile = new File(localFileName);
		if (!localFile.getParentFile().exists()) {
			localFile.getParentFile().mkdirs();
		}
		if (!localFile.exists()) {
			try {
				localFile.createNewFile();
				randomAccessFile = new RandomAccessFile(localFile, "rw");
				fileChannel = randomAccessFile.getChannel();
			} catch (IOException e) {
				logger.error("创建本地文件失败!", e);
			}
		} else {
			try {
				randomAccessFile = new RandomAccessFile(localFile, "rw");
				fileChannel = randomAccessFile.getChannel();
				if (position > 0) {
					fileChannel.truncate(position);
					fileChannel.position(position);
				}
			} catch (Exception e) {
				logger.error("查找本地文件并设置写入点失败!", e);
			}
		}
	}

	@Override
	public void writeFile(ByteBuffer byteBuffer) {
		try {
			byteBuffer.position(0);
			fileChannel.write(byteBuffer);
		} catch (IOException e) {
			logger.error("写入本地文件失败!", e);
		}
	}

	@Override
	public void close() {
		IOUtils.closeQuietly(fileChannel);
		IOUtils.closeQuietly(randomAccessFile);
	}
	
	public void writeFileFooter(StringBuilder builder) {
		RandomAccessFile out = null;
		FileChannel localChannel = null;
		try {
			out = new RandomAccessFile(localFile, "rw");
			localChannel = out.getChannel();
			localChannel.position(localChannel.size());
			localChannel.write(ByteBuffer.wrap(builder.toString().getBytes()));
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

	@Override
	public void writeFile(StringBuilder builder) {
		RandomAccessFile out = null;
		FileChannel localChannel = null;
		try {
			out = new RandomAccessFile(localFile, "rw");
			out.writeBytes(builder.toString());
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
}