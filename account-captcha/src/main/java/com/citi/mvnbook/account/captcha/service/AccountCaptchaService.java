package com.citi.mvnbook.account.captcha.service;

import com.citi.mvnbook.account.captcha.exception.AccountCaptchaException;

import java.util.List;

/**
 * @author wangtongfa
 * @date 2022/11/20 15:05
 */
public interface AccountCaptchaService {

    String generateCaptchaKey() throws AccountCaptchaException;

    byte[] generateCaptchaImage(String captchaKey) throws AccountCaptchaException;

    boolean validateCaptcha(String captchaKey, String captchaValue) throws AccountCaptchaException;

    List<String> getPredefinedTexts() throws AccountCaptchaException;

    void setPredefinedTexts(List<String> predefinedTexts) throws AccountCaptchaException;
}
