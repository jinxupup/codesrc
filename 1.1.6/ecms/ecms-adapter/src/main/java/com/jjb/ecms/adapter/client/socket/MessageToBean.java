package com.jjb.ecms.adapter.client.socket;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jjb.ecms.adapter.socket.ResponseType;
import com.jjb.ecms.adapter.socket.SocketServerProcessUtils;
import com.jjb.ecms.adapter.utils.TransBeanConvert;
import com.jjb.unicorn.facility.exception.ProcessException;
import com.jjb.unicorn.facility.util.StringUtils;

/**
 * @Description: 返回xml报文转换成bean
 * @author JYData-R&D-Big H.N
 * @date 2018年4月14日 下午4:59:09
 * @version V1.0
 */
@Component
public class MessageToBean {

	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private SocketServerProcessUtils sspUtils;

	/**
	 * @Author lixing
	 * @Description xml to bean
	 * @Date 2019/1/3 9:42
	 */
	public <T> T parseXML(T resp, String result, Integer subStrBeginIndex, String servId, String tokenId) throws Exception  {

		if(StringUtils.isNotBlank(result) && result.length() > 16){
			try {
				String rs = result;
				if(subStrBeginIndex!=null && subStrBeginIndex>0){
					rs = result.substring(subStrBeginIndex,result.length()-16);
				}
				Document document = DocumentHelper.parseText(rs);
				Element root = document.getRootElement();//根节点
				Element appResp = root.element("APP_HEAD");
				Element bodyResp = root.element("BODY");
				ResponseType res = new ResponseType();
				// 初始化返回报文
				res = sspUtils.initResp(res);
				if(appResp!=null){
					resp = TransBeanConvert.convertRequest(appResp, res,resp);
					resp = TransBeanConvert.convertRequest(bodyResp, res,resp);

				}
			} catch (DocumentException e) {
				logger.error("PID-["+tokenId+"]解析接收的交易["+servId+"]报文["+result+"]...截取的报文头长度["
						+subStrBeginIndex+"]发生异常",e);
				throw new ProcessException("解析接收的报文发生异常");
			}
		}
	   
	   return resp;
	}
}
