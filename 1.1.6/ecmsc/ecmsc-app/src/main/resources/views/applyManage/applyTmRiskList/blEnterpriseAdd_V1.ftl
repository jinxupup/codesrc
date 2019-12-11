<@form id="queryForm" >
     <@row>
          <@field label="公司名称">
          		<@input name="corpName" />
          </@field>
          <@field label="公司电话">
                <@input name="empPhone" />
          </@field>        
      </@row>
      <@row>
      	  <@field label="公司地址">
                <@input name="empAdd" />
          </@field>
          <@field label="黑名单来源">
                <@dictSelect name="BlacklistSrc" />
          </@field>
      </@row>
      <@row>
      	  <@field label="记录有效期">
                <@date name="" />
          </@field>
          <@field label="行动类型">
                <@dictSelect name="actType" />
          </@field>
      </@row>
      <@row>
      	  <@field label="风险级别">
                <@dictSelect name="riskLevel" />
          </@field>
          <@field label="上黑名单原因说明">
                <@input name="reason" />
          </@field>
      </@row>
      <@row>
      	  <@field label="备注">
                <@input name="memo" />
          </@field>          
      </@row>
      <@toolbar>
      	  <@backButton />
      	  <@submitButton />
      </@toolbar>
</@form>      