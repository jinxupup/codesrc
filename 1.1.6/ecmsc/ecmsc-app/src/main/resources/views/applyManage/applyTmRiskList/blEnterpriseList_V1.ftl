<@form id="queryForm" >
     <@row>
          <@field label="公司名称">
          		<@input name="corpName" />
          </@field>
          <@field label="公司电话">
                <@input name="corpName" />
          </@field>
          <@button id="queryButton" name="查询" fa="search"/> 
      </@row>
</@form>      
<@table id="dataTable" url="" form_id="queryForm" button_id="queryButton">
      <@th checkbox="true"/>
      <@th title="黑名单来源" field="blacklistSrc" />
      <@th title="公司名称" field="corpName" />
      <@th title="公司电话" field="empPhone" />
      <@th title="公司地址" field="empAdd" />
      <@th title="记录有效期" field="validDate" />  
      <@th title="上黑名单原因说明" field="reason" />
      <@th title="行动类型" field="actType" />
      <@th title="风险级别" field="riskLevel" />                 
      <@th title="操作" render="true" >
	  		<@href href=""  name="删除" />
	  		<@href href="" sense="danger" name="修改" />
	  </@th>         
</@table>