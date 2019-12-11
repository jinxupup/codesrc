package com.jjb.acl.infrastructure;

import com.jjb.unicorn.facility.util.DataTypeUtils;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * SSH命令历史表
 * @author jjb
 */
public class TmCommandHist implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;

    private String nodeCode;

    private String issueCommandLine;

    private Date issueTime;

    private String issueUserOrg;

    private String issueUserId;

    private Integer exitCode;

    private String stderr;

    private String stdout;

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
     * <p>节点代码</p>
     */
    public String getNodeCode() {
        return nodeCode;
    }

    /**
     * <p>节点代码</p>
     */
    public void setNodeCode(String nodeCode) {
        this.nodeCode = nodeCode;
    }

    /**
     * <p>执行命令行</p>
     */
    public String getIssueCommandLine() {
        return issueCommandLine;
    }

    /**
     * <p>执行命令行</p>
     */
    public void setIssueCommandLine(String issueCommandLine) {
        this.issueCommandLine = issueCommandLine;
    }

    /**
     * <p>命令执行时间</p>
     */
    public Date getIssueTime() {
        return issueTime;
    }

    /**
     * <p>命令执行时间</p>
     */
    public void setIssueTime(Date issueTime) {
        this.issueTime = issueTime;
    }

    /**
     * <p>执行用户机构</p>
     */
    public String getIssueUserOrg() {
        return issueUserOrg;
    }

    /**
     * <p>执行用户机构</p>
     */
    public void setIssueUserOrg(String issueUserOrg) {
        this.issueUserOrg = issueUserOrg;
    }

    /**
     * <p>执行用户名</p>
     */
    public String getIssueUserId() {
        return issueUserId;
    }

    /**
     * <p>执行用户名</p>
     */
    public void setIssueUserId(String issueUserId) {
        this.issueUserId = issueUserId;
    }

    /**
     * <p>退出代码</p>
     */
    public Integer getExitCode() {
        return exitCode;
    }

    /**
     * <p>退出代码</p>
     */
    public void setExitCode(Integer exitCode) {
        this.exitCode = exitCode;
    }

    /**
     * <p>STDERR</p>
     */
    public String getStderr() {
        return stderr;
    }

    /**
     * <p>STDERR</p>
     */
    public void setStderr(String stderr) {
        this.stderr = stderr;
    }

    /**
     * <p>STDOUT</p>
     */
    public String getStdout() {
        return stdout;
    }

    /**
     * <p>STDOUT</p>
     */
    public void setStdout(String stdout) {
        this.stdout = stdout;
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
        map.put("nodeCode", nodeCode);
        map.put("issueCommandLine", issueCommandLine);
        map.put("issueTime", issueTime);
        map.put("issueUserOrg", issueUserOrg);
        map.put("issueUserId", issueUserId);
        map.put("exitCode", exitCode);
        map.put("stderr", stderr);
        map.put("stdout", stdout);
        map.put("jpaVersion", jpaVersion);
        return map;
    }

    public void updateFromMap(Map<String, Serializable> map) {
        if (map.containsKey("id")) this.setId(DataTypeUtils.getIntegerValue(map.get("id")));
        if (map.containsKey("nodeCode")) this.setNodeCode(DataTypeUtils.getStringValue(map.get("nodeCode")));
        if (map.containsKey("issueCommandLine")) this.setIssueCommandLine(DataTypeUtils.getStringValue(map.get("issueCommandLine")));
        if (map.containsKey("issueTime")) this.setIssueTime(DataTypeUtils.getDateValue(map.get("issueTime")));
        if (map.containsKey("issueUserOrg")) this.setIssueUserOrg(DataTypeUtils.getStringValue(map.get("issueUserOrg")));
        if (map.containsKey("issueUserId")) this.setIssueUserId(DataTypeUtils.getStringValue(map.get("issueUserId")));
        if (map.containsKey("exitCode")) this.setExitCode(DataTypeUtils.getIntegerValue(map.get("exitCode")));
        if (map.containsKey("stderr")) this.setStderr(DataTypeUtils.getStringValue(map.get("stderr")));
        if (map.containsKey("stdout")) this.setStdout(DataTypeUtils.getStringValue(map.get("stdout")));
        if (map.containsKey("jpaVersion")) this.setJpaVersion(DataTypeUtils.getIntegerValue(map.get("jpaVersion")));
    }

    @Override
    public String toString() {
        return getClass().getName() + "@" + Integer.toHexString(hashCode())+"["+
        "serialVersionUID="+serialVersionUID+
        ", id="+id+
        "id="+id+
        ", nodeCode="+nodeCode+
        "nodeCode="+nodeCode+
        ", issueCommandLine="+issueCommandLine+
        "issueCommandLine="+issueCommandLine+
        ", issueTime="+issueTime+
        "issueTime="+issueTime+
        ", issueUserOrg="+issueUserOrg+
        "issueUserOrg="+issueUserOrg+
        ", issueUserId="+issueUserId+
        "issueUserId="+issueUserId+
        ", exitCode="+exitCode+
        "exitCode="+exitCode+
        ", stderr="+stderr+
        "stderr="+stderr+
        ", stdout="+stdout+
        "stdout="+stdout+
        ", jpaVersion="+jpaVersion+
        "jpaVersion="+jpaVersion+
        "]";
    }

    public void fillDefaultValues() {
        if (nodeCode == null) nodeCode = "";
        if (issueCommandLine == null) issueCommandLine = "";
        if (issueTime == null) issueTime = new Date();
        if (issueUserOrg == null) issueUserOrg = "";
        if (issueUserId == null) issueUserId = "";
        if (exitCode == null) exitCode = 0;
        if (stderr == null) stderr = "";
        if (stdout == null) stdout = "";
        if (jpaVersion == null) jpaVersion = 0;
    }
}