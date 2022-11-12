package com.citi.mvnbook.account.persist.service;

import com.citi.mvnbook.account.persist.exceptions.AccountPersistException;
import com.citi.mvnbook.account.persist.model.Account;

/**
 * @author wangtongfa
 * @date 2022/11/5 22:01
 */
public interface AccountPersistService {

    Account createAccount(Account account) throws AccountPersistException;

    Account readAccount(String id) throws AccountPersistException;

    Account updateAccount(Account account) throws AccountPersistException;

    Account deleteAccount(String id) throws AccountPersistException;
}
