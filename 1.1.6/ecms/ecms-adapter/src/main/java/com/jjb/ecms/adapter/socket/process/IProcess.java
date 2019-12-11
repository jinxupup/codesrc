package com.jjb.ecms.adapter.socket.process;

import org.dom4j.Element;

import com.jjb.ecms.adapter.socket.ResponseType;

/**
 *@ClassName SwitchProcess
 *@Description 根据交易类型选择交易渠道
 *@Author lixing
 *Date 2018/12/28 16:33
 *Version 1.0
 */
public interface IProcess {

	/**
	 * @Author lixing
	 * @Description 实现该接口的时候类命名请以process+4位数字
	 * @Date 2018/12/28 17:46
	 */
	public String doProcess(Long start, Element appHead, Element reqEle, ResponseType res) throws Exception;

}
 