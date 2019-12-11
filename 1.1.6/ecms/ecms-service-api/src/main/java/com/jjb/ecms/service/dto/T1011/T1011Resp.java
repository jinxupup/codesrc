package com.jjb.ecms.service.dto.T1011;

import com.jjb.ecms.service.dto.BasicResponse;
import lombok.Data;

import java.io.Serializable;

/**
  * @Description: 申请进度查询返回数据类
  * @author JYData-R&D-Big Star
  * @date 2016年11月23日 上午10:10:02
  * @version V1.0
 */
@Data
public class T1011Resp extends BasicResponse implements Serializable {

	private static final long serialVersionUID = 1L;
	public Boolean Result;//联机交易是否成功
}
