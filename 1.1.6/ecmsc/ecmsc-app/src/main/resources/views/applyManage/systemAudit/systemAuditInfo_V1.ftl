<#include "/layout.ftl"/>
<@body>
<@gotop/>
	<#--历史修改记录弹出框-->
	<@panel>
    	<@panelBody  style="padding-left:10px;">
    		 <@form id="queryForm" cols="4">
                <@row>
					<@field label="操作员ID" field_ar="14">
						<@multipleSelect name="operatorId" value="${(tmAclUser.userName)!}"
							options=ecms_('tableMap','getAllUser','') nullable="true" single="true" showfilter="true"/>
					</@field>
					<@field label="查询起始日期">
						<@date id="beginDate" name="query.beginDate" datetype="date" value="${(query.beginDate)!}"/>
					</@field>
					<@field label="查询截止日期">
						<@date id="endDate" name="query.endDate" datetype="date" value="${(query.endDate)!}"/>
					</@field>
	               	<@button id="queryButton" name="查询" />
				</@row>
				
            </@form>
    		
			<@table id="dataTable" url="tmSystemAudit/tmSystemAuditList" form_id="queryForm" button_id="queryButton" load_auto="false" page_size="10" condensed="true">
                <@th title="ID" field="id" sortable="true"/>
                <@th title="操作员ID" field="operatorId" sortable="true"/>
                <@th title="修改类" field="paramKey" sortable="true"/>
                <@th title="操作类型" field="paramOperation" sortable="true"/>
                <@th title="修改前" render="true">
                {{if row.oldObject!=''}}
					<@button id="oldObjectButton" name="操作详细信息" click="insertOld(id={{row.id}})"/>
		    	{{/if}}
                </@th>
                <@th title="修改后" render="true">
                	{{if row.newObject!=''}}
                		<@button id="newObjectButton" name="操作详细信息" click="insertNew(id={{row.id}})"/>
                	{{/if}}
                </@th>
                <@th title="修改人" field="updateUser" sortable="true"/>
                <@th title="修改日期" field="updateDate" render="true" width="160px" sortable="true">
                	<@thDate value="{{row.updateDate}}" datetype="datetime" />
                </@th>
            </@table>
    	</@panelBody>
    </@panel>
    <script>
    	function insertOld(id){
    		b = top.dialog({
            title: '修改前数据',
            width:600,
            height:400,
            url:'tmSystemAudit/getPreRecord?id=' + id, 
            oniframeload:function(){},
            button:[
            {	
                value: '确定',
                callback: function () {
               		this.close();
	                return false;
                },
                autofocus: true
            },
            ]
       		});
        b.showModal(); 
    	}
    	
    	function insertNew(id){
    		b = top.dialog({
            title: '修改后数据',
            width:600,
            height:400,
            url:'tmSystemAudit/getAfterRecord?id=' + id, 
            oniframeload:function(){},
            button:[
            {	
                value: '确定',
                callback: function () {
               		this.close();
	                return false;
                },
                autofocus: true
            },
            ]
       		});
        b.showModal(); 
    	}
    </script>
</@body>