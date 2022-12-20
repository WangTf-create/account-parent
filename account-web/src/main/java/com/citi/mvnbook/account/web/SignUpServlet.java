package com.citi.mvnbook.account.web;

import com.citi.mvnbook.account.exceptions.AccountServiceException;
import com.citi.mvnbook.account.model.SignUpRequest;
import com.citi.mvnbook.account.service.AccountService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serial;

/**
 * @author wangtongfa
 * @date 2022/12/12 20:17
 */
public class SignUpServlet extends HttpServlet {
    @Serial
    private static final long serialVersionUID = -671820172232489248L;
    private ApplicationContext context;

    @Override
    public void init() throws ServletException {
        super.init();
        this.context = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        String email = req.getParameter("email");
        String name = req.getParameter("name");
        String password = req.getParameter("password");
        String confirmPassword = req.getParameter("confirm_password");
        String captchaKey = req.getParameter("captcha_key");
        String captchaValue = req.getParameter("captcha_value");
        if(StringUtils.isAnyBlank(id,email,name,password,confirmPassword,captchaKey,captchaValue)){
            resp.sendError(400,"Parameter Incomplete");
        }

        AccountService accountService = (AccountService) context.getBean("accountService");
        SignUpRequest signUpRequest = new SignUpRequest();
        signUpRequest.setId(id);
        signUpRequest.setEmail(email);
        signUpRequest.setName(name);
        signUpRequest.setPassword(password);
        signUpRequest.setConfirmPassword(confirmPassword);
        signUpRequest.setCaptchaKey(captchaKey);
        signUpRequest.setCaptchaValue(captchaValue);
        signUpRequest.setActivationServiceUrl(getServletContext().getRealPath("/")+"active");

        try {
            accountService.signUp(signUpRequest);
            resp.getWriter().write("Account is created, please check your mail box");
        } catch (AccountServiceException e) {
            resp.sendError(400,e.getMessage());
        }


    }
}
