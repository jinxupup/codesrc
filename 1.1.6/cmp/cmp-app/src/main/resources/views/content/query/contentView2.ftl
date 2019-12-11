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
                $(".preservation").css("display", "block");
            } else {
                $(".preservation").css("display", "none");
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
                $(".preservation").css("display", "block");
            } else {
                $(".preservation").css("display", "none");
            }
        }

        function closePage() {
            window.close();
            window.opener = null;
            window.open('', '_self');
            window.close();
        }

        function backPage() {
            window.location.href = "${base}/cmp_/contentListPage";
        }

        var Index = 0;
        var Size = 0;
        var Path = null;
        var Url = null;
        window.onload = function () {
            nextImg(0);
            $('#alertTxt').html('').show().delay(1500).fadeOut();
            document.getElementById('newIvImg').ondblclick = function () {
                current = (current + 90) % 360;
                this.style.transform = 'rotate(' + current + 'deg)';
            };
            //图片移动
            var box1 = document.getElementById("box1");
            box1.onmousedown = function (event) {
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
        function changeImg(url, subTypeDesc, updateUser, formatDate, index, size, path, subtype) {
            // if (current != 0) {
            //     var show = confirm("你修改了图片,是否需要保存?");
            //     if (show) {
            //         return;
            //     }
            // }
            //解决图片旋转后切换图片,后面图片也被旋转
            while (current != 0) {
                if (current > 0) {
                    nrotate();
                } else if (current < 0) {
                    srotate();
                }
            }
            if ('vidType' == subtype) {
                showVideo(url, subTypeDesc, updateUser, formatDate, index, size, path, subtype);
                return;
            }
            //图片居中
            $('#box1').css({
                "left": "50%",
                "top": "50%",
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

     /*       //下方小图片选中提示
            for (i = 0; i <= Size; i++) {
                $("#smallImg" + i).css("border", "");
            }
            $("#smallImg" + Index).css("border", "2px solid #7000ff");
            // $('#newIvImg').css("height", "100px").css("width", "100px");*/
        }

        function showVideo(url, subTypeDesc, updateUser, formatDate, index, size, path, subtype) {
            var viewerDiv = $('#viewer');
            $('#newIvImg').hide();
            var vd = $('#viedeoIv');
            if (vd.length > 0) {
                vd.attr("src", url);
                vd.show();
            } else {
                viewerDiv.append('<video id=\"viedeoIv\" src=\"' + url + '\" controls=\"controls\"></video>');
                $('#viedeoIv').css({position: "absolute", top: "5px", left: "5px"});
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
        //自己
        function uploadButton(batchNo, isAccets,parm) {
            window.location.href = "${base}/assets/cmp_/uploadPage?batchNo=" + batchNo + "&isAccets=" + isAccets +"&parm="+parm;
        }

        //外部免密
        function uploadButtons(batchNo, isAccets,parm) {
            window.location.href = "${base}/cmp_/uploadPage?batchNo=" + batchNo + "&isAccets=" + isAccets +"&parm="+parm;
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
                    data: {"batchNo": batchNo, "path": Path, "isAccets": isAccets},
                    async: false,
                    success: function (res) {
                        var json = JSON.stringify(res);
                        var a = eval('(' + json + ')');
                        if (a.imageNum == true) {
                            $('#alertTxt2').html('删除成功').show().delay(1500).fadeOut();
                            setTimeout("Refresh()", 1500);
                        } else {
                            alert("删除失败 ！！！");
                        }
                    }
                });
            }
        }

        //TODO外部免密
        function delectImages(batchNo, isAccets,fileId) {
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
                            $('#alertTxt2').html('删除成功').show().delay(250).fadeOut();
                            setTimeout("Refresh()", 250);
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

        //刷新页面方法
        function Refresh() {
            window.location.reload();
        }

    </script>

</head>
<body>
<table border="0" sytle="solid #eaeaea;" width="100%">
    <tr>
        <td style="height: 15px"></td>
    </tr>
    <#assign start=0>
    <#assign end=5>
    <#assign val="" />
    <tr>
        <td style="font-size:16px;font-family: \5FAE\8F6F\96C5\9ED1;text-align:center; width: 16%; height: 730px;padding-top: 15px;padding-left: 6px" valign="top">
            <div id="menuDiv" style="overflow-x: auto; height: 80%; width: 100%; float:left;">
                <div style="text-align:left;">
                    <span style="font-size:11px;font-family: \5FAE\8F6F\96C5\9ED1;color:#337ab7;">姓名:</span>
                    <span class="_border">${(tmCmpMain.name)!}</span>
                </div>
                <div style="text-align:left;">
                    <span style="font-size:11px;font-family: \5FAE\8F6F\96C5\9ED1;color:#337ab7;">证件号:</span>
                    <span class="_border">${(tmCmpMain.idNo)!}</span>
                </div>
                <div class="_bordd">00000000000000000000000000000000000</div>
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
                    <div style="text-align:left;">
                        <span style="font-size:11px;font-family: \5FAE\8F6F\96C5\9ED1;color:#337ab7;">当前批次号: </span>
                        <span class="_border" id="formatDate"/>${imageNum}
                    </div>

                </div>

                <div class="_bordd">00000000000000000000000000000000000</div>
                <#if allContextMap?? >
                    <#if (allContextMap?size>0)>
                        <#list allContextMap?keys as k1>
                            <div style="text-align:left;">
                                <span style="font-size:11px;font-family: \5FAE\8F6F\96C5\9ED1;color:#337ab7;">批次号:</span>
                                <div class="_bordd"><span style="font-size:11px;font-family: \5FAE\8F6F\96C5\9ED1;color:black;">${k1}
                                </div>
                                </span>
                                <#assign catalogIndex=-1>
                                <#list allContextMap[k1]?keys as k2>
                                    <div class="vtitle"><em class="v"></em>${k2}</div>
                                    <div class="vcon" style="display: none;">
                                        <ul class="vconlist clearfix">
                                            <#list allContextMap[k1][k2] as tmCmpContent>
                                                <#assign catalogIndex +=1>
                                                <li>
                                                    <a onclick="changeImg('${tmCmpContent.contRelPath!}${tmCmpContent.contAbsPath!}',
                                                            '${tmCmpContent.subTypeDesc!}','${tmCmpContent.updateUser!}','${tmCmpContent.formatDate!}',
                                                            '${catalogIndex}',
                                                            '${allContextList?size}',
                                                            '${tmCmpContent.contAbsPath!}','${tmCmpContent.supType!}')"
                                                       style="text-align:left;"> ${tmCmpContent.formatDate}</a>
                                                    <input type="hidden" id="imagePath"
                                                           value="${tmCmpContent.contRelPath!''}${tmCmpContent.contAbsPath!''}"/>
                                                    <#if (isAccets)??&& isAccets=='true'>
                                                    <#--//免密删除-->
                                                        <button title="删除当前浏览图片" style="cursor: pointer;color: #fff; background-color: #D9534F;border-color: #D9534F;top-bar-button: 90px;vertical-align: -2px;
                         border-radius: 4px;padding: 1px;width: 66px;height: 21.5px;font-size: 12px;font-family: 黑体;text-align: center;border: none;line-height: 23px"
                                                                onclick="delectImages('${(tmCmpMain.batchNo)!}','${(isAccets)!}','${tmCmpContent.id!}')">删除图片
                                                        </button>
                                                    </#if>

                                                </li>
                                            </#list>
                                        </ul>
                                    </div>
                                </#list>
                            </div>
                        </#list>
                    </#if>
                </#if>
                <div style="height: 150px; width: 100%"></div>

                <br/>

            </div>
            <div style="text-align:left;padding-top: 150px;">
            </div>
            <table style="width: 100%">
                <tr style="width: 100%;height: 25px">
                </tr>
                <tr style="width: 100%">
                    <td width="15px"></td>
                    <#if (isAccets)??&& isAccets=='true'>
                    <td style="width: 20%;float: left">
                            <button title="跳转到上传页面" style="cursor: pointer;color: #fff; background-color: #2b84d9;border-color: #2b84d9;top-bar-button: 90px;vertical-align: -2px;
                         border-radius: 4px;padding: 1px;width: 40px;height: 21.5px;font-size: 12px;font-family: 黑体;text-align: center;border: none;line-height: 23px"
                                    onclick="uploadButton('${imageNum}','${(isAccets)!}','${(parm)!}')">上传
                            </button>
                    </td>
                    <td style="width: 20%;float: left">
                            <div style="width: 100%;align-self: center;">
                                <button title="退出本页面" style="cursor: pointer;color: #fff; background-color: #ff0000;border-color: #454545;top-bar-button: 90px;vertical-align: -2px;
                         border-radius: 4px;padding: 1px;width: 40px;height: 21.5px;font-size: 12px;font-family: 黑体;text-align: center;border: none;line-height: 23px"
                                        onclick="closePage()">退出
                                </button>
                            </div>
                    </td>
                    </#if>
                </tr>
            </table>
        </td>
        <td style="width: 83%;height: 730px;">

            <div id="divone" style="width:100%;height:100%;">
                <div id="viewer" style="width:100%;height:100%;overflow: hidden;max-height: 659px;max-width: 1357px;text-align: center" class="viewer">
                    <span style="font-size:14px;font-family: 黑体;color:#0000FF;">&nbsp;&nbsp;快捷键: ← →左右切换图片 ; ↑ ↓ 旋转图片 ; alt+q 或 鼠标右键 下载资源</span>
                    <span id="alertTxt" style="z-index: 1" class="cotentText"></span>
                    <span id="alertTxt2" style="z-index: 1" class="cotentText"></span>
                    <div id="box1" style="position: absolute;" onmousewheel="return bbimg(this)">
                        <img id="newIvImg" style="z-index: -2;">
                    </div>
                    <div id="content">
                        <a id="prev" href="javascript:" onclick="nextImg(-1)"><</a>
                        <a id="next" href="javascript:" onclick="nextImg(1)">></a>
                    </div>
                </div>
            </div>
        </td>
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
            $(".contUl ul").css("left", left_move * "-600" + "px");
        }
    });

    // 右箭头  .rightBon
    $("body").on("click", ".rightBon", function () {
        console.log(left_move);
        if (left_move <= Size /15) {
            left_move++;
            $(".contUl ul").css("left", left_move * "-600" + "px");
        }

    });
    //        左右图片切换
    function nextImg(i) {
        Index = Index - 0;
        var index = 0;
        if (current == 0) {
            if (i > 0) {
                if (Index > 6) {
                    if (move_two <= Size - 10) {
                        move_two++;
                        $(".contUl ul").css("left", move_two * "-60" + "px");
                    }
                }

                //加
                index = Index++;
            } else {
                if (Index > 6) {
                    if (move_two > 0) {
                        move_two--;
                        $(".contUl ul").css("left", move_two * "-60" + "px");
                    }
                }

                //减
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

        if (Index <= 0) {
            $('#prev').css("cursor", "not-allowed");
            $('#prev').attr("title", "已经是第一张了");
        }
        if (Index >= maxsize - 1) {
            $('#next').css("cursor", "not-allowed");
            $('#next').attr("title", "已经是最后一张了");
        }
        if (Index < 0) {
            Index++;
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
                '${tmCmpContent.contAbsPath!}', '${tmCmpContent.supType!}');
        }
        </#list>
    }
    //快捷键
    document.onkeydown = function (ev) {
        ss(ev)
    };
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
        if (event.keyCode == 81 && ev.altKey) {//alt + q 键
            window.location.href = "${base}/assets/cmp_/download/gf?url=" + Url;
        }
    }
    //图片滚轮放大缩小
    function bbimg(o) {
        var zoom = parseInt(o.style.zoom, 10) || 100;
        zoom += event.wheelDelta / 12;
        if (zoom > 0) o.style.zoom = zoom + '%';
        return false;
    }
</script>
</body>
</html>

