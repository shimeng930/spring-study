package com.shim.annotation;

/**
 * Created by xn064961 on 2017/1/16.
 */
public class PwdUtils {

    @UserCase(id = 24, desc = "pwd not be empty")
    public boolean validatePwd (String pwd) {
        return (pwd.matches("\\w*\\d\\w*"));
    }

    @UserCase(id = 24)
    public String encryptPwd (String pwd) {
        return new StringBuilder(pwd).reverse().toString();
    }
}
