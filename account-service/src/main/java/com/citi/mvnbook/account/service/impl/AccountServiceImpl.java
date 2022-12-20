package com.citi.mvnbook.account.service.impl;

import com.citi.mvnbook.account.captcha.exception.AccountCaptchaException;
import com.citi.mvnbook.account.captcha.service.AccountCaptchaService;
import com.citi.mvnbook.account.captcha.util.RandomGenerator;
import com.citi.mvnbook.account.email.exceptions.AccountEmailException;
import com.citi.mvnbook.account.email.service.AccountEmailService;
import com.citi.mvnbook.account.exceptions.AccountServiceException;
import com.citi.mvnbook.account.model.SignUpRequest;
import com.citi.mvnbook.account.persist.exceptions.AccountPersistException;
import com.citi.mvnbook.account.persist.model.Account;
import com.citi.mvnbook.account.persist.service.AccountPersistService;
import com.citi.mvnbook.account.service.AccountService;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author wangtongfa
 * @date 2022/12/4 21:07
 */
@Service
public class AccountServiceImpl implements AccountService {

    @Resource
    private AccountPersistService accountPersistService;

    @Resource
    private AccountEmailService accountEmailService;
    @Resource
    private AccountCaptchaService accountCaptchaService;

    @Override
    public String generateCaptchaKey() throws AccountServiceException {
        try {
            return accountCaptchaService.generateCaptchaKey();
        } catch (AccountCaptchaException e) {
            throw new AccountServiceException("Unable to generate Captcha Key.", e);
        }
    }

    @Override
    public byte[] generateCaptchaImage(String captchaKey) throws AccountServiceException {
        try {
            return accountCaptchaService.generateCaptchaImage(captchaKey);
        } catch (AccountCaptchaException e) {
            throw new AccountServiceException("Unable to generate Captcha Image.", e);
        }

    }

    private final Map<String,String>activationMap= Maps.newHashMap();

    @Override
    public void signUp(SignUpRequest signUpRequest) {
        try {
            if(!signUpRequest.getPassword().equals(signUpRequest.getConfirmPassword())){
                throw new AccountServiceException("2 passwords do not match.");
            }
            if(!accountCaptchaService.validateCaptcha(signUpRequest.getCaptchaKey(), signUpRequest.getCaptchaValue())){
                throw new AccountServiceException("Incorrect captcha.");
            }
            Account account = new Account();
            account.setId(signUpRequest.getId());
            account.setEmail(signUpRequest.getEmail());
            account.setPassword(signUpRequest.getPassword());
            account.setActivated(Boolean.FALSE);
            account.setName(signUpRequest.getName());
            accountPersistService.createAccount(account);

            String activationId = RandomGenerator.getRandom();
            activationMap.put(activationId,account.getId());
            String link = signUpRequest.getActivationServiceUrl().endsWith("/")
                    ? signUpRequest.getActivationServiceUrl() + activationId : signUpRequest.getActivationServiceUrl() + "?key=" + activationId;

            accountEmailService.sendMail(account.getEmail(),"Please Active Your Account",link);

        }catch (AccountServiceException e){
            throw new AccountServiceException("Unable to validate captcha",e);
        }catch (AccountPersistException e) {
            throw new AccountServiceException("Unable to create account",e);
        }catch (AccountEmailException e){
            throw new AccountServiceException("Unable to send activation mail",e);
        }

    }

    @Override
    public void activate(String activeNumber) {
        String accountId = activationMap.get(activeNumber);
        if(accountId == null){
            throw new AccountServiceException("Invalid account activation ID");
        }
        try {
            Account account = accountPersistService.readAccount(accountId);
            account.setActivated(true);
            accountPersistService.updateAccount(account);
        } catch (AccountPersistException e) {
            throw new AccountServiceException("Unable to active account.");
        }

    }

    @Override
    public void login(String username, String password) {
        try {
            Account account = accountPersistService.readAccount(username);
            if(account == null){
                throw new AccountServiceException("Account does not exist.");
            }
            if(!account.isActivated()){
                throw new AccountServiceException("Account is disabled");
            }
            if(account.getPassword().equals(password)){
                throw new AccountServiceException("Incorrect password");
            }
        } catch (AccountPersistException e) {

            throw new AccountServiceException("Unable to login in.");
        }

    }
}
