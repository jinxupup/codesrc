package com.jjb.ecms.adapter.socket.process;

import org.dom4j.Element;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.stereotype.Service;

import com.jjb.ecms.adapter.socket.ResponseType;
import com.jjb.ecms.service.constants.SysConstants;
import com.jjb.unicorn.facility.exception.ProcessException;
import com.jjb.unicorn.facility.util.StringUtils;

/**
  *@ClassName SwitchProcess
  *@Description 接口路由选择
  *@Author lixing
  *Date 2018/12/28 16:33
  *Version 1.0
  */
@Service
public class SwitchProcess implements BeanFactoryAware {

    private BeanFactory beanFactory = null;

    public String process(String reqType, Long start, Element appHead, Element reqEle, ResponseType res) throws ProcessException {

        String respServ =null;

        try {
            String serviceId =SysConstants.BEAN_NAME_PREFIX +reqType;
            if (!beanFactory.containsBean(serviceId)) {
            	if(serviceId!=null && serviceId.length()>10) {
            		throw new ProcessException(SysConstants.ERR_ESB_S002_CODE, SysConstants.ERR_ESB_S002_MES+"["+reqType+"]");
            	}else {
            		throw new ProcessException(SysConstants.ERRS002_CODE, SysConstants.ERRS002_MES+"["+reqType+"]");
            	}
            }
            IProcess process = (IProcess) this.getBean(serviceId);
            respServ = process.doProcess(start, appHead, reqEle, res);

        } catch (Exception e) {
            if(e instanceof ProcessException){
                ProcessException processexecption = (ProcessException) e;
                throw new ProcessException(processexecption.getErrorCode(),e.getMessage());
            }else if(e instanceof IllegalArgumentException){
                throw new ProcessException(SysConstants.ERRS002_CODE, SysConstants.ERRS002_MES+"["+reqType+"]");
            }else{
                throw new ProcessException(SysConstants.ERRS001_CODE,SysConstants.ERRS001_MES);
            }
        }

        return respServ;
    }


    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    private Object getBean(String name) {
        return beanFactory.getBean(name);
    }

}
