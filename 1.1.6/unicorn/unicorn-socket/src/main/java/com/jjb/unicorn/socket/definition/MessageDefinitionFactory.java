package com.jjb.unicorn.socket.definition;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.core.io.Resource;

import com.thoughtworks.xstream.XStream;

public class MessageDefinitionFactory implements FactoryBean<MessageDefinition> {

	private Resource messageDef;

	@Override
	public MessageDefinition getObject() throws Exception {
		XStream xStream = new XStream();
		xStream.alias("message", MessageDefinition.class);
		xStream.alias("field", FieldDefinition.class);
		xStream.alias("index", Integer.class);
		return (MessageDefinition) xStream.fromXML(messageDef.getInputStream());
	}

	public Resource getMessageDef() {
		return messageDef;
	}

	public void setMessageDef(Resource messageDef) {
		this.messageDef = messageDef;
	}

	@Override
	public Class<MessageDefinition> getObjectType() {
		return MessageDefinition.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}
}
