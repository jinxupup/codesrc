package com.jjb.ecms.adapter.utils.ccif;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import org.dom4j.Document;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

/*转化为标准格式的xml报文*/
public class Trans {
   public String formatXml(String inputXml) throws Exception{
	   SAXReader reader=new SAXReader();
	   Document document =reader.read(new StringReader(inputXml));
	   String requestXml=null;
	   XMLWriter writer=null;
	   if(document !=null){
		   try{
		   StringWriter stringWriter=new StringWriter();  
		   OutputFormat format=new OutputFormat(" ",true);
		   writer=new XMLWriter(stringWriter,format);
		   writer.write(document);
		   writer.flush();
		   requestXml=stringWriter.getBuffer().toString();
		   }finally{
			   if(writer !=null){
				   try{
					   writer.close();
				   }catch(IOException e){   
				   }
			   }
		   }
	   }
	   return requestXml;
   }
}
