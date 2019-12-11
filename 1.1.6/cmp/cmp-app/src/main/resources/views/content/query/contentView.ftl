<#include "/layout_img.ftl"/>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=IE11">
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
    <script type="text/javascript" src="${base}/assets/js/jquery.jcarousellite.min.js"></script>
    <link type="text/css" rel="stylesheet" href="${base}/assets/css/custom.css"/>
    <link type="text/css" rel="stylesheet" href="${base}/assets/css/cmpCustom.css"/>
    <link type="text/css" rel="stylesheet" href="${base}/assets/css/iviewer.css"/>
    <link type="text/css" rel="stylesheet" href="${base}/assets/css/jcrop.css"/>
    <script>

        var current = 0;

        function srotate() {
            if (current >= 360) {
                current = 90;
            } else {
                current = (current + 90) % 360;
            }
            document.getElementById('newIvImg').style.transform = 'rotate(' + current + 'deg)';
            if (current != 0) {
                $("#preservation").css("display", "block");
            } else {
                $("#preservation").css("display", "none");
            }
        }

        function nrotate() {
            if (current <= -360) {
                current = -90;
            } else {
                current = (current - 90) % 360;
            }
            document.getElementById('newIvImg').style.transform = 'rotate(' + current + 'deg)';
            if (current != 0) {
                $("#preservation").css("display", "block");
            } else {
                $("#preservation").css("display", "none");
            }
        }

        function closePage() {
            window.opener = null;
            window.open('', '_self');
            window.close();
        }
        function backPage() {
            window.location.href="${base}/cmp_/contentListPage";
        }
        var Index = 0;
        var Size = 0;
        var Path = null;
        var Url = null;
        var fileId='';
        window.onload = function () {
            nextImg(0);
            $('#alertTxt').html('').show().delay(1500).fadeOut();
            document.getElementById('newIvImg').ondblclick = function () {
                current = (current + 90) % 360;
                this.style.transform = 'rotate(' + current + 'deg)';
            };
            //图片移动
            var box1 = document.getElementById("box1");
            box1.onmousedown =function (event) {
                event.preventDefault();
                var ol = event.clientX - box1.offsetLeft;
                var ot = event.clientY - box1.offsetTop;
                document.onmousemove = function (event) {
                    var left = event.clientX - ol;
                    var top = event.clientY - ot;
                    box1.style.left = left + "px";
                    box1.style.top = top + "px";
                };
                document.onmouseup = function (event) {
                    document.onmousemove = null;
                    document.onmouseup = null;
                }

            }
        };

        //改变类型时显示的图片
        function changeImg(url, subTypeDesc, updateUser, formatDate, index, size, path,subtype,id) {
            if (current != 0) {
                var show = confirm("你修改了图片,是否需要保存?");
                if (show) {
                    return;
                }
            }
            //解决图片旋转后切换图片,后面图片也被旋转
            while (current != 0) {
                if (current > 0) {
                    nrotate();
                } else if (current < 0) {
                    srotate();
                }
            }
            fileId=id;
            if('vidType' == subtype){
                showVideo(url, subTypeDesc, updateUser, formatDate, index, size, path,subtype);
                return;
            }
            //图片居中
            $('#box1').css({
                "left":"50%",
                "top":"50%",
                "transform": "translate(-50%,-50%)"
            });
            $('#ivImg').remove();
            $('#newIvImg').show();
            $('#viedeoIv').hide();
            Index = index;
            Size = size;
            Path = path;
            Url = url;
            console.log(url);
            document.getElementById('newIvImg').src = url;
            var spanInfo = document.getElementById('subTypeDesc');
            spanInfo.innerHTML = subTypeDesc;
            var spanInfo = document.getElementById('updateUser');
            spanInfo.innerHTML = updateUser;
            var spanInfo = document.getElementById('formatDate');
            spanInfo.innerHTML = formatDate;

            //下方小图片选中提示
            for (i = 0; i <= Size; i++) {
                $("#smallImg" + i).css("border", "");
            }
            $("#smallImg" + Index).css("border", "2px solid #7000ff");
            // $('#newIvImg').css("height", "100px").css("width", "100px");

        }

        function showVideo(url, subTypeDesc, updateUser, formatDate, index, size, path,subtype){
            var viewerDiv= $('#viewer');
            $('#newIvImg').hide();
            var vd=$('#viedeoIv');
            if(vd.length>0){
                vd.attr("src",url);
                vd.show();
            }else{
                viewerDiv.append('<video id=\"viedeoIv\" src=\"'+url+'\" controls=\"controls\"></video>');
                $('#viedeoIv').css({ position: "absolute", top :"5px", left: "5px"});
            }
            Index = index;
            Size = size;
            Path = path;

            var spanInfo = document.getElementById('subTypeDesc');
            spanInfo.innerHTML = subTypeDesc;
            var spanInfo = document.getElementById('updateUser');
            spanInfo.innerHTML = updateUser;
            var spanInfo = document.getElementById('formatDate');
            spanInfo.innerHTML = formatDate;

        }

        /**
         *  上传图片
         */
        //自己,'${(parm)!}'
        function uploadButton(batchNo, isAccets) {
            window.location.href = "${base}/cmp_/uploadPage?batchNo=" + batchNo + "&isAccets=" + isAccets;
        }

        //外部免密`
        function uploadButtons(batchNo, isAccets,parm) {
            window.location.href = "${base}/assets/cmp_/uploadPage?batchNo=" + batchNo + "&isAccets=" + isAccets + "&parm=" + parm;
        }

        /**
         * 删除图片
         * @param batchNo
         * @param isAccets  deleteFile
         */
        //自己
        function delectImage(batchNo, isAccets) {
            var show = confirm("确定删除?");
            if (show == true) {
                $.ajax({
                    url: "${base}/cmp_/deleteFile",
                    type: "post",
                    dataType: "json",
                    data: {"batchNo": batchNo, "path": Path, "isAccets": isAccets,"FILEID":fileId},
                    async: false,
                    success: function (res) {
                        var json = JSON.stringify(res);
                        var a = eval('(' + json + ')');
                        if (a.imageNum == true) {
                            $('#alertTxt2').html('删除成功').show().delay(500).fadeOut();
                            setTimeout("Refresh()",500);
                        } else {
                            alert("删除失败 ！！！");
                        }
                    }
                });
            }
        }

        //TODO外部免密
        function delectImages(batchNo, isAccets) {
            var show = confirm("确定删除?");
            if (show == true) {
                $.ajax({
                    url: "${base}/assets/cmp_/deleteFile",
                    type: "post",
                    dataType: "json",
                    data: {"batchNo": batchNo, "path": Path, "isAccets": isAccets,"FILEID":fileId},
                    async: false,
                    success: function (res) {
                        var json = JSON.stringify(res);
                        var a = eval('(' + json + ')');
                        if (a.imageNum == true) {
                            $('#alertTxt2').html('删除成功').show().delay(1500).fadeOut();
                            setTimeout("Refresh()",1500);
                        } else {
                            alert("删除失败 ！！！");
                        }
                    }
                });
            }
        }

        $(function () {
            //菜单隐藏展开
            var tabs_i = 0;
            $('.vtitle').click(function () {
                var _self = $(this);
                var j = $('.vtitle').index(_self);
//                if (tabs_i == j - 20) return false;
                tabs_i = j;
                $('.vtitle em').each(function (e) {
                    if (e == tabs_i) {
                        $('em', _self).removeClass('v01').addClass('v02');
                    } else {
                        $(this).removeClass('v02').addClass('v01');
                    }
                });
                $('.vcon').slideUp().eq(tabs_i).slideDown();
            });
            
            $("#newIvImg").mouseup(function(e){  
               if(2==e.button){           
                window.location.href="${base}/assets/cmp_/download/gf?url="+this.src;
              }
            });
        });

        //修改图片旋转方向;
        function preservationMethod(batchNo,isAccets) {
            var show = confirm("保存当前图片旋转方向?");
            if (show) {
                //修改图片
                $.ajax({
                    url: "${base}/assets/cmp_/updateFile",
                    type: "post",
                    dataType: "json",
                    data: {"batchNo": batchNo, "path": Path, "isAccets": isAccets, "current": current, "url": Url},
                    async: false,
                    success: function (res) {
                        var json = JSON.stringify(res);
                        var a = eval('(' + json + ')');
                        if (a.imageNum == true) {
                            $('#alertTxt2').html('修改成功').show().delay(1500).fadeOut();
                            setTimeout("Refresh()",666);
                        } else {
                            alert("修改失败 ！！！");
                        }
                    }
                });
            }
        }
        //刷新页面方法
        function Refresh() {
            window.location.reload();
        }

    </script>


</head>
<body>
<table border="0" sytle="solid #eaeaea;" width="100%">
    <#assign start=0>
    <#assign end=5>
    <tr>
        <#assign val="" />
        <td style="right: 0px; width:1px;height: 700px;"/>
        <td style="font-size:16px;font-family: \5FAE\8F6F\96C5\9ED1;text-align:center; width: 15%; height: 730px"
            valign="top">
            <div id="menuDiv" style="overflow-x: auto; height: 90%; width: 100%; float:left;">
                <div style="text-align:left;">
                    <span style="font-size:11px;font-family: \5FAE\8F6F\96C5\9ED1;color:#337ab7;">姓名:</span>
                    <span class="_border">${(tmCmpMain.name)!}</span>
                </div>
                <div style="text-align:left;">
                    <span style="font-size:11px;font-family: \5FAE\8F6F\96C5\9ED1;color:#337ab7;">证件号:</span>
                    <span class="_border">${(tmCmpMain.idNo)!}</span>
                </div>
                <div style="text-align:left;">
                    <span style="font-size:11px;font-family: \5FAE\8F6F\96C5\9ED1;color:#337ab7;">批次号:</span>
                    <span class="_border">${(tmCmpMain.batchNo)!}</span>
                </div>
                <#--        <div style="text-align:left;">
                            <span style="font-size:11px;face:'\5FAE\8F6F\96C5\9ED1';color:black;">备注:${(remark)!}</span>
                        </div>-->
                <div class="_bordd">
                    ---------------------------------------
                </div>
                <div style="text-align:left;word-wrap: break-word;word-break: break-all;">
                    <#--<span id="contDetail" style="font-size:11px;face:'\5FAE\8F6F\96C5\9ED1';color:#0000FF;"></span>-->

                    <div style="text-align:left;">
                        <span style="font-family: \5FAE\8F6F\96C5\9ED1; font-size:11px;color:#337ab7;">类型: </span>
                        <span class="_border" id="subTypeDesc"/>
                    </div>
                    <div style="text-align:left;">
                        <span style="font-size:11px;font-family: \5FAE\8F6F\96C5\9ED1;color:#337ab7;">维护人: </span>
                        <span class="_border" id="updateUser"/>
                    </div>
                    <div style="text-align:left;">
                        <span style="font-size:11px;font-family: \5FAE\8F6F\96C5\9ED1;color:#337ab7;">上传时间: </span>
                        <span class="_border" id="formatDate"/>
                    </div>
                    <div class="_bordd">
                        ---------------------------------------
                    </div>
                </div>


                <br/>
                <#assign sss=-1>
                <#if allContextMap?? >
                    <#if (allContextMap?size>1)>
                    <#else>
                        <#list allContextMap?keys as k1><#--批次号-->
                            <#list allContextMap[k1]?keys as k2><#--小类型-->
                                <div class="vtitle"><em class="v"></em>${k2}</div>
                                <div class="vcon" style="display: none;">
                                    <ul class="vconlist clearfix">
                                        <#list allContextMap[k1][k2] as tmCmpContent><#--实体类集合<List>-->
                                            <li>
                                                <#assign sss +=1>
                                                <a onclick="changeImg('${tmCmpContent.contRelPath!}${tmCmpContent.contAbsPath!}',
                                                        '${tmCmpContent.subTypeDesc!}','${tmCmpContent.updateUser!}','${tmCmpContent.formatDate!}',
                                                        '${sss}',
                                                        '${allContextList?size}',
                                                        '${tmCmpContent.contAbsPath!}','${tmCmpContent.supType!}','${tmCmpContent.id!}')"
                                                   style="text-align:left;"> ${tmCmpContent.formatDate}</a>
                                                <input type="hidden" id="imagePath"
                                                       value="${tmCmpContent.contRelPath!''}${tmCmpContent.contAbsPath!''}"/>
                                            </li>
                                        </#list>
                                    </ul>
                                </div>
                            </#list>
                        </#list>
                    </#if>
                </#if>
            <div style="height: 150px; width: 100%"/>
            </div>

        </td>
        <td style="width: 82%;height: 730px;">

            <div id="divone" style="width:100%;height:100%;">
                <div id="viewer" style="width:100%;height:100%;overflow: hidden;max-height: 659px;max-width: 1357px;text-align: center" class="viewer">
                    <span style="font-size:14px;font-family: 黑体;color:#0000FF;">&nbsp;&nbsp;快捷键: ← →左右切换图片 ; ↑ ↓ 旋转图片 ; alt+q 或 鼠标右键 下载资源</span>
                    <span id="alertTxt" style="z-index: 1" class="cotentText"></span>
                    <div id="box1"style="position: absolute;" onmousewheel="return bbimg(this)">
                        <img id="newIvImg" style="z-index: -2;">
                    </div>
                    <div id="content">
                        <a id="prev" href="javascript:" onclick="nextImg(-1)"><</a>
                        <a id="next" href="javascript:" onclick="nextImg(1)">></a>
                    </div>
                </div>
                <div class="SpaceLine"></div>
                <div class="HS15"></div>
                <div class="main">
                    <table>
                        <tr style="width: 100% ;text-align:left;">
                            <td style="width: 10%">
                                <div style="text-align:left;">
                                    <#if (isAccets)??&& isAccets=='true'>
                                    <#--//免密删除-->
                                        <button title="删除当前浏览图片" style="cursor: pointer;color: #fff; background-color: #D9534F;border-color: #D9534F;top-bar-button: 90px;vertical-align: -2px;
                         border-radius: 4px;padding: 1px;width: 66px;height: 21.5px;font-size: 12px;font-family: 黑体;text-align: center;border: none;line-height: 23px"
                                                onclick="delectImages('${(tmCmpMain.batchNo)!}','${(isAccets)!}')">删除图片
                                        </button>
                                    <#else>
                                    <#--<input style="background-color: #ff4b3f" type="button" value="删除图片" onclick="delectImage('${(tmCmpMain.batchNo)!}','${(isAccets)!}')">-->
                                        <button title="删除当前浏览图片" style="cursor: pointer;color: #fff; background-color: #D9534F;border-color: #D9534F;top-bar-button: 90px;vertical-align: -2px;
                         border-radius: 4px;padding: 1px;width: 66px;height: 21.5px;font-size: 12px;font-family: 黑体;text-align: center;border: none;line-height: 23px"
                                                onclick="delectImage('${(tmCmpMain.batchNo)!}','${(isAccets)!}')">删除图片
                                        </button>
                                    </#if>
                                    <#if (isAccets)??&& isAccets=='true'>
                                        <button title="跳转到上传页面" style="cursor: pointer;color: #fff; background-color: #2b84d9;border-color: #2b84d9;top-bar-button: 90px;vertical-align: -2px;
                         border-radius: 4px;padding: 1px;width: 66px;height: 21.5px;font-size: 12px;font-family: 黑体;text-align: center;border: none;line-height: 23px"
                                                onclick="uploadButtons('${(tmCmpMain.batchNo)!}','${(isAccets)!}','${(parm)!}')">上传
                                        </button>
                                    <#else>
                                        <button title="跳转到上传页面" style="cursor: pointer;color: #fff; background-color: #2b84d9;border-color: #2b84d9;top-bar-button: 90px;vertical-align: -2px;
                         border-radius: 4px;padding: 1px;width: 40px;height: 21.5px;font-size: 12px;font-family: 黑体;text-align: center;border: none;line-height: 23px"
                                                onclick="uploadButton('${(tmCmpMain.batchNo)!}','${(isAccets)!}')">上传
                                        </button>
                                    </#if>
                                    <#if (isAccets)??&& isAccets=='true'>
                                    <#--//免密退出按钮-->
                                        <button title="退出本页面" style="cursor: pointer;color: #fff; background-color: #ff0000;border-color: #454545;top-bar-button: 90px;vertical-align: -2px;
                         border-radius: 4px;padding: 1px;width: 40px;height: 21.5px;font-size: 12px;font-family: 黑体;text-align: center;border: none;line-height: 23px"
                                                onclick="closePage()">退出
                                        </button>
                                    </#if>
                                </div>
                            </td>
                            <td style="width: 30%">
                                <div class="smallpic">

                                    <div class="smallpicBox">
                                        <button style="outline-color: rgba(112,0,255,0.69);background-color: transparent;text-align: center;border: none;width: 22px;height: 55px">
                                            <div class="leftBon" id="leftBon"><</div>
                                        </button>
                                        <div class="contUl">
                                            <ul class="smallpicUl">
                                                <#if allContextList?? >
                                                <#--<img src="${base}/assets/i/jj/ArrowL.jpg" id="btnPrev" class="FlLeft"-->
                                                <#--style="cursor:pointer;height: 50px;" onclick="upperFlip(-5)"/>-->
                                                    <#list allContextList as tmCmpContent>
                                                        <li class="smallpicLi" rel=${tmCmpContent_index+2}>
                                                            <#--下方小图片changeImg(url, subTypeDesc, updateUser, formatDate, index, size, path)-->
                                                            <#if tmCmpContent.supType=='vidType'>
                                                                <video id="smallImg${tmCmpContent_index}" src=${tmCmpContent.contRelPath!}${tmCmpContent.contAbsPath!} style="width:50px;height:50px"
                                                                       onclick="changeImg('${tmCmpContent.contRelPath!}${tmCmpContent.contAbsPath!}',
                                                                               '${tmCmpContent.subTypeDesc!}','${tmCmpContent.updateUser!}','${tmCmpContent.formatDate!}',
                                                                               '${tmCmpContent_index}',
                                                                               '${allContextList?size}',
                                                                               '${tmCmpContent.contAbsPath!}','${tmCmpContent.supType!}','${tmCmpContent.id!}')"/>
                                                            <#else>
                                                                <img id="smallImg${tmCmpContent_index}" src=${tmCmpContent.contRelPath!}${tmCmpContent.contAbsPath!} style="width:50px;height:50px;"
                                                                     onclick="changeImg('${tmCmpContent.contRelPath!}${tmCmpContent.contAbsPath!}',
                                                                             '${tmCmpContent.subTypeDesc!}','${tmCmpContent.updateUser!}','${tmCmpContent.formatDate!}',
                                                                             '${tmCmpContent_index}',
                                                                             '${allContextList?size}',
                                                                             '${tmCmpContent.contAbsPath!}','${tmCmpContent.supType!}','${tmCmpContent.id!}')"/>
                                                            </#if>
                                                        </li>
                                                    </#list>
                                                <#--<img src="${base}/assets/i/jj/ArrowR.jpg" id="btnNext" class="FlLeft"-->
                                                <#--style="cursor:pointer;height: 50px;" onclick="lowerFlip(+5)"/>-->
                                                </#if>
                                            </ul>
                                        </div>
                                        <button style="outline-color: rgba(112,0,255,0.69);background-color: transparent;text-align: center;border: none;width: 22px;height: 55px">
                                            <div class="rightBon" id="rightBon">></div>
                                        </button>
                                    </div>

                                </div>

                            </td>
                            <#if (isAccets)??&& isAccets!='true'>
                                <td style="width: 4%">
                                    <button title="退出本页面" style="cursor: pointer;color: #fff; background-color: #2b84d9;border-color: #454545;top-bar-button: 90px;vertical-align: -2px;
                         border-radius: 4px;padding: 1px;width: 40px;height: 21.5px;font-size: 12px;font-family: 黑体;text-align: center;border: none;line-height: 23px"
                                            onclick="backPage()">返回
                                    </button>
                                </td>
                            </#if>

                            <#--<td style="width: 2%">-->
                            <#--<button style="outline-color: rgba(112,0,255,0.69);background-color: transparent;text-align: center;border: none;width: 31px;height: 31px">-->
                            <#--<img title="使图片逆时针旋转" src="${base}/assets/i/rotateImg/inverse005.jpg" id="btnNext"-->
                            <#--class="FlLeft"-->
                            <#--style="cursor:pointer;height: 30px;vertical-align: -20px;"-->
                            <#--onclick="nrotate()"/>-->
                            <#--</button>-->
                            <#--</td>-->
                            <#--<td style="width: 2%"></td>-->

                            <#--<td style="width: 2%">-->
                            <#--<button style="outline-color: rgba(112,0,255,0.69);background-color: transparent;text-align: center;border: none;width: 31px;height: 31px">-->
                            <#--<img title="使图片顺时针旋转" src="${base}/assets/i/rotateImg/along005.jpg" id="btnNext"-->
                            <#--class="FlLeft"-->
                            <#--style="cursor:pointer;height: 30px;vertical-align: -20px;"-->
                            <#--onclick="srotate()"/>-->
                            <#--</button>-->
                            <#--</td>-->
                            <td style="width: 8%">

                                <button id="preservation" style="display: none;outline-color: rgba(112,0,255,0.69);background-color: transparent;text-align: center;border: none;width: 31px;height: 31px"
                                        onclick="preservationMethod('${(tmCmpMain.batchNo)!}','${(isAccets)!}')">
                                    <img title="将当前图片旋转保存" src="${base}/assets/i/rotateImg/saveImg.jpg" class="FlLeft" style="cursor:pointer;height: 30px;vertical-align: -20px;"/>
                                </button>
                            </td>
                            <span id="alertTxt2" class="cotentText"></span>

                            </td></tr>


                    </table>


                </div>
                <div class="HS15"></div>
            </div>

        </td>
        <td style="right: 0px; width: 3%;height: 730px;"></td>
    </tr>
</table>


<script type="text/javascript">


    var left_move = 0;
    var move_two = 0;

    //左箭头 .leftBon
    $("body").on("click", ".leftBon", function () {
        console.log(left_move);
        if (left_move > 0) {
            left_move--;
            $(".contUl ul").css("left", left_move * "-300" + "px");
        }
    });

    // 右箭头  .rightBon
    $("body").on("click", ".rightBon", function () {
        console.log(left_move);
        if (left_move <= Size /7) {
            left_move++;
            $(".contUl ul").css("left", left_move * "-300" + "px");
        }

    });


    // //缩略图滚动事件
    // $(".jCarouselLite").jCarouselLite({
    //     btnNext: "#btnNext",
    //     btnPrev: "#btnPrev",
    //     scroll: 1,
    //     speed: 240,
    //     circular: true,
    //     visible: 6
    // });


</script>
<script>


    <#--'${tmCmpContent.contRelPath!}${tmCmpContent.contAbsPath!}'-->
    <#--,'类型:${tmCmpContent.subTypeDesc!}; 由 ${tmCmpContent.updateUser!}; 于  ${tmCmpContent.formatDate!} 上传',-->
    <#--'${tmCmpContent_index}'-->


    //        左右图片切换
    function nextImg(i) {

        Index = Index - 0;
        if (current == 0) {
            if (i > 0) {
                if (Index > 2) {
                    if (move_two <= Size - 7) {
                        move_two++;
                        $(".contUl ul").css("left", move_two * "-60" + "px");
                    }
                }
                //加
                index = Index++;
            } else {
                //减
                if (Index > 2) {
                    if (move_two > 0) {
                        move_two--;
                        $(".contUl ul").css("left", move_two * "-60" + "px");
                    }
                }
                index = Index--;
            }
        }
        var maxsize = '${allContextList?size}';
        var pass;
        var add;
        <#list allContextList as tmCmpContent>
        var changeImgIndex = '${tmCmpContent_index}';
        changeImgIndex = changeImgIndex - 0;

        $('#next').css("cursor", "pointer");
        $('#prev').css("cursor", "pointer");
        $('#prev').attr("title", "上一张");
        $('#next').attr("title", "下一张");

        if (Index <= 0 ) {
            $('#prev').css("cursor", "not-allowed");
            $('#prev').attr("title", "已经是第一张了");
        }
        if (Index >= maxsize-1) {
            $('#next').css("cursor", "not-allowed");
            $('#next').attr("title", "已经是最后一张了");
        }
        if (Index < 0) {
            Index++;
            // var spanInfo = document.getElementById('alertTxt');    cursor: not-allowed;
            // spanInfo.innerHTML = "89787879787987789789798789";
            // $('<div>').appendTo('body').html('123456').show().delay(1500).fadeOut();
            $('#alertTxt').html('第一张').show().delay(1500).fadeOut();
        }
        if (Index > maxsize - 1) {
            Index--;
            $('#alertTxt').html('最后一张').show().delay(1500).fadeOut();
        }



        if (Index === changeImgIndex) {
            changeImg('${tmCmpContent.contRelPath!}${tmCmpContent.contAbsPath!}',
                '${tmCmpContent.subTypeDesc!}', '${tmCmpContent.updateUser!}', '${tmCmpContent.formatDate!}',
                changeImgIndex,
                maxsize,
                '${tmCmpContent.contAbsPath!}','${tmCmpContent.supType!}','${tmCmpContent.id!}');


            // changeImg(url, subTypeDesc, updateUser, formatDate, index, size, path)
            <#--pass = '${tmCmpContent.contRelPath!}${tmCmpContent.contAbsPath!}';-->
            <#--std= '${tmCmpContent.subTypeDesc!}';-->
            <#--udu = '${tmCmpContent.updateUser!}';-->
            <#--fmd= '${tmCmpContent.formatDate!}';-->
            <#--haha = '${tmCmpContent.contAbsPath!}';-->
            <#--changeImg(pass, std, udu, fmd, changeImgIndex, maxsize, haha);-->
        }

        </#list>
//        function changeImg(url, imageInfo, index, size)

    }


</script>
<script>
    //快捷键
    document.onkeydown = function (ev) {ss(ev)};
    function ss(ev) {
        if (event.keyCode == 38) {
            nrotate();//逆时针
        }
        if (event.keyCode == 40) {
            srotate();//顺时针
        }
        if (event.keyCode == 37) {
            nextImg(-1);//上一张
        }
        if (event.keyCode == 39) {
            nextImg(1);//下一张
        }
        if (event.keyCode == 81 && current!=0 && ev.ctrlKey) {
            preservationMethod('${(tmCmpMain.batchNo)!}','${(isAccets)!}');//旋转保存ctrl+q
        }
        if (event.keyCode == 81 && ev.altKey) {//alt + q 键 下载资源
            window.location.href = "${base}/assets/cmp_/download/gf?url=" + Url;
        }
    }


    // function onDocKeyDown(e) {
    //     e = e || window.event;
    //     if (e.ctrlKey && e.keyCode == 65) {
    //         re
    //     }
    //
    // }




</script>
<script>
    //图片滚轮放大缩小
    function bbimg(o) {
        /*$('#box1').css({
            "left":"50%",
            "top":"50%",
            "transform": "translate(-50%,-50%)"
        });*/
        var zoom = parseInt(o.style.zoom, 10) || 100;
        zoom += event.wheelDelta / 12;
        if(zoom>0) o.style.zoom = zoom + '%';
        return false;
    }

    // window.onload = function () {
    //
    // };



</script>


</body>
</html>

