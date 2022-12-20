<%--
  Created by IntelliJ IDEA.
  User: wangtongfa
  Date: 2022/12/12
  Time: 20:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page import="com.citi.mvnbook.account.service.*,
                org.springframework.context.ApplicationContext,
                org.springframework.web.context.support.WebApplicationContextUtils,
                java.*.*" %>


<html>
<head>
    <title>注册页面</title>
</head>
<body>

<%
    ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(config.getServletContext());
    AccountService accountService = (AccountService) context.getBean("accountService");
    String captchaKey = accountService.generateCaptchaKey();
%>

<div class="text-field">

    <form name="signup" action="signup" method="post">
        <label>账户ID：</label><label>
        <input type="text" name="id"/>
    </label> <br/>
        <label>Email：</label><label>
        <input type="text" name="email"/>
    </label> <br/>
        <label>显示名称：</label><label>
        <input type="text" name="name"/>
    </label> <br/>
        <label>密码：</label><label>
        <input type="text" name="password"/>
    </label> <br/>
        <label>确认密码：</label><label>
        <input type="text" name="confirm_password"/>
    </label> <br/>
        <label>验证码：</label><label>
        <input type="text" name="captcha_value"/>
    </label> <br/>
        <input type="hidden" name="captcha_key" value="<%=captchaKey %>"/>
        <img src="<%=request.getContextPath() %>/captcha_image?key=<%=captchaKey%>"/></br>
        <button>确认并提交</button>
    </form>
</div>

</body>
</html>
