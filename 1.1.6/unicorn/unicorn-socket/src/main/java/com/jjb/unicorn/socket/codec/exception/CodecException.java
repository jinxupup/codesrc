package com.jjb.unicorn.socket.codec.exception;

/**
 * 编解码异常类，在变量格式定义错误或类型错误时抛出
 * 
 * @author BIG.CPU
 *
 */
@SuppressWarnings("serial")
public class CodecException extends Exception {

	public CodecException() {
		super();
	}
	
	public CodecException(String msg) {
		super(msg);
	}

	public CodecException(Throwable cause) {
		super(cause);
	}

	public CodecException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
