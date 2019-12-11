package com.jjb.unicorn.maven.plugin;

import java.io.File;
import java.io.Serializable;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.ibator.api.GeneratedJavaFile;
import org.apache.ibatis.ibator.api.dom.java.CompilationUnit;
import org.apache.ibatis.ibator.api.dom.java.Field;
import org.apache.ibatis.ibator.api.dom.java.FullyQualifiedJavaType;
import org.apache.ibatis.ibator.api.dom.java.JavaVisibility;
import org.apache.ibatis.ibator.api.dom.java.TopLevelClass;
import org.apache.ibatis.ibator.internal.util.JavaBeansUtil;
import org.apache.maven.model.Resource;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.project.MavenProject;

import com.jjb.unicorn.maven.meta.Column;
import com.jjb.unicorn.maven.meta.Database;
import com.jjb.unicorn.maven.meta.MappingInfo;
import com.jjb.unicorn.maven.meta.Table;
import com.jjb.unicorn.maven.util.MappingUtil;



/**
 * @author jjb
 * 
 * @goal mybatis_oracle
 * 
 * @phase generate-sources
 */
public class MyBatis_OracleMojo extends AbstractMojo {

	/**
	 * @parameter
	 * @required
	 */
	private String basePackage;
	/**
	 * @parameter default-value="target/yak-generated"
	 */
	private String outputDirectory;
	
	
	/**
	 * @parameter
	 */
	private String versionField;
	
	
	/**
	 * @parameter
	 * @required
	 */
	private File sources[];

	/**
	 * @parameter default-value=false
	 */
	private boolean trimStrings;
	
	/**
	 * @parameter default-value=false
	 */
	private boolean useAutoTrimType;
	
	/**
	 * @parameter default-value=".*"
	 */
	private String tablePattern;
	
    /**
     * <i>Maven Internal</i>: Project to interact with.
     *
     * @parameter expression="${project}"
     * @required
     * @readonly
     * @noinspection UnusedDeclaration
     */
    private MavenProject project;

    private List<Generator> generators = new ArrayList<Generator>();

	public void execute() throws MojoExecutionException, MojoFailureException
	{
		try
		{
			project.addCompileSourceRoot(outputDirectory);
			Resource r = new Resource();
			ArrayList<String> in = new ArrayList<String>();
			in.add("**/client/**");
			in.add("**/shared/**");
			r.setDirectory(outputDirectory);
			r.setIncludes(in);
			project.addResource(r);
			
			List<Database> databases = new ArrayList<Database>();
			PDMImporter pdmImporter = new PDMImporter(getLog());
			ERMImporter ermImporter = new ERMImporter(getLog());
			
			for (File source : sources)
			{
				getLog().info("处理源文件:" + source.getAbsolutePath());
				String ext = FilenameUtils.getExtension(source.getName());
				if ("pdm".equals(ext))
					databases.add(pdmImporter.doImport(source, tablePattern));
				else if ("erm".equals(ext))
					databases.add(ermImporter.doImport(source, tablePattern));
				else
					throw new MojoExecutionException("不支持的扩展名[" + ext + "]");
			}
			
			//调各插件
			//TODO使用配置方式调，并且各组件间需要调整整合，现在还是耦合性很大
			generators.add(new EqualsHashCode());
			generators.add(new Entity2Map());
			generators.add(new ToString());
			generators.add(new FillDefaultValues());

			for (Generator generator : generators)
			{
				generator.setLogger(getLog());
			}
			
			for (Database db : databases)
			{
				
				List<CompilationUnit> units = generateEntity(db);
				List<MappingInfo> unitsMap =  generateMapping (db);
				List<GeneralFileContent> allFiles = new ArrayList<GeneralFileContent>();

				//先把内容生成出来，统一放到allFiles里
				for (CompilationUnit unit : units)
				{
					GeneratedJavaFile gjf = new GeneratedJavaFile(unit, outputDirectory);
					String filename = MessageFormat.format(
							"{0}/{1}", 
							StringUtils.replace(gjf.getTargetPackage(), ".", "/"),
							gjf.getFileName());
					
					allFiles.add(new GeneralFileContent(filename, gjf.getFormattedContent(), "UTF-8"));
				}
				
				//生成java文件
				for (GeneralFileContent file : allFiles)
				{
					File targetFile = new File(FilenameUtils.concat(outputDirectory, file.getFilename()));
					FileUtils.writeStringToFile(targetFile, file.getContent(), file.getEncoding());
				}
				
				//生成mybatis映射文件
				for (MappingInfo info : unitsMap) {
					String outputMappingDirectory = outputDirectory.substring(0, outputDirectory.length()-4)+"resources/mybatis-mapping";
					File targetFile = new File (FilenameUtils.concat(outputMappingDirectory, info.getFileName()));
					FileUtils.writeStringToFile(targetFile, info.getContent(), "UTF-8");
				}
			}
		}
		catch (Exception e)
		{
			throw new MojoFailureException("生成过程出错", e);
		}
	}
	

	/**
	 *
	 * @return
	 */
	private List<CompilationUnit> generateEntity(Database db)
	{
		List<CompilationUnit> generatedFiles = new ArrayList<CompilationUnit>();

		Map<Table, TopLevelClass> generatingMap = new HashMap<Table, TopLevelClass>();
		//生成entity
		for (Table table : db.getTables())
		{
		
			table.setJavaClass(new FullyQualifiedJavaType(basePackage +"."+  JavaBeansUtil.getCamelCaseString(table.getDbName(), true)));

			for (Column col : table.getColumns())
				col.setPropertyName(JavaBeansUtil.getCamelCaseString(col.getDbName(), false));
			

			TopLevelClass entityClass = new TopLevelClass(table.getJavaClass());
			entityClass.setVisibility(JavaVisibility.PUBLIC);
			if (StringUtils.isNotBlank(table.getTextName()))
			{
				entityClass.addJavaDocLine("/**");
				entityClass.addJavaDocLine(" * " + table.getTextName());
				entityClass.addJavaDocLine(" * @author jjb");
				entityClass.addJavaDocLine(" */");
			}

			entityClass.addSuperInterface(GeneratorUtils.forType(entityClass, Serializable.class.getCanonicalName()));

			Field serialField= new Field ();
			serialField.setFinal(true);
			serialField.setStatic(true);
			serialField.setName("serialVersionUID");
			FullyQualifiedJavaType longType = new FullyQualifiedJavaType ("long");
			serialField.setType(longType);
			serialField.setInitializationString("1L");
			serialField.setVisibility(JavaVisibility.PRIVATE);
			entityClass.addField(serialField);
			
			for (Column col : table.getColumns())
			{
				
				GeneratorUtils.generateProperty(entityClass, col.getJavaType(), col.getPropertyName(), GeneratorUtils.generatePropertyJavadoc(col), trimStrings);

			}
			for (Generator generator : generators)
			{
				generator.afterEntityGenerated(entityClass, table);
			}
			generatedFiles.add(entityClass);
			generatingMap.put(table, entityClass);
		}
		
		return generatedFiles;
	}
	
	
	/**
	 *
	 * @return
	 */
	private List<MappingInfo> generateMapping (Database db)
	{
		List<MappingInfo> generatedFiles = new ArrayList<MappingInfo>();

		for (Table table : db.getTables())
		{
			MappingInfo mappingInfo = new MappingInfo ();
			
			StringBuilder sb = new StringBuilder("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>");
			sb.append("\n");
			sb.append("<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\" >");
			sb.append("\n");
			sb.append("<mapper namespace=\"");
			//命名空间
			sb.append(basePackage+".mapping."+JavaBeansUtil.getCamelCaseString(table.getDbName(), true)+"Mapper\">");
			sb.append("\n");
			sb.append("	<resultMap id=\"BaseResultMap\" type=\"");
			sb.append(getEntityFullName(table)+"\" >\n");
			//映射语句
			List<Column> keyColumns = table.getPrimaryKeyColumns();
			Set<String> keyNames = new HashSet<String> ();
			for (Column col : keyColumns) {
				sb.append("		<id column=\"");
				sb.append(col.getDbName());
				sb.append("\" property=\"");
				sb.append(JavaBeansUtil.getCamelCaseString(col.getDbName(), false));
				sb.append("\" jdbcType=\"");
				sb.append(col.getDbType());
				sb.append("\"/>\n");
				keyNames.add(col.getDbName());
			}

			for (Column col : table.getColumns()) {
				if (keyNames.contains(col.getDbName())) {
					continue;
				}
				sb.append("		<result column=\"");
				sb.append(col.getDbName());
				sb.append("\" property=\"");
				sb.append(JavaBeansUtil.getCamelCaseString(col.getDbName(), false));
				sb.append("\" jdbcType=\"");
				sb.append(col.getDbType());
				sb.append("\" />\n");
			}
			sb.append("	</resultMap>\n");
			
			MappingUtil.createTableFieldId(sb, table.getColumns(),table.getDbName());
			
			//生成sequence名称,只有sequence才需要
			createSequenceId(sb,table,keyColumns);
			
			//生成删除语句
			sb.append("	<delete id=\"deleteByPrimaryKey\" parameterType=\"map\" >\n");
			sb.append("		delete from ");
			sb.append(table.getDbName());
			sb.append("\n");
			sb.append("		where \n");
			processKeyColumns(sb,keyColumns);
			sb.append("	</delete>\n");
			
			//生成插入语句
			sb.append("	<insert id=\"insert\" parameterType=\"");
			sb.append(getEntityFullName(table));
			sb.append("\" >\n");
			createInsertSequence(sb,keyColumns);
			sb.append("		insert into "+table.getDbName()+" ( ");
			sb.append(MappingUtil.createSelectStatement(table.getDbName()));
			sb.append(" )\n");
			sb.append("		values ( ");
			sb.append(createInsertStatementA (table.getColumns()));
			sb.append(" \n	)");
			sb.append("\n	</insert>\n");
			
			//生成不能更新空值的更新语句
			sb.append("	<update id=\"updateNotNullByPrimaryKey\" parameterType=\"");
			sb.append(getEntityFullName(table));
			sb.append("\">\n");
			sb.append("		update ");
			sb.append(table.getDbName());
			sb.append(" \n");
			sb.append("		<trim prefix=\"set\" suffixOverrides=\",\"> \n");
			sb.append(MappingUtil.createUpdateStatement(table.getColumns(),keyNames));
			sb.append("		</trim>");
			sb.append("\n		where \n");
			MappingUtil.processUpdateKeyColumnsNoVersion(sb,keyColumns,table.getColumns());
			sb.append("	</update>\n");
			
			//生成更新语句
			sb.append("	<update id=\"updateByPrimaryKey\" parameterType=\"");
			sb.append(getEntityFullName(table));
			sb.append("\">\n");
			sb.append("		update ");
			sb.append(table.getDbName());
			sb.append(" \n");
			sb.append("		<trim prefix=\"set\" suffixOverrides=\",\"> \n");
			sb.append(MappingUtil.createUpdateNullableStatement(table.getColumns(),keyNames,versionField));
			sb.append("		</trim>");
			sb.append("\n		where \n");
			MappingUtil.processUpdateKeyColumns (sb,keyColumns,table.getColumns(),versionField);
			sb.append("	</update>\n");
			
			//生成查询语句(通过主键)
			sb.append("	<select id=\"selectByPrimaryKey\" resultMap=\"BaseResultMap\" parameterType=\"map\" >\n");
			sb.append("		select	");
			sb.append(MappingUtil.createSelectStatement(table.getDbName()));
			sb.append("		\n		from ");
			sb.append(table.getDbName());
			sb.append("\n		where \n");
			processKeyColumns(sb,keyColumns);
			sb.append("	</select>\n");
			
			//生成查询语句
			sb.append("	<select id=\"selectAll\" resultMap=\"BaseResultMap\" parameterType=\"map\" >\n");
			sb.append("		select ");
			sb.append(MappingUtil.createSelectStatement(table.getDbName()));
			sb.append("		\n 		from ");
			sb.append(table.getDbName());
			sb.append("\n		where 1=1 \n");
			sb.append(MappingUtil.createConditionalStatement(table.getColumns()));
			sb.append(MappingUtil.createOrderList());
			sb.append("\n	</select>");
			
			//查询ID
			MappingUtil.createIdList(sb, table.getDbName(), table.getColumns());
			
			sb.append("\n</mapper>");
			mappingInfo.setContent(sb.toString());
			mappingInfo.setFileName(JavaBeansUtil.getCamelCaseString(table.getDbName(), true)+"Mapper.xml");
			
			generatedFiles.add(mappingInfo);
		}
		
		return generatedFiles;
	}
	
	
	
	private String fetchPrimaryKeyName (List<Column> keyColumns) {
		int size = keyColumns.size();
		if (size != 1) {
			return null;
		}
		
		return keyColumns.get(0).getDbName();
	}
	
	private void createSequenceId (StringBuilder sb,Table table,List<Column> keyColumns) {
		if (!needGenerateSequence(keyColumns)) {
			return;
		}
		
		sb.append("	<sql id=\"TABLE_SEQUENCE\">");
		sb.append("SEQ_");
		sb.append(table.getDbName());
		sb.append("_");
		sb.append(fetchPrimaryKeyName(keyColumns));
		sb.append(".nextval");
		sb.append("</sql>\n\n");
	}
	
	private void createInsertSequence (StringBuilder sb,List<Column> keyColumns) {
		if (!needGenerateSequence(keyColumns)) {
			return;
		}
		
		sb.append("		<selectKey keyProperty=\"");
		sb.append(JavaBeansUtil.getCamelCaseString(fetchPrimaryKeyName(keyColumns),false));
		sb.append("\" resultType=\"int\" order=\"BEFORE\">\n");
        sb.append("			select <include refid=\"TABLE_SEQUENCE\" /> from dual\n");
        sb.append("		</selectKey>\n");
	}
	
	private boolean needGenerateSequence (List<Column> keyColumns) {
		if (keyColumns.size() != 1) {
			return false;
		}
		
		Column col = keyColumns.get(0);
		if (col.isIdentity()) {
			return true;
		}
		
		return false;
	}

	private String createInsertStatementA (List<Column> keyColumns) {
		StringBuilder temp = new StringBuilder ("");
		int i= 1;
		int size = keyColumns.size();
		for (Column col : keyColumns) {
			
			temp.append(" #{");
			temp.append(JavaBeansUtil.getCamelCaseString(col.getDbName(), false));
			temp.append(",jdbcType=");
			temp.append(col.getDbType());
			temp.append(" }");
			if (i++ <size) {
				temp.append(" ,");
			}
			if (i%4 == 0) {
				temp.append("\n			");
			}
		}
		String result = temp.toString();
		
		return result;
	}
	
	
	private String getEntityFullName (Table table) {
		return basePackage+"."+JavaBeansUtil.getCamelCaseString(table.getDbName(), true);
	}
	
	private void processKeyColumns (StringBuilder sb ,List<Column> keyColumns) {
		int size = keyColumns.size();
		int i = 1;
		for (Column col : keyColumns) {
			sb.append("		");
			sb.append(col.getDbName());
			sb.append(" = #{");
			sb.append(JavaBeansUtil.getCamelCaseString(col.getDbName(), false));
			sb.append(", jdbcType=");
			sb.append(col.getDbType());
			sb.append("}\n");
			if (i++ <size) {
				sb.append(" 	and ");
			}


		}
	}

	public String getOutputDirectory()
	{
		return outputDirectory;
	}

	public void setOutputDirectory(String outputDirectory)
	{
		this.outputDirectory = outputDirectory;
	}

	public boolean isTrimStrings()
	{
		return trimStrings;
	}

	public void setTrimStrings(boolean trimStrings)
	{
		this.trimStrings = trimStrings;
	}

	public String getBasePackage()
	{
		return basePackage;
	}

	public void setBasePackage(String basePackage)
	{
		this.basePackage = basePackage;
	}

	public MavenProject getProject()
	{
		return project;
	}

	public void setProject(MavenProject project)
	{
		this.project = project;
	}

	public boolean isUseAutoTrimType() {
		return useAutoTrimType;
	}

	public void setUseAutoTrimType(boolean useAutoTrimType) {
		this.useAutoTrimType = useAutoTrimType;
	}

	public File[] getSources() {
		return sources;
	}

	public void setSources(File[] sources) {
		this.sources = sources;
	}


	public String getVersionField() {
		return versionField;
	}


	public void setVersionField(String versionField) {
		this.versionField = versionField;
	}

}
