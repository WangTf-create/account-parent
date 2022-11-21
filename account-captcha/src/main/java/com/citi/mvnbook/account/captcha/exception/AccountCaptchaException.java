package com.citi.mvnbook.account.captcha.exception;

import java.io.Serial;

/**
 * @author wangtongfa
 * @date 2022/11/20 20:07
 */
public class AccountCaptchaException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1766515308968595800L;

    public AccountCaptchaException(String message) {
        super(message);
    }

    public AccountCaptchaException(String message, Throwable cause) {
        super(message, cause);
    }
}
