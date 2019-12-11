<#include "/layout.ftl">

<@body>
	<@panel head="资源管理">
	   <@panelBody>
    	  	<@form id="queryForm" cols="3">
    	  		<@row class="a" >
    		  		<@field label="系统类型">
                        <@dictSelect dicttype="SYS_TYPE" name="query.sysType"/>
                    </@field>
                    <@field label="资源类型">
                          <@dictSelect dicttype="RESOURCE_TYPE" name="query.type"/>
                    </@field>
    		  		<@field label="资源代码">
    		  			<@input name="query.resourceCode"/>
    		  		</@field>
    		  	</@row>
    		  	<@row>
    		  		<@field label="资源名称">
                        <@input name="query.resourceName" />
                    </@field>
    		  		<@field label="上级资源代码">
                        <@input name="query.parentResourceCode" />
                    </@field>
                    <@field label="" label_ar="0">
               			<@toolbar>
			                <@toolbar>
				    			<@button id="queryButton" fa="search">查询</@button>&nbsp;&nbsp;&nbsp;
				    			<@href href="acl/resource/addpage" fa="plus">新增</@href>
				    		</@toolbar>
			            </@toolbar>
               		</@field>
    	  		</@row>
    	  	</@form>
    	  <@table id="dataTable" url="acl/resource/list" form_id="queryForm" button_id="queryButton" condensed="true">
    	  	<@th title="资源代码" field="resourceCode" sortable="true"></@th>
    	  	<@th title="资源名称" field="resourceName" sortable="true"></@th>
    	  	<@th title="系统类型" field="sysType" render="true" sortable="true">
                <@thDictName dicttype="SYS_TYPE"  value="{{row.sysType}}" showcode="true" />
            </@th>
    	  	<@th title="资源类型" field="type" render="true" sortable="true">
                <@thDictName dicttype="RESOURCE_TYPE"  value="{{row.type}}" showcode="true" />
            </@th>
    	  	<@th title="上级资源代码" field="parentResourceCode" sortable="true"></@th>
            <@th title="资源路径" field="parentPath" sortable="true"></@th>
            <@th title="资源授权类型" field="resourceAuthType" render="true" sortable="true">
                <@thDictName dicttype="ResourceAuthType"  value="{{row.resourceAuthType}}" showcode="true" />
            </@th>
            <@th title="链接" field="href"></@th>
            <@th title="图标" field="icon" render="true">
                <@icon fa="{{row.icon}}"/> {{row.icon}}
            </@th>
            <@th title="排序" field="sort" sortable="true"></@th>
            <@th title="资源控制" field="isUsed" render="true" sortable="true">
            	<@thDictName dicttype="ResourceControlType"  value="{{row.isUsed}}" showcode="true" />
            </@th>
            <@th title="说明" field="remark"></@th>
            <@th title="操作" render="true" >
                <@href href="acl/resource/editpage?resourceCode={{row.resourceCode}}&&sysType={{row.sysType}}" name="编辑" />
                <@href href="acl/resource/addpage?resourceCode={{row.resourceCode}}&&sysType={{row.sysType}}" name="复制" />
                
                <@ajaxButton  confirm="确定删除此记录？" sense="danger"  name="删除" url="acl/resource/delete?resourceCode={{row.resourceCode}}&&sysType={{row.sysType}}" success_url="acl/resource/page" />
                
            </@th>
    	  </@table>
	  </@panelBody>
	</@panel>
</@body>