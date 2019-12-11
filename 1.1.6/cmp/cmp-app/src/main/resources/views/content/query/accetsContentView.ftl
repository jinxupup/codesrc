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
    <link type="text/css" rel="stylesheet" href="${base}/assets/css/iviewer.css"/>
    <link type="text/css" rel="stylesheet" href="${base}/assets/css/jcrop.css"/>
    <script>
        var Index = 0;
        var Size = 0;
        var Path = null;

        window.onload = function () {
            var current = 0;//ondblclick
            document.getElementById('ivImg').ondblclick = function () {
                current = (current + 90) % 360;
                this.style.transform = 'rotate(' + current + 'deg)';
            }
        };

        //加载第一张小图片
        //        window.onload = function () {
        //            nextImg(0)
        //        };

        //改变类型时显示的图片
        function changeImg(url, subTypeDesc, updateUser, formatDate, index, size, path) {
            Index = index;
            Size = size;
            Path = path;
//            alert("index " + Index);
            console.log(url);
            document.getElementById('ivImg').src = url;
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
        //外部免密
        function uploadButton(batchNo, isAccets) {
            window.location.href = "/cmp-app/assets/cmp_/uploadPage?batchNo=" + batchNo + "&isAccets=" + isAccets;
        }

        //自己
        function uploadButtons(batchNo, isAccets) {
            window.location.href = "/cmp-app/cmp_/uploadPage?batchNo=" + batchNo + "&isAccets=" + isAccets;
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
                    url: "/cmp-app/cmp_/deleteFile",
                    type: "post",
                    dataType: "json",
                    data: {"batchNo": batchNo, "path": Path, "isAccets": isAccets},
                    async: false,
                    success: function (res) {
                        var json = JSON.stringify(res);
                        var a = eval('(' + json + ')');
                        if (a.imageNum == true) {
                            alert("ok 删除成功 ");
                            window.location.reload();
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
                    url: "/cmp-app/assets/cmp_/deleteFile",
                    type: "post",
                    dataType: "json",
                    data: {"batchNo": batchNo, "path": Path, "isAccets": isAccets},
                    async: false,
                    success: function (res) {
                        var json = JSON.stringify(res);
                        var a = eval('(' + json + ')');
                        if (a.imageNum == true) {
                            alert("ok 删除成功 ");
                            window.location.reload();
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
        })
    </script>
    <style>


        #content {
            width: 1490px;
            height: 1px;
            position: relative;
            margin: 1px auto;
        }

        #content a {
            line-height: 666px;
            width: 60px;
            height: 700px;
            background: rgba(240, 240, 240, 0.5);
            border: 1px #fff solid;
            font-size: 70px;
            font-family: 黑体;
            font-weight: bold;
            color: #fff;
            text-align: center;
            position: absolute;
            top: 1px;
            text-decoration: none;
        }

        #content a:hover {
            color: #4cb9c6;
        }

        #prev {
            position: absolute;
            left: 0%;
        }

        #next {
            position: absolute;
            right: 0%;
        }

        #img1 {
            width: 600px;
            height: 501px;
        }


        li {
            display: inline;
        }

        .ThumbPicBorder {
            width: 100%;
            text-align: center;
            height: 52px;
        }

        * {
            margin: 0;
            padding: 0;
            list-style-type: none;
        }

        a, img {
            border: 0;
        }

        body {
            font: 12px/180% Arial, Helvetica, sans-serif, "\5FAE\8F6F\96C5\9ED1";
        }

        a, a:hover {
            text-decoration: none;
        }

        /*收缩菜单*/
        .v {
            float: right;
            width: 14px;
            height: 14px;
            overflow: hidden;
            background: url(${base}/assets/i/jj/vicon.png) no-repeat;
            display: inline-block;
            margin-top: -5px;
            margin-bottom: -5px;
        }

        .v01 {
            background-position: 0 0;
        }

        .v02 {
            background-position: 0 -16px;;
        }

        .vtitle {
            height: 35px;
            background: #ffffff;
            line-height: 35px;
            border: 1px solid #ccb6a9;
            margin-top: -1px;
            padding-left: 20px;
            font-size: 15px;
            color: #4d4d4d;
            font-family: "\5FAE\8F6F\96C5\9ED1";
            cursor: pointer;
        }

        .vtitle em {
            margin: 10px 10px 0 0;
        }

        .vconlist {
            background: #f9ffef;
        }

        .vconlist li a {
            height: 30px;
            line-height: 30px;
            padding-left: 30px;
            display: block;
            font-size: 14px;
            color: #0906bf;
            font-family: "\5FAE\8F6F\96C5\9ED1";
            cursor: pointer;
        }

        .vconlist li.select a, .vconlist li a:hover {
            color: #ed4948;
            text-decoration: none;
        }
        /*<span id="lalala" style="font-size:30px; font-family: 楷体;color:#ff4b3f;"></span>*/
        /*<span id="hahaha" style="font-size:30px; font-family: 楷体;color:#ff4b3f;"></span>*/
        .cotentText{
            position: absolute;
            display: none;
            left: 50%;
            top: 30px;
            transform: translateX(-50%);
            font-size: 30px;
            line-height: 30px;
            text-align: center;
            border-radius: 7px;
            padding: 5px 15px;
        }
        #lalala {
            text-align: center;
            margin: 100px auto;
            font-family: 新宋体;
            font-size: 66px;
            color: white;
            text-shadow: 0 0 20px #fefcc9, 10px -10px 30px #feec85, -10px -10px 40px #ffae34, 20px -20px 50px #ec760c, -20px -10px 60px #cd4606, 0 -10px 70px #973716, 10px -10px 80px #451b0e;
        }

    </style>
</head>
<body>
<table border="0" sytle="solid #eaeaea;" width="100%;">
    <tr>
        <td style="height: 70px;"></td>

    </tr>
    <tr>
        <#assign val="" />
        <td style="font-size:14px;font-family: \5FAE\8F6F\96C5\9ED1;text-align:center; width: 15%;" valign="top"
        ">
        <div style="text-align:left;">
            <span style="font-size:10px;font-family: \5FAE\8F6F\96C5\9ED1;color:#337ab7;">姓名:</span>
            <span style="font-size:10px;font-family: \5FAE\8F6F\96C5\9ED1;color:black;">${(tmCmpMain.name)!}</span>
        </div>
        <div style="text-align:left;">
            <span style="font-size:10px;font-family: \5FAE\8F6F\96C5\9ED1;color:#337ab7;">证件号:</span>
            <span style="font-size:10px;font-family: \5FAE\8F6F\96C5\9ED1;color:black;">${(tmCmpMain.idNo)!}</span>
        </div>
        <div style="text-align:left;">
            <span style="font-size:10px;font-family: \5FAE\8F6F\96C5\9ED1;color:#337ab7;">批次号:</span>
            <span style="font-size:10px;font-family: \5FAE\8F6F\96C5\9ED1;color:black;">${(tmCmpMain.batchNo)!}</span>
        </div>


        <#--        <div style="text-align:left;">
                    <span style="font-size:10px;face:'\5FAE\8F6F\96C5\9ED1';color:black;">备注:${(remark)!}</span>
                </div>-->


        <hr/input
        <div style="text-align:left;word-wrap: break-word;word-break: break-all;">
            <div style="text-align:left;">
                <span style="font-size:14px;font-family: \5FAE\8F6F\96C5\9ED1;color:#337ab7;">>>>>影像详情<<<<<</span>
            </div>
            <#--<span id="contDetail" style="font-size:10px;face:'\5FAE\8F6F\96C5\9ED1';color:#0000FF;"></span>-->

            <div style="text-align:left;">
                <span style="font-family: \5FAE\8F6F\96C5\9ED1; font-size:10px;color:#337ab7;">类型: </span>
                <span id="subTypeDesc" style="font-size:10px;font-family: \5FAE\8F6F\96C5\9ED1;color:black;"/>
            </div>
            <div style="text-align:left;">
                <span style="font-size:10px;font-family: \5FAE\8F6F\96C5\9ED1;color:#337ab7;">维护人: </span>
                <span id="updateUser" style="font-size:10px;font-family: \5FAE\8F6F\96C5\9ED1;color:black;"/>
            </div>
            <div style="text-align:left;">
                <span style="font-size:10px;font-family: \5FAE\8F6F\96C5\9ED1;color:#337ab7;">上传时间: </span>
                <span id="formatDate" style="font-size:10px;font-family: \5FAE\8F6F\96C5\9ED1';color:black;"/>
            </div>
        </div>


        <div style="text-align:left;">
            <#if (isAccets)??&& isAccets=='true'>
            <#--//免密删除-->
                <button style="color: #fff; background-color: #D9534F;border-color: #D9534F;top-bar-button: 90px;vertical-align: -2px;
                         border-radius: 4px;padding: 1px;width: 66px;height: 21.5px;font-size: 1px;font-family: 黑体;text-align: center;border: none;line-height: 23px"
                        onclick="delectImages('${(tmCmpMain.batchNo)!}','${(isAccets)!}')">删除图片
                </button>
            <#else>
            <#--<input style="background-color: #ff4b3f" type="button" value="删除图片" onclick="delectImage('${(tmCmpMain.batchNo)!}','${(isAccets)!}')">-->
                <button style="color: #fff; background-color: #D9534F;border-color: #D9534F;top-bar-button: 90px;vertical-align: -2px;
                         border-radius: 4px;padding: 1px;width: 66px;height: 21.5px;font-size: 1px;font-family: 黑体;text-align: center;border: none;line-height: 23px"
                        onclick="delectImage('${(tmCmpMain.batchNo)!}','${(isAccets)!}')">删除图片
                </button>
            </#if>
            <#if (isAccets)??&& isAccets=='true'>
                <button style="color: #fff; background-color: #2b84d9;border-color: #2b84d9;top-bar-button: 90px;vertical-align: -2px;
                         border-radius: 4px;padding: 1px;width: 66px;height: 21.5px;font-size: 1px;font-family: 黑体;text-align: center;border: none;line-height: 23px"
                        onclick="uploadButton('${(tmCmpMain.batchNo)!}','${(isAccets)!}')">上传
                </button>
            <#else>
                <button style="color: #fff; background-color: #2b84d9;border-color: #2b84d9;top-bar-button: 90px;vertical-align: -2px;
                         border-radius: 4px;padding: 1px;width: 40px;height: 21.5px;font-size: 1px;font-family: 黑体;text-align: center;border: none;line-height: 23px"
                        onclick="uploadButtons('${(tmCmpMain.batchNo)!}','${(isAccets)!}')">上传
                </button>
            </#if>

        </div>
        <hr/>
        <br/>
        </div>
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
                        <a onclick="changeImg('${tmCmpContent.contRelPath!}${tmCmpContent.contAbsPath!}',
                                '${tmCmpContent.subTypeDesc!}','${tmCmpContent.updateUser!}','${tmCmpContent.formatDate!}',
                                '${k2_index}',
                                '${allContextList?size}',
                                '${tmCmpContent.contAbsPath!}')"
                           style="width:100%;text-align:left;"> ${tmCmpContent.formatDate}</a>
                        <input type="hidden" id="imagePath"
                               value="${tmCmpContent.contRelPath!''}${tmCmpContent.contAbsPath!''}"/>
                    </li>
                </#list>
            </ul>
        </div>
        <div style="text-align:center;clear:both">


            <#--                    <#list allContextMap[k1][k2] as tmCmpContent>&lt;#&ndash;实体类集合<List>&ndash;&gt;
                                        <button type="button" onclick="changeImg('${tmCmpContent.contRelPath!}${tmCmpContent.contAbsPath!}',
                                                '类型:${tmCmpContent.subTypeDesc!}; 由${tmCmpContent.updateUser!}; 于  ${tmCmpContent.formatDate!} 上传')"
                                                style="width:100%;text-align:left;">${tmCmpContent.subTypeDesc}</button>
                                        <input type="hidden" id="imagePath" value="${tmCmpContent.contRelPath!''}${tmCmpContent.contAbsPath!''}"/>
                                </#list>-->
            </#list>
            </#list>
            </#if>
            </#if>
        </div>
        </td>


        <td style="width:80%;height: 750px;">

            <div id="divone" style="width:100%;height:100%;display: block">
                <div id="viewer" style="width:100%;height:100%;" class="viewer">
                     <span style="font-size:10px;font-family: \5FAE\8F6F\96C5\9ED1;color:#0000FF;">
                    &nbsp;&nbsp;滑动鼠标滚轮即放大缩小图片，90 度旋转请点击图片；
                   </span>
                    <span id="lalala" class="cotentText"></span>
                    <div id="content">
                        <a id="prev" href="javascript:" onclick="nextImg(-1)" title="上一张"><</a>
                        <a id="next" href="javascript:" onclick="nextImg(1)" title="下一张">></a>
                    </div>
                </div>
                <div class="SpaceLine"></div>
                <div class="HS15"></div>
                <div class="main">
                    <div class="ThumbPicBorder">
                        <div class="jCarouselLite FlLeft">


                            <#if allContextList?? >
                            <#--<img src="${base}/assets/i/jj/ArrowL.jpg" id="btnPrev" class="FlLeft"-->
                            <#--style="cursor:pointer;height: 50px;" onclick="nextImg(-1)"/>-->
                                <#list allContextList as tmCmpContent>
                                    <li rel=${tmCmpContent_index+2}>
                                        <#--下方小图片changeImg(url, subTypeDesc, updateUser, formatDate, index, size, path)-->
                                        <a onclick="changeImg('${tmCmpContent.contRelPath!}${tmCmpContent.contAbsPath!}',
                                                '${tmCmpContent.subTypeDesc!}','${tmCmpContent.updateUser!}','${tmCmpContent.formatDate!}',
                                                '${tmCmpContent_index}',
                                                '${allContextList?size}',
                                                '${tmCmpContent.contAbsPath!}')">

                                            <img src=${tmCmpContent.contRelPath!}${tmCmpContent.contAbsPath!} style="width:50px;height:50px"></a>
                                    </li>
                                </#list>
                            <#--<img src="${base}/assets/i/jj/ArrowR.jpg" id="btnNext" class="FlLeft"-->
                            <#--style="cursor:pointer;height: 50px;" onclick="nextImg(1)"/>-->


                            <#--<#list allContextList as tmCmpContent>-->

                            <#--<#if Index=='${tmCmpContent_index}'>-->
                            <#--</#if>-->
                            <#--</#list>-->


                            </#if>


                        </div>
                    </div>
                </div>
                <div class="HS15"></div>
            </div>


        </td>
        <td style="width:5%;height: 750px;"></td>
        </hr></tr>
</table>
<script type="text/javascript">
    /*    var  list =document.getElementById("list");
        //获取ul对象
        list=list.getElementsByTagName("li")
        //获取ul下的li对象,是数组集合
        for (var i=0,l=list.length;i<l;i++){
            list[i].rel=i
        }*/
    //缩略图滚动事件
    $(".jCarouselLite").jCarouselLite({
        btnNext: "#btnNext",
        btnPrev: "#btnPrev",
        scroll: 1,
        speed: 240,
        circular: true,
        visible: 6
    });


</script>
<script>


    <#--'${tmCmpContent.contRelPath!}${tmCmpContent.contAbsPath!}'-->
    <#--,'类型:${tmCmpContent.subTypeDesc!}; 由 ${tmCmpContent.updateUser!}; 于  ${tmCmpContent.formatDate!} 上传',-->
    <#--'${tmCmpContent_index}'-->


    //        左右图片切换
    function nextImg(i) {
        Index = Index - 0;
        var haha = 0;
        if (i > 0) {
            //加
            haha = Index++;
        } else {
            //减
            haha = Index--;
        }
//        alert(haha);
        var maxsize = '${allContextList?size}';
        var pass;
        var add;
        <#list allContextList as tmCmpContent>
        var lala = '${tmCmpContent_index}';
        lala = lala - 0;
        if (Index < 0) {
            Index++;
            // var spanInfo = document.getElementById('lalala');
            // spanInfo.innerHTML = "89787879787987789789798789";
            // $('<div>').appendTo('body').html('123456').show().delay(1500).fadeOut();
            $('#lalala').html('第一张').show().delay(1500).fadeOut();
        }
        if (Index > maxsize - 1) {
            Index--;
            $('#lalala').html('最后一张').show().delay(1500).fadeOut();
        }
        if (Index === lala) {
            changeImg('${tmCmpContent.contRelPath!}${tmCmpContent.contAbsPath!}',
                '${tmCmpContent.subTypeDesc!}', '${tmCmpContent.updateUser!}', '${tmCmpContent.formatDate!}',
                lala,
                maxsize,
                '${tmCmpContent.contAbsPath!}');


            // changeImg(url, subTypeDesc, updateUser, formatDate, index, size, path)
            <#--pass = '${tmCmpContent.contRelPath!}${tmCmpContent.contAbsPath!}';-->
            <#--std= '${tmCmpContent.subTypeDesc!}';-->
            <#--udu = '${tmCmpContent.updateUser!}';-->
            <#--fmd= '${tmCmpContent.formatDate!}';-->
            <#--haha = '${tmCmpContent.contAbsPath!}';-->
            <#--changeImg(pass, std, udu, fmd, lala, maxsize, haha);-->
        }

        </#list>
//        function changeImg(url, imageInfo, index, size)

    }


</script>


</body>
</html>

