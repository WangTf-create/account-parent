package com.citi.mvnbook.account.service;

import com.citi.mvnbook.account.exceptions.AccountServiceException;
import com.citi.mvnbook.account.model.SignUpRequest;

/**
 * @author wangtongfa
 * @date 2022/12/4 21:02
 */
public interface AccountService {

    String generateCaptchaKey() throws AccountServiceException;

    byte[]  generateCaptchaImage(String captchaKey) throws AccountServiceException;

    void signUp(SignUpRequest signUpRequest);

    void activate(String activeNumber);

    void login(String username, String password);

}
