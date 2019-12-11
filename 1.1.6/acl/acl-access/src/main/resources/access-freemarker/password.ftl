<#include "/layout.ftl"/>

<style type="text/css">
	.default {background: #eeeeee;}
	.weak {background: #FF0000;}
	.medium {background: #FF9900;}
	.strong {background: #33CC00;}
	span {display: inline-block;width: 70px;height: 30px;line-height: 30px;background: #ddd;text-align: center;margin: 4px 2px;}
</style>
<@body>
<@panel head="用户信息">
    <@panelBody>
	<@form id="form" action="editpassword" before="checkPassword" multi_submit="true" 
	   after="afterSubmit" >
        <@row>
            <@field label="登陆名">
                <@input id="userNo" name="userNo" value="${userNo}" readonly="true"/>
            </@field>
        </@row>
        <@row>
            <@field label="原密码">
                <@input id="oldPassword" name="oldPassword" type="password"  valid={"notEmpty":"true"} />
            </@field>
       </@row>
        <@row>
            <@field label="新密码">
                <@input id="newPassword" type="password" name="newPassword"  valid={"notEmpty":"true"} />
                <label for="">密  码  强  度：</label><span>弱</span><span>中</span><span>强</span>
            </@field>
        </@row>
        <@row>
            <@field label="重复新密码">
                <@input id="newPassword2" type="password" name="newPassword2"  valid={"notEmpty":"true"}/>
            </@field>
        </@row>
        
        <@row>
            <@toolbar>
                <@submitButton id="subBtn" />&nbsp;&nbsp;&nbsp;
				<@href href="logout" name="退出" />
            </@toolbar>
        </@row>
    </@form>    
        
    </@panelBody>
</@panel>

<script type="text/javascript">
	window.onload = function(){
	
		var oInput = document.getElementById('newPassword');
		oInput.value = '';
		var spans = document.getElementsByTagName('span');

		oInput.onkeyup = function(){
			//强度状态设为默认
			spans[0].className = spans[1].className = spans[2].className = "default";

			var newPassword = this.value;
			var result = 0;
			for(var i = 0, len = newPassword.length; i < len; ++i){
				result |= charType(newPassword.charCodeAt(i));
			}

			var level = 0;
			//对result进行四次循环，计算其level
			for(var i = 0; i <= 4; i++){
				if(result & 1){
					level ++;
				}
				//右移一位
				result = result >>> 1;
			}

			if(newPassword.length >= 8){
				switch (level) {
					case 1:
						spans[0].className = "weak";
						break;
					case 2:
					case 3:
						spans[0].className = "medium";
						spans[1].className = "medium";
						break;
					case 4:
						spans[0].className = "strong";
						spans[1].className = "strong";
						spans[2].className = "strong";
						break;
				}
			}
		}
	}

	/*
	定义一个函数，对给定的数分为四类(判断密码类型)，返回十进制1，2，4，8
	数字 0001 -->1  48~57
	小写字母 0010 -->2  97~122
	大写字母 0100 -->4  65~90
	特殊 1000 --> 8 其它
	*/
	function charType(num){
		if(num >= 48 && num <= 57){
			return 1;
		}
		if (num >= 97 && num <= 122) {
			return 2;
		}
		if (num >= 65 && num <= 90) {
			return 4;
		}
		return 8;
	}

	var checkPassword = function(){
		
		var newPassword = document.getElementById('newPassword').value;
		var result = 0;
		for(var i = 0, len = newPassword.length; i < len; ++i){
			result |= charType(newPassword.charCodeAt(i));
		}

		var level = 0;
		//对result进行四次循环，计算其level
		for(var i = 0; i <= 4; i++){
			if(result & 1){
				level ++;
			}
			//右移一位
			result = result >>> 1;
		}
		
		if(newPassword.length < 6){
			alert("新密码长度必须大于6个字符。");
			return false;
		}
		<#-- 20180315 不强制要求设置复杂密码，且长度不小于6位即可
		if(level != 4 || newPassword.length < 8){
			alert("新密码复杂度不够，必须包含：密码必须由字母大写、字母小写、数字、特殊字符共同组成！");
			return false;
		}
		-->
		if($("#newPassword").val()!=$("#newPassword2").val()){
			alert("重复新密码不一致");
			ar_.buttonDisable("subBtn",false);
			return false;
		}
        if($("#newPassword").val()==$("#oldPassword").val()){
            alert("新密码不能与原密码相同");
            ar_.buttonDisable("subBtn",false);
            return false;
        }
        return true;
    }
	
	var afterSubmit = function(res){
    	alert(res.msg);
    	if(res.s){
    		top.window.location.href = "${base}/main";
    	}else{
    		
    	}
    }
</script>

</@body>