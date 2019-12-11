<#assign base=request.contextPath />

<#include "/layout.ftl">
<@body>
    <style type="text/css">
        body {
            background:url("${base}/assets/i/bg-body.jpg") #082b4f ;
        }
        .container{
            margin-top: 100px;
        }

        .form-signin {
            max-width: 330px;
            padding: 15px;
            margin: 0 auto;
        }
        .form-signin .form-signin-heading,
        .form-signin .checkbox {
            margin-bottom: 10px;
        }
        .form-signin .checkbox {
            font-weight: normal;
        }
        .form-signin .form-control {
            position: relative;
            height: auto;
            -webkit-box-sizing: border-box;
            -moz-box-sizing: border-box;
            box-sizing: border-box;
            padding: 10px;
            font-size: 16px;

        }
        .form-signin .form-control:focus {
            z-index: 2;
        }
        .form-signin input[type="text"] {
            margin-bottom: -1px;
            border-bottom-right-radius: 0;
            border-bottom-left-radius: 0;
            background-color: rgba(221, 230, 232, 0.8);
        }
        .form-signin input[type="password"] {
            margin-bottom: 10px;
            border-top-left-radius: 0;
            border-top-right-radius: 0;
            background-color: rgba(221, 230, 232, 0.8);
        }


        .form-signin-heading{
            padding-bottom: 10px;
            color: rgba(255, 255, 255, 0.8);
        }
        .signImg{
            width: 220px;
            height: 220px;
        }
        .sign{
            text-align: center;
        }

        .left {
            left: 5px;
            width: 1%;
            height: 1%
        }

        .left {
            margin-left: 10px;
            margin-top: 8px;
        }

        .loginTopLeftImg {
            width: 80px;
            height: 24px
        }
        .footer-contents {
            position: absolute;
            bottom: 14px;
            right: 10px;
            left: 10px;
            height: auto;
            -webkit-font-smoothing: antialiased;
            text-align: right;
            font-size: 13px;
            color: rgba(255, 255, 255, 0.8);
        }

        .warn-div {
            background-color: #F8F8F8;
            height: 38px;
            padding-top: 6px;
        }
        .warn-msg {
            margin-left: 100px;
            font-size:16px;

        }
        .download-text {
            font-size:18px;
            margin-left: 26px;
            font-weight:bold;
            font-style:italic;
            text-decoration:underline;
        }
    </style>
    <div id="warn" class="warn-div" style="display:none">
        <span class="warn-msg">浏览器版本太低，为保证系统良好的运行，请点击下载按钮下载安装谷歌浏览器（37及以上版本）访问。（如已安装，请重新打开支持的浏览器访问）</span>

        <a href="${(ftpFileUploadUrl)!}" class="download-text">下载</a>
    </div>
    <div id="warn2" class="warn-div" style="display:none">
        <span class="warn-msg">为保证系统良好的运行，建议使用IE9及以上或谷歌（37及以上版本）浏览器。（如已安装，请使用建议浏览器访问）</span>
        <a href="${(ftpFileUploadUrl)!}" class="download-text">下载</a>
    </div>

    <div class="left">
        <#if dict_("logoImg")??>
            <img alt class="loginTopLeftImg" src="${base}/assets/${(dict_("logoImg").value3)!}">
        <#else>
            <img alt class="loginTopLeftImg" src="${base}/assets/i/jj/jj_logo_left.png">
        </#if>
    </div>

    <div class="container">

        <form class="form-signin" action="${base}/login" method="POST">
            <div id="" class="sign">
                <#if dict_("logoImg")??>
                    <img alt class="signImg" src="${base}/assets/${(dict_("logoImg").value2)!}">
                <#else>
                    <img alt class="signImg" src="${base}/assets/i/jj/jj_logo.png">
                </#if>
                <h2 class="form-signin-heading"> </h2>
            </div>

            <#--
            <label for="inputEmail" class="sr-only">机构</label>
            <input type="text" id="orgId" name="orgId" class="form-control" placeholder="机构" >
            -->
            <label for="inputPassword" class="sr-only">用户名</label>
            <input type="text" id="loginName" name="loginName" class="form-control" placeholder="用户名" required>
            <label for="inputPassword" class="sr-only">密码</label>
            <input type="password" id="password" name="password" class="form-control" placeholder="密码" required>
            <div style="color:red">${(message)!}</div>
            <button class="btn btn-lg btn-primary btn-block" type="submit">登录</button>
        </form>
    </div>
    <div class="right">
        <div class="footer-contents">
            <span class="copyright">Copyright © 2018 jjb Inc. 保留所有权利。</span>
        </div>
    </div>
    <#if (ftpFileUploadUrl)??>
        <script type="text/javascript">

            $(function(){
                var sys = {};
                var ua = navigator.userAgent.toLowerCase();
                var s;
                (s = ua.match(/edge\/([\d.]+)/)) ? sys.edge = s[1] :
                    (s = ua.match(/rv:([\d.]+)\) like gecko/)) ? sys.ie = s[1] :
                        (s = ua.match(/msie ([\d.]+)/)) ? sys.ie = s[1] :
                            (s = ua.match(/firefox\/([\d.]+)/)) ? sys.firefox = s[1] :
                                (s = ua.match(/chrome\/([\d.]+)/)) ? sys.chrome = s[1] :
                                    (s = ua.match(/opera.([\d.]+)/)) ? sys.opera = s[1] :
                                        (s = ua.match(/version\/([\d.]+).*safari/)) ? sys.safari = s[1] : 0;

                if (sys.edge) {
                    $("#warn2").css('display','');
                }

                if (sys.ie)
                {
                    var IE5 = IE55 = IE6 = IE7 = IE8 = IE9 = false;
                    var reIE = new RegExp("msie (\\d+\\.\\d+);");
                    reIE.test(ua);
                    var fIEVersion = parseFloat(RegExp["$1"]);
                    IE55 = fIEVersion == 5.5;
                    IE6 = fIEVersion == 6.0;
                    IE7 = fIEVersion == 7.0;
                    IE8 = fIEVersion == 8.0;
                    IE9 = fIEVersion == 9.0;
                    if (IE55) {
                        $("#warn").css('display','');
                    }
                    if (IE6) {
                        $("#warn").css('display','');
                    }
                    if (IE7) {
                        $("#warn").css('display','');
                    }
                    if (IE8) {
                        $("#warn").css('display','');
                    }

                }

                if (sys.firefox) {
                    $("#warn2").css('display','');
                }

                if (sys.chrome) {
                    var vs = (sys.chrome).split('.')[0];
                    if(vs < 37) {
                        $("#warn").css('display','');
                    }
                }

                if (sys.opera) {
                    $("#warn2").css('display','');
                }

                if (sys.safari) {
                    $("#warn2").css('display','');
                }


            });

        </script>
    </#if>
</@body>

