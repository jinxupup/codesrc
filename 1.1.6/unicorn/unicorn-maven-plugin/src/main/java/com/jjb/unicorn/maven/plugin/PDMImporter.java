package com.jjb.unicorn.maven.plugin;

import java.io.File;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.ibator.api.dom.java.FullyQualifiedJavaType;
import org.apache.maven.plugin.logging.Log;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import com.jjb.unicorn.maven.meta.Column;
import com.jjb.unicorn.maven.meta.Database;
import com.jjb.unicorn.maven.meta.Domain;
import com.jjb.unicorn.maven.meta.Table;


public class PDMImporter
{
	private Log logger;
	
	public PDMImporter(Log logger)
	{
		this.logger = logger;
	}

	@SuppressWarnings("unchecked")
	protected Database doImport(File pdmSource, String tablePattern)
	{
		try
		{
			Database result = new Database();

			// 解析源文件
			SAXReader sar = new SAXReader();
			Document docSource = sar.read(pdmSource);
			
			// 首先处理domain
			Map<String, Domain> domainMap = new HashMap<String, Domain>();
			for (Element nodeDomain : (List<Element>) docSource.selectNodes("//c:Domains/o:PhysicalDomain"))
			{
				Node nodeCode = nodeDomain.selectSingleNode("a:Code");
				Node nodeName = nodeDomain.selectSingleNode("a:Name");
				Node nodeLength = nodeDomain.selectSingleNode("a:Length");
				Node nodeDataType = nodeDomain.selectSingleNode("a:DataType");
				Node nodePrecision = nodeDomain.selectSingleNode("a:Precision");
				Node nodeValues = nodeDomain.selectSingleNode("a:ListOfValues");

				if (nodeValues == null)
					continue;	//跳过那些没有值列表的domain

				Domain domain = new Domain();
				int length = 0;
				int scale = 0;
				if (nodeCode != null) domain.setCode(nodeCode.getText());
				if (nodeName != null) domain.setName(nodeName.getText());
				if (nodeLength != null)
					length = Integer.parseInt(nodeLength.getText());
				if (nodePrecision != null)
					scale = Integer.parseInt(nodePrecision.getText());
				if (nodeDataType != null)
				{
					domain.setDbType(nodeDataType.getText());
					domain.setType(resolveColumnType(domain.getDbType(), length, scale));
				}
				
				if (nodeValues != null)
				{
					String text = nodeValues.getText();
					if (StringUtils.isNotBlank(text))
					{
						LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
						String lines[] = StringUtils.split(text, "\r\n");
						for (String line : lines)
						{
							if (StringUtils.isNotBlank(line))
							{
								String kv[] = StringUtils.split(line, "\t");
								if (kv.length != 2)
									throw new IllegalArgumentException("("+domain.getCode()+")无效键值对:"+line);
								map.put(kv[0], kv[1]);
							}
						}
						domain.setValueMap(map);
					}
				}
				domainMap.put(nodeDomain.attributeValue("Id"), domain);
				result.getDomains().add(domain);
			}

			// 取所有表
			Map<String, Column> columnIdMap = new HashMap<String, Column>();
			Map<String, Table> tableIdMap = new HashMap<String, Table>();
			for (Element nodeTable : (List<Element>) docSource.selectNodes("//c:Tables/o:Table"))
			{
				Table table = new Table();
				table.setDbName(nodeTable.selectSingleNode("a:Code").getText());
				table.setTextName(nodeTable.selectSingleNode("a:Name").getText());
			
				logger.info("处理 " + table.getDbName());
				Node comment = nodeTable.selectSingleNode("a:Comment");
				if (comment != null)
					table.setDescription(comment.getText());

				if (!Pattern.matches(tablePattern, table.getDbName()))
					continue;
				
				tableIdMap.put(nodeTable.attributeValue("Id"), table);

				for (Element nodeColumn : (List<Element>) nodeTable.selectNodes("c:Columns/o:Column"))
				{
					processColumns(domainMap, columnIdMap, table, nodeColumn);
				}

				// 处理主键
				Element nodePrimaryKeyRef = (Element) nodeTable.selectSingleNode("c:PrimaryKey/o:Key");
				if (nodePrimaryKeyRef == null)
				{
					logger.warn(MessageFormat.format("主键没找到[{0}], 跳过该表", table.getDbName()));
					continue;
				}
				String ref = nodePrimaryKeyRef.attribute("Ref").getText();
				List<Element> pkColumns = (List<Element>) nodeTable.selectNodes("c:Keys/o:Key[@Id=\"" + ref + "\"]/c:Key.Columns/o:Column");
				for (Element pkColumn : pkColumns)
				{
					table.getPrimaryKeyColumns().add(columnIdMap.get(pkColumn.attributeValue("Ref")));
				}
				
				// 处理索引
				for (Element nodeIndex : (List<Element>) nodeTable.selectNodes("c:Indexes/o:Index"))
				{
					List<Column> indexColumns = new ArrayList<Column>();
					for (Element nodeIndexColumn : (List<Element>) nodeIndex.selectNodes("c:IndexColumns/o:IndexColumn/c:Column/o:Column"))
					{
						String columnRef = nodeIndexColumn.attributeValue("Ref");
						Column col = (Column)columnIdMap.get(columnRef);
						indexColumns.add(col);
					}
					table.getIndexes().add(indexColumns);
				}
				
				
				result.getTables().add(table);
			}
			
			// 处理视图
			for (Element nodeView : (List<Element>) docSource.selectNodes("//c:Views/o:View"))
			{
				Table table = new Table();
				table.setDbName(nodeView.selectSingleNode("a:Code").getText());
				
				if (!Pattern.matches(tablePattern, table.getDbName()))
					continue;
				
				Element nodeRefTable = (Element)nodeView.selectSingleNode("c:View.Tables/o:Table");
				if (nodeRefTable == null)
				{
					logger.warn(MessageFormat.format("视图[{0}]使用了显式字段列表，暂时无法生成定义", table.getDbName()));
					continue;
				}
				
				Table refTable = tableIdMap.get(nodeRefTable.attributeValue("Ref"));

				//从参考表中拷过来
				table.setColumns(refTable.getColumns());
				table.setPrimaryKeyColumns(refTable.getColumns());
				table.setIndexes(refTable.getIndexes());
				
				tableIdMap.put(nodeView.attributeValue("Id"), table);
				
				result.getTables().add(table);
			}
			
			
			// 处理关系
//			List<Element> references = (List<Element>) docSource.selectNodes("//c:References/o:Reference");
//			for (Element r : references)
//			{
//				Relationship rel = new Relationship();
//				Element table1 = (Element) r.selectSingleNode("c:ParentTable/o:Table");
//				Element table2 = (Element) r.selectSingleNode("c:ChildTable/o:Table");
//				String id1 = table1.attributeValue("Ref");
//				String id2 = table2.attributeValue("Ref");
//				if (!tableIdMap.containsKey(id1) || !tableIdMap.containsKey(id2))
//					continue;		//��������Ϊû����������ı�
//				rel.setParent(tableIdMap.get(id1));
//				rel.setChild(tableIdMap.get(id2));
//				for (Element rj : (List<Element>) r.selectNodes("c:Joins/o:ReferenceJoin"))
//				{
//					JoinColumn jc = new JoinColumn();
//					Element col1 = (Element) rj.selectSingleNode("c:Object1/o:Column");
//					Element col2 = (Element) rj.selectSingleNode("c:Object2/o:Column");
//					id1 = col1.attributeValue("Ref");
//					id2 = col2.attributeValue("Ref");
//					jc.setPk(columnIdMap.get(id1));
//					jc.setFk(columnIdMap.get(id2));
//
//					rel.getJoinColumns().add(jc);
//				}
//
//				result.getRelationships().add(rel);
//			}
			
			// 处理sequence
			List<Element> sequenceCodes = (List<Element>) docSource.selectNodes("//c:Sequences/o:Sequence/a:Code");
			for (Element s : sequenceCodes)
			{
				result.getSequences().add(s.getText());
			}
			
			return result;
		}
		catch (Throwable t)
		{
			throw new IllegalArgumentException(t);
		}
	}

	private void processColumns(Map<String, Domain> domainMap,
			Map<String, Column> columnIdMap, Table table, Element nodeColumn) {
		Node nodeCode = nodeColumn.selectSingleNode("a:Code");
		Node nodeName = nodeColumn.selectSingleNode("a:Name");
		Node nodeDescription = nodeColumn.selectSingleNode("a:Comment");
		Node nodeLength = nodeColumn.selectSingleNode("a:Length");
		Node nodeDataType = nodeColumn.selectSingleNode("a:DataType");
		Node nodePrecision = nodeColumn.selectSingleNode("a:Precision");
		Column col = new Column();
		if (nodeCode != null) col.setDbName(nodeCode.getText());
		if (nodeName != null) col.setTextName(nodeName.getText());
		if (nodeDescription != null) col.setDescription(nodeDescription.getText());
		if (nodeLength != null) col.setLength(Integer.parseInt(nodeLength.getText()));
		if (nodePrecision != null) col.setScale(Integer.parseInt(nodePrecision.getText()));
		if (nodeDataType != null)
		{
			String dbType = nodeDataType.getText();
			col.setTemporal(dbType);
			col.setLob(isLobType(dbType));
			if (dbType.startsWith("VARCHAR")) {
				dbType= dbType.substring(0, 7);
			} else if (dbType.startsWith("CHAR")) {
				dbType= dbType.substring(0, 4);
			}
			col.setDbType(dbType);
			col.setJavaType(resolveColumnType(dbType, col.getLength(), col.getScale()));
		}

		columnIdMap.put(nodeColumn.attributeValue("Id"), col);
		
		//处理domain
		Element nodeDomain = (Element) nodeColumn.selectSingleNode("c:Domain/o:PhysicalDomain");
		if (nodeDomain != null)
			col.setDomain(domainMap.get(nodeDomain.attributeValue("Ref")));

		table.getColumns().add(col);
	}

	private FullyQualifiedJavaType resolveColumnType(String dbType, int length, int scale)
	{
		FullyQualifiedJavaType fqjt = null;
		dbType = dbType.toUpperCase().trim();
		if (dbType.startsWith("CHAR") || dbType.startsWith("VARCHAR"))
			fqjt = FullyQualifiedJavaType.getStringInstance();
		else if (dbType.startsWith("DECIMAL")||dbType.equals("NUMERIC"))
		{
			if (scale == 0)
			{
				// 整数
				if (length <= 8)
					fqjt = new FullyQualifiedJavaType("java.lang.Integer");
				// TODO:SmartGWT不支持Long，所以不使用。但以后生成与否需要参数化
//				else if (length <= 18)
//					fqjt = new FullyQualifiedJavaType("java.lang.Long");
				else
					fqjt = new FullyQualifiedJavaType("java.math.BigDecimal");
			}
			else
			{
				fqjt = new FullyQualifiedJavaType("java.math.BigDecimal");
			}
		}
		else if (dbType.equals("INTEGER") || dbType.equals("INT") || dbType.equals("SMALLINT") || dbType.equals("TINYINT"))
		{
			fqjt = new FullyQualifiedJavaType("java.lang.Integer");
		}
		else if (dbType.equals("BIGINT") || dbType.equals("LONG"))
		{
			fqjt = new FullyQualifiedJavaType("java.lang.Long");
		}
		else if (dbType.equals("LONGTEXT") || dbType.equals("TEXT") || dbType.equals("LONG VARCHAR"))
		{
			fqjt = new FullyQualifiedJavaType("java.lang.String");
		}
		else if (dbType.equals("LONGBLOB") || dbType.equals("BLOB"))
		{
			fqjt = new FullyQualifiedJavaType("java.lang.Byte");
		}
		else if (dbType.equals("DATETIME") || dbType.equals("TIMESTAMP") || dbType.startsWith("DATE"))
		{
			fqjt = new FullyQualifiedJavaType("java.util.Date");
		}
		else if (dbType.equals("FLOAT"))
		{
			fqjt = new FullyQualifiedJavaType("java.lang.Double");	//DB2下没有float，统一使用Double
		}
		else if (dbType.equals("DOUBLE"))
		{
			fqjt = new FullyQualifiedJavaType("java.lang.Double");
		}
		else if (dbType.equals("NUMBERIC"))
		{
			fqjt = new FullyQualifiedJavaType("java.lang.Float");
		}
		else if (dbType.equals("BIT"))
		{
			fqjt = new FullyQualifiedJavaType("java.lang.Boolean");
		}
		else
		{
			System.err.println("未支持类型:" + dbType + "(" + dbType + ")");
			throw new IllegalArgumentException();
		}

		return fqjt;
	}
	
	private boolean isLobType(String dbType)
	{
		return dbType.equals("LONGTEXT") || dbType.equals("TEXT") || dbType.equals("LONG VARCHAR")||dbType.equals("LONGBLOB") || dbType.equals("BLOB");
	}
}
