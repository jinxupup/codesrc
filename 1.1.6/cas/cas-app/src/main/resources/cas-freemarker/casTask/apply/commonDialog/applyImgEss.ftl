

<#include "/layout_img.ftl"/>
<!DOCTYPE html>
<html lang="zh-cn">
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<script type="text/javascript" src="${base}/assets/js/jquery-1.7.2.min.js"></script>
		<script type="text/javascript" src="${base}/assets/js/jquery.easyui.min.js"></script>
		<script type="text/javascript" src="${base}/assets/js/jqueryui.js"></script>
		<script type="text/javascript" src="${base}/assets/js/jquery.cookie.js"></script>
		<script type="text/javascript" src="${base}/assets/js/mousewheel.js"></script>
		<script type="text/javascript" src="${base}/assets/js/iviewer.js"></script>
		<script type="text/javascript" src="${base}/assets/js/jquery.Jcrop.min.js"></script>
		<script type="text/javascript" src="${base}/assets/js/screenage.js"></script>
		<script type="text/javascript" src="${base}/assets/js/ui.tooltip.js"></script>
		
		<link type="text/css" rel="stylesheet" href="${base}/assets/css/custom.css" />
		<link type="text/css" rel="stylesheet" href="${base}/assets/css/iviewer.css" />
		<link type="text/css" rel="stylesheet" href="${base}/assets/css/jcrop.css" />
		<script>
			var current = 0;
			function srotate() {
				current = (current + 90) % 360;
				document.getElementById('ivImg').style.transform = 'rotate(' + current + 'deg)';
			}

			function nrotate() {
				current = (current - 90) % 360;
				document.getElementById('ivImg').style.transform = 'rotate(' + current + 'deg)';
			}


			window.onload = function () {
				document.getElementById('ivImg').ondblclick = function () {
					current = (current + 90) % 360;
					this.style.transform = 'rotate(' + current + 'deg)';
				}
			};
			<#--var current = 0;
			function rotate(that){
				current = (current+(that))%360;
				document.getElementById('viewer').style.transform = 'rotate('+current+'deg)';
			}-->
			function changeImg(that){
				document.getElementById('ivImg').src=that;
			}


		</script>
	</head>
	<body>
		<table border="1" sytle="solid #eaeaea;" width="100%;">
			<#if picUrlMap??>
				<tr>
					<#assign val="" />
					<td style="font-size:14px;face:'宋体';text-align:center;" valign="top">
						<div style="height:30px;">
							<span style="font-size:12.5px;face:'宋体';color:#0000FF;">电子信贷系统影像调阅</span>
						</div>
						<div style="text-align:left;">
							<#list picUrlMap?keys as key>
								<#if key_index==0>
									<#assign val="${picUrlMap[key]}" />
								</#if>
								<button type="button" onclick="changeImg('${picUrlMap[key]}')" style="width:100%;text-align:left;">${key}</button>
								<br/>
							</#list>
						</div>
						<div style="height:30px; padding-top:50px;">
							<a href="javascript:window.opener=null;window.open('', '_self');window.close();">退出</a>
						</div>

						<#--<div style="line-height: 1250px">-->

							<#--<button title="使图片逆时针旋转" style="cursor: pointer;color: #fff; background-color: #2b84d9;border-color: #2b84d9;top-bar-button: 90px;vertical-align: -2px;-->
                         <#--border-radius: 100%;padding: 1px;width: 25px;height: 25px;font-size: 12px;font-family: 黑体;text-align: center;border: none;line-height: 23px"-->
									<#--onclick="nrotate()">↕-->
							<#--</button>-->

							<#--<button title="使图片顺时针旋转" style="cursor: pointer;color: #fff; background-color: #2b84d9;border-color: #2b84d9;top-bar-button: 90px;vertical-align: -2px;-->
                         <#--border-radius: 100%;padding: 1px;width: 25px;height: 25px;font-size: 12px;font-family: 黑体;text-align: center;border: none;line-height: 23px"-->
									<#--onclick="srotate()">↔-->
							<#--</button>-->

						<#--</div>-->

					</td>
					<td  style="width:85%;height:745px;">
						<#--<div style="float:right;">
							<button type="button" onclick="rotate(-90);" style="margin-left:15px;margin-right:5px;margin-bottom:2px;">逆时针旋转90度</button>
							<button type="button" onclick="rotate(90);" style="margin-left:15px;margin-right:5px;margin-bottom:2px;">顺时针旋转90度</button>
							<button type="button" onclick="rotate(180);" style="margin-left:15px;margin-right:5px;margin-bottom:2px;">旋转180度</button>
						</div>-->
						<input type="hidden" id="imagePath" value="${val}"/>
						<div id="viewer" style="width:100%;height:100%;" class="viewer">
							<span style="font-size:12.5px;face:'宋体';color:#0000FF;">
							&nbsp;&nbsp;&nbsp;&nbsp; 滑动鼠标滚轮即放大缩小图片，90 度旋转请双击图片-或者 W - S 快捷键旋转；</span>
						</div>
					</td>
				</tr>
			</#if>
		</table>

	</body>
</html>
<script>

    document.onkeydown = function (ev) {ss()};//A65  s47  d32  w51   q45  e33
    function ss() {
        if (event.keyCode == 87) {
            nrotate();//逆时针
        }
        if (event.keyCode == 83) {
            srotate();//顺时针
        }
        <#--if (event.keyCode == 65) {-->
            <#--nextImg(-1);//上一张-->
        <#--}-->
        <#--if (event.keyCode == 68) {-->
            <#--nextImg(1);//下一张-->
        <#--}-->
        <#--if (event.keyCode == 81 && current!=0) {-->
            <#--preservationMethod('${(tmCmpMain.batchNo)!}','${(isAccets)!}');//下一张-->
        <#--}-->

    }


</script>
<#--影像调阅窗口-->
<#--<#include "/layout.ftl"/>
<@body>
	<div  style="text-align : center;">
		<#if picUrlMap??>
			<#list picUrlMap?keys as key>
				<@panel head="${key}---${picUrlMap[key]}" >
			    	<img id="target" src="${picUrlMap[key]}" style="width:500px;hiegth:500px;">
				</@panel>
				
			</#list>
		</#if>
	<div>
</@body>

<script>
	window.onload = function(){ 
		var current = 0;
		document.getElementById('target').onclick = function(){
		current = (current+90)%360;
		this.style.transform = 'rotate('+current+'deg)';
	}
	//全景图随鼠标的滚动进行缩放
	var fullImg = document.getElementById("target");
	if (fullImg.addEventListener) {
		// IE9, Chrome, Safari, Opera
		fullImg.addEventListener("mousewheel", MouseWheelHandler, false);
		// Firefox
		fullImg.addEventListener("DOMMouseScroll", MouseWheelHandler, false);
	}else fullImg.attachEvent("onmousewheel", MouseWheelHandler);
		function MouseWheelHandler(e) {
			var e = window.event || e;
			var delta = Math.max(-1, Math.min(1, (e.wheelDelta || -e.detail)));
			fullImg.style.width = Math.max(200, Math.min(2400, fullImg.width + (30 * delta))) + "px";
			return false;
		}
	};
</script>-->