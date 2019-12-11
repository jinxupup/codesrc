package com.jjb.acl.infrastructure;

import com.jjb.unicorn.facility.util.DataTypeUtils;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * SSH命令记录
 * @author jjb
 */
public class TmCommand implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;

    private String commandName;

    private String commandLine;

    private Integer jpaVersion;

    /**
     * <p>ID</p>
     */
    public Integer getId() {
        return id;
    }

    /**
     * <p>ID</p>
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * <p>命令名称</p>
     */
    public String getCommandName() {
        return commandName;
    }

    /**
     * <p>命令名称</p>
     */
    public void setCommandName(String commandName) {
        this.commandName = commandName;
    }

    /**
     * <p>命令行</p>
     */
    public String getCommandLine() {
        return commandLine;
    }

    /**
     * <p>命令行</p>
     */
    public void setCommandLine(String commandLine) {
        this.commandLine = commandLine;
    }

    /**
     * <p>乐观锁版本号</p>
     */
    public Integer getJpaVersion() {
        return jpaVersion;
    }

    /**
     * <p>乐观锁版本号</p>
     */
    public void setJpaVersion(Integer jpaVersion) {
        this.jpaVersion = jpaVersion;
    }

    public Map<String, Serializable> convertToMap() {
        HashMap<String, Serializable> map = new HashMap<String, Serializable>();
        map.put("id", id);
        map.put("commandName", commandName);
        map.put("commandLine", commandLine);
        map.put("jpaVersion", jpaVersion);
        return map;
    }

    public void updateFromMap(Map<String, Serializable> map) {
        if (map.containsKey("id")) this.setId(DataTypeUtils.getIntegerValue(map.get("id")));
        if (map.containsKey("commandName")) this.setCommandName(DataTypeUtils.getStringValue(map.get("commandName")));
        if (map.containsKey("commandLine")) this.setCommandLine(DataTypeUtils.getStringValue(map.get("commandLine")));
        if (map.containsKey("jpaVersion")) this.setJpaVersion(DataTypeUtils.getIntegerValue(map.get("jpaVersion")));
    }

    @Override
    public String toString() {
        return getClass().getName() + "@" + Integer.toHexString(hashCode())+"["+
        "serialVersionUID="+serialVersionUID+
        ", id="+id+
        "id="+id+
        ", commandName="+commandName+
        "commandName="+commandName+
        ", commandLine="+commandLine+
        "commandLine="+commandLine+
        ", jpaVersion="+jpaVersion+
        "jpaVersion="+jpaVersion+
        "]";
    }

    public void fillDefaultValues() {
        if (commandName == null) commandName = "";
        if (commandLine == null) commandLine = "";
        if (jpaVersion == null) jpaVersion = 0;
    }
}