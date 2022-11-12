package com.citi.mvnbook.account.email.exceptions;

import java.io.Serial;

/**
 * @author wangtongfa
 * @date 2022/10/18 22:21
 */
public class AccountEmailException extends RuntimeException{

    @Serial
    private static final long serialVersionUID = 3061814742484658368L;

    public AccountEmailException(String message) {
        super(message);
    }

    public AccountEmailException(String message, Throwable cause) {
        super(message, cause);
    }
}
