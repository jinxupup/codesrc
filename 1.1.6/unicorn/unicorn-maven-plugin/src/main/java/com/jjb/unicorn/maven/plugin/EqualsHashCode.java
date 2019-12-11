package com.jjb.unicorn.maven.plugin;

import java.text.MessageFormat;

import org.apache.ibatis.ibator.api.dom.java.Field;
import org.apache.ibatis.ibator.api.dom.java.FullyQualifiedJavaType;
import org.apache.ibatis.ibator.api.dom.java.JavaVisibility;
import org.apache.ibatis.ibator.api.dom.java.Method;
import org.apache.ibatis.ibator.api.dom.java.Parameter;
import org.apache.ibatis.ibator.api.dom.java.TopLevelClass;

import com.jjb.unicorn.facility.util.EqualsBuilder;
import com.jjb.unicorn.facility.util.HashCodeBuilder;


/**
 * 为生成的Key类添加equals和hashCode重载
 * 
 * @author jjb
 *
 */
public class EqualsHashCode extends AbstractGenerator {
	@Override
	public void afterKeyGenerated(TopLevelClass keyClass) {
	
		Method equals = new Method();
		keyClass.addMethod(equals);
		equals.setName("equals");
		equals.setVisibility(JavaVisibility.PUBLIC);
		equals.setReturnType(FullyQualifiedJavaType.getBooleanPrimitiveInstance());
		equals.addParameter(new Parameter(FullyQualifiedJavaType.getObjectInstance(), "obj"));
		equals.addAnnotation("@Override");
		keyClass.addImportedType(new FullyQualifiedJavaType(EqualsBuilder.class.getCanonicalName()));
		equals.addBodyLine("if (obj == null) { return false; }");
		equals.addBodyLine("if (obj == this) { return true; }");
		equals.addBodyLine("if (obj.getClass() != getClass()) {return false;}");
		equals.addBodyLine(MessageFormat.format("{0} rhs = ({0}) obj;", keyClass.getType().getShortName()));
		equals.addBodyLine("return new EqualsBuilder()");

		//public int hashCode() {
		//	// you pick a hard-coded, randomly chosen, non-zero, odd number
		//	// ideally different for each class
		//	return new HashCodeBuilder().append(name).append(age)
		//			.append(smoker).toHashCode();
		//}
		Method hashCode = new Method();
		keyClass.addMethod(hashCode);
		hashCode.setName("hashCode");
		hashCode.setVisibility(JavaVisibility.PUBLIC);
		hashCode.setReturnType(FullyQualifiedJavaType.getIntInstance());
		hashCode.addAnnotation("@Override");
		keyClass.addImportedType(new FullyQualifiedJavaType(HashCodeBuilder.class.getCanonicalName()));
		hashCode.addBodyLine("return new HashCodeBuilder()");

		for (Field field : keyClass.getFields())
		{
			equals.addBodyLine(MessageFormat.format("\t.append({0}, rhs.{0})", field.getName()));
			hashCode.addBodyLine(MessageFormat.format("\t.append({0})", field.getName()));
		}

		equals.addBodyLine("\t.isEquals();");
		hashCode.addBodyLine("\t.toHashCode();");
	}

}
