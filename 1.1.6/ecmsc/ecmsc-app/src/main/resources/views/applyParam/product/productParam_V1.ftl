<#include "/layout.ftl"/>
<@body>
    <@panel head="产品列表">
        <@panelBody>
            <@form id="queryForm" cols="4" style="padding-top:10px;">
                <@row>
	                <@field label="产品代码">
	                    <@select id="productCd" name="productCd" value="" options=ecms_('tableMap','productForStatus','') />
	                </@field>
					<@field label="卡等级">
						<@dictSelect dicttype="CardClass" name="cardClass"/>
					</@field>
					<@field label="产品状态">
						<@dictSelect nullable="false" dicttype="ProductStatus" name="productStatus"/>
					</@field>
					<#--<@field label="流程选择">
						<@dictSelect dicttype="ProcdefKey" name="procdefKey"/>
					</@field>-->
					<@field label="" field_ar="" label_ar="3" input_ar="32">
						<@button id="queryButton" name="查询" fa="search"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	                	<@href href="productParam/pruductAddPage" name="新增" fa="plus"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	                	<@buttonAuth code="SYNC_BMP_PRODUCTS">
	                		<@button name="同步核心产品" click="syncBmpPrdoucts()"/>
	                	</@buttonAuth>
					</@field>
	                
                </@row>
            </@form>
            </br>
            <@table id="dataTable" url="productParam/list" form_id="queryForm" button_id="queryButton" page_size="10" condensed="true">
                <@th title="产品编号" field="productCd" sortable="true"/>
                <@th title="产品描述" field="productDesc" />
                <@th title="卡等级" field="cardClass" render="true">
                	 <@thDictName dicttype="CardClass"  value="{{row.cardClass}}" showcode="true" />
                </@th>
                <@th title="产品状态" field="productStatus" render="true" sortable="true">
                	 <@thDictName dicttype="ProductStatus"  value="{{row.productStatus}}" showcode="true"/>
                </@th>
                <@th title="流程选择" render="true">
                	<@button name="查看流程" click="applyHistoryBtn('{{row.productCd}}','{{row.productDesc}}')" fa="history"/>
				</@th>
                <@th title="操作" render="true" >
                    <@href href="productParam/pruductUpdatePage?id={{row.id}}" name="修改" />
					<@href href="productParam/setPruductProcessPage?id={{row.id}}" name="关联流程" />
                    <@buttonAuth code="ECMS_PROD_WITH_FIELD">
                		<@href href="fieldManage/fieldProductPage?productCd={{row.productCd}}" name="关联字段"/>
                	</@buttonAuth>
                </@th>
            </@table>
            <@field  hidden="true">
                <@input name="query._SORT_NAME" value="productCd" />
                <@input name="query._SORT_ORDER" value="asc"/>
            </@field>
        </@panelBody>
        
    </@panel>
    </br>
    <@panel head="卡面列表" style="width:500px">
    	<table>
    		<th>
    			<td>
    				<@table style="width:380px" id="dataTable2" striped="false" url="productParam/cardFaceList" pagination="false" >
				        <@th title="卡面代码" field="code" width="25%"/>
				        <@th title="卡面描述" field="codeName" width="75%"/>
				    </@table>
    			</td>
    			<td valign="top" style="padding:10px 10px;">
    				<@buttonAuth code="SYNC_BMP_CARDFACE">
                		<@button name="同步核心卡面" click="syncBmpCardFaces()"/>
                	</@buttonAuth>
    			</td>
    		</th>
    	</table>
    </@panel>
    <script type="text/javascript">
		<#--确认同步核心产品参数 -->
		function syncBmpPrdoucts()
		{
			if(confirm("是否确认同步核心[产品]参数？")){
				$.ajax({
					url:"${base}/productParam/syncBmpPrdoucts",
					type:"post",
					data:{},
					dataType:"json",
		 			success:function(ref){
	 					if(ref.s){<#--如果成功-->
	 						alert(ref.msg);
	 						$('#dataTable').bootstrapTable('refresh');
	 					}else{
		 					alert(ref.msg);<#--如果失败，则显示失败原因-->
	 					}
		 			}		
				});	
			}
		}
		
		<#--确认同步核心卡面参数 -->
		function syncBmpCardFaces(){
			if(confirm("是否确认同步核心[卡面]参数？如果[确定]则清除当前系统已有[卡面]再同步核心!")){
				$.ajax({
					url:"${base}/productParam/syncBmpCardFaces",
					type:"post",
					data:{},
					dataType:"json",
		 			success:function(ref){
	 					if(ref.s){<#--如果成功-->
	 						alert(ref.msg);
	 						$('#dataTable2').bootstrapTable('refresh');
	 					}else{
		 					alert(ref.msg);<#--如果失败，则显示失败原因-->
	 					}
		 			}		
				});	
			}
		}
		<#--历史申请信息 -->
		var applyHistoryBtn = function(productCd,productDesc){
			var url = '${base}/productParam/productProcessList?productCd='+productCd;
	        dialogInfo('产品 ['+productCd+'-'+productDesc+'] 与系统流程及风控条线配置',1400,500,url);
	    }
	    <#--弹出框共用js-->
		function dialogInfo(title,width,height,url){
			a = top.dialog({
				title: title,
				width: width,
				height: height,
				url: url,
				oniframeload:function(){},
				button:
				[
				    {
				        value: '确定',
				        callback: function () {
				       		this.close();
				            return false;
				        },
				        autofocus: true
				    }
				]
			});
			a.showModal();
		}
	</script>
		
</@body>
