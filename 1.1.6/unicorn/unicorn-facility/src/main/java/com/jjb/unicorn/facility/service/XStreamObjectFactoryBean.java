package com.jjb.unicorn.facility.service;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.core.io.Resource;

import com.thoughtworks.xstream.XStream;

/**
 * 指定通过xml文件建立一个Spring的Bean
 * @author LI.J
 *
 */
public class XStreamObjectFactoryBean<T> implements FactoryBean<T>
{
	private Resource xml;
	
	private T object;
	
	@Override
	public T getObject() throws Exception {
		
		return getTargetObject();
	}

	@Override
	public Class<?> getObjectType() {
		try
		{
			return getTargetObject().getClass();
		}
		catch (IOException e)
		{
			throw new IllegalArgumentException(e);
		}
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

	public Resource getXml() {
		return xml;
	}
	
	@SuppressWarnings("unchecked")
	private T getTargetObject() throws IOException
	{
		if (object == null)
		{
			XStream xStream = new XStream();
			InputStream is = xml.getInputStream();
			object = (T)xStream.fromXML(is);
			is.close();
		}
		return object;
	}

	@Required
	public void setXml(Resource xml) {
		this.xml = xml;
	}

}
