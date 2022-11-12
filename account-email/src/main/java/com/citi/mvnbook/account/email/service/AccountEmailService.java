package com.citi.mvnbook.account.email.service;

import com.citi.mvnbook.account.email.exceptions.AccountEmailException;

/**
 * @author wangtongfa
 * @date 2022/10/18 22:16
 */
public interface AccountEmailService {

    void sendMail(String to,String subject,String htmlText) throws AccountEmailException;

}
