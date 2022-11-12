package com.citi.mvnbook.account.persist.exceptions;

import java.io.Serial;

/**
 * @author wangtongfa
 * @date 2022/11/5 22:02
 */

public class AccountPersistException extends RuntimeException{

    @Serial
    private static final long serialVersionUID = -7445678931155719702L;

    public AccountPersistException(String message) {
        super(message);
    }

    public AccountPersistException(String message, Throwable cause) {
        super(message, cause);
    }
}
