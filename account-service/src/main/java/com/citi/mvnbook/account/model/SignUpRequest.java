package com.citi.mvnbook.account.model;

import lombok.Getter;
import lombok.Setter;

/**
 * @author wangtongfa
 * @date 2022/12/4 21:05
 */
@Getter
@Setter
public class SignUpRequest {
   private String password;
   private String confirmPassword;
   private boolean activation;

   private String captchaValue;
   private String captchaKey;
   private String id;
   private String email;
   private String name;
   private String activationServiceUrl;


}
