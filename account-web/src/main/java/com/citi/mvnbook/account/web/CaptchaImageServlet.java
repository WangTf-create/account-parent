package com.citi.mvnbook.account.web;

import com.citi.mvnbook.account.exceptions.AccountServiceException;
import com.citi.mvnbook.account.service.AccountService;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serial;

/**
 * @author wangtongfa
 * @date 2022/12/7 12:40
 */
public class CaptchaImageServlet extends HttpServlet {
    @Serial
    private static final long serialVersionUID = 8168697438208563490L;

    private ApplicationContext context;

    @Override
    public void init() throws ServletException {
        super.init();
        this.context = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, BeansException {
        String key = req.getParameter("key");
        if (key == null || key.length() == 0) {
            resp.sendError(400, "No Captcha Key Found");
        } else {
            AccountService accountService = (AccountService) context.getBean("accountService");
            try {
                resp.setContentType("image/jpeg");
                ServletOutputStream outputStream = resp.getOutputStream();
                outputStream.write(accountService.generateCaptchaImage(key));
                outputStream.close();
            } catch (AccountServiceException e) {
                resp.sendError(404, e.getMessage());
            }
        }
    }
}
