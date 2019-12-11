package com.jjb.acl.infrastructure;

import com.jjb.unicorn.facility.util.DataTypeUtils;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 服务器节点定义
 * @author jjb
 */
public class TmNode implements Serializable {
    private static final long serialVersionUID = 1L;

    private String nodeCode;

    private String host;

    private Integer port;

    private String username;

    private String privateKey;

    private String keyPassword;

    private String properties;

    private String jsvc;

    private String appHome;

    private String javaHome;

    private String catalinaHome;

    private Integer jpaVersion;

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
     * <p>服务器主机地址</p>
     */
    public String getHost() {
        return host;
    }

    /**
     * <p>服务器主机地址</p>
     */
    public void setHost(String host) {
        this.host = host;
    }

    /**
     * <p>服务器端口</p>
     */
    public Integer getPort() {
        return port;
    }

    /**
     * <p>服务器端口</p>
     */
    public void setPort(Integer port) {
        this.port = port;
    }

    /**
     * <p>登录用户名</p>
     */
    public String getUsername() {
        return username;
    }

    /**
     * <p>登录用户名</p>
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * <p>认证密钥数据(PEM)格式</p>
     */
    public String getPrivateKey() {
        return privateKey;
    }

    /**
     * <p>认证密钥数据(PEM)格式</p>
     */
    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    /**
     * <p>密钥加密密码</p>
     */
    public String getKeyPassword() {
        return keyPassword;
    }

    /**
     * <p>密钥加密密码</p>
     */
    public void setKeyPassword(String keyPassword) {
        this.keyPassword = keyPassword;
    }

    /**
     * <p>服务器属性定义</p>
     */
    public String getProperties() {
        return properties;
    }

    /**
     * <p>服务器属性定义</p>
     */
    public void setProperties(String properties) {
        this.properties = properties;
    }

    /**
     * <p>jsvc程序</p>
     */
    public String getJsvc() {
        return jsvc;
    }

    /**
     * <p>jsvc程序</p>
     */
    public void setJsvc(String jsvc) {
        this.jsvc = jsvc;
    }

    /**
     * <p>应用主目录</p>
     */
    public String getAppHome() {
        return appHome;
    }

    /**
     * <p>应用主目录</p>
     */
    public void setAppHome(String appHome) {
        this.appHome = appHome;
    }

    /**
     * <p>JDK目录(JAVA_HOME)</p>
     */
    public String getJavaHome() {
        return javaHome;
    }

    /**
     * <p>JDK目录(JAVA_HOME)</p>
     */
    public void setJavaHome(String javaHome) {
        this.javaHome = javaHome;
    }

    /**
     * <p>Tomcat目录</p>
     */
    public String getCatalinaHome() {
        return catalinaHome;
    }

    /**
     * <p>Tomcat目录</p>
     */
    public void setCatalinaHome(String catalinaHome) {
        this.catalinaHome = catalinaHome;
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
        map.put("nodeCode", nodeCode);
        map.put("host", host);
        map.put("port", port);
        map.put("username", username);
        map.put("privateKey", privateKey);
        map.put("keyPassword", keyPassword);
        map.put("properties", properties);
        map.put("jsvc", jsvc);
        map.put("appHome", appHome);
        map.put("javaHome", javaHome);
        map.put("catalinaHome", catalinaHome);
        map.put("jpaVersion", jpaVersion);
        return map;
    }

    public void updateFromMap(Map<String, Serializable> map) {
        if (map.containsKey("nodeCode")) this.setNodeCode(DataTypeUtils.getStringValue(map.get("nodeCode")));
        if (map.containsKey("host")) this.setHost(DataTypeUtils.getStringValue(map.get("host")));
        if (map.containsKey("port")) this.setPort(DataTypeUtils.getIntegerValue(map.get("port")));
        if (map.containsKey("username")) this.setUsername(DataTypeUtils.getStringValue(map.get("username")));
        if (map.containsKey("privateKey")) this.setPrivateKey(DataTypeUtils.getStringValue(map.get("privateKey")));
        if (map.containsKey("keyPassword")) this.setKeyPassword(DataTypeUtils.getStringValue(map.get("keyPassword")));
        if (map.containsKey("properties")) this.setProperties(DataTypeUtils.getStringValue(map.get("properties")));
        if (map.containsKey("jsvc")) this.setJsvc(DataTypeUtils.getStringValue(map.get("jsvc")));
        if (map.containsKey("appHome")) this.setAppHome(DataTypeUtils.getStringValue(map.get("appHome")));
        if (map.containsKey("javaHome")) this.setJavaHome(DataTypeUtils.getStringValue(map.get("javaHome")));
        if (map.containsKey("catalinaHome")) this.setCatalinaHome(DataTypeUtils.getStringValue(map.get("catalinaHome")));
        if (map.containsKey("jpaVersion")) this.setJpaVersion(DataTypeUtils.getIntegerValue(map.get("jpaVersion")));
    }

    @Override
    public String toString() {
        return getClass().getName() + "@" + Integer.toHexString(hashCode())+"["+
        "serialVersionUID="+serialVersionUID+
        ", nodeCode="+nodeCode+
        "nodeCode="+nodeCode+
        ", host="+host+
        "host="+host+
        ", port="+port+
        "port="+port+
        ", username="+username+
        "username="+username+
        ", privateKey="+privateKey+
        "privateKey="+privateKey+
        ", keyPassword="+keyPassword+
        "keyPassword="+keyPassword+
        ", properties="+properties+
        "properties="+properties+
        ", jsvc="+jsvc+
        "jsvc="+jsvc+
        ", appHome="+appHome+
        "appHome="+appHome+
        ", javaHome="+javaHome+
        "javaHome="+javaHome+
        ", catalinaHome="+catalinaHome+
        "catalinaHome="+catalinaHome+
        ", jpaVersion="+jpaVersion+
        "jpaVersion="+jpaVersion+
        "]";
    }

    public void fillDefaultValues() {
        if (nodeCode == null) nodeCode = "";
        if (host == null) host = "";
        if (port == null) port = 0;
        if (username == null) username = "";
        if (privateKey == null) privateKey = "";
        if (keyPassword == null) keyPassword = "";
        if (properties == null) properties = "";
        if (jsvc == null) jsvc = "";
        if (appHome == null) appHome = "";
        if (javaHome == null) javaHome = "";
        if (catalinaHome == null) catalinaHome = "";
        if (jpaVersion == null) jpaVersion = 0;
    }
}