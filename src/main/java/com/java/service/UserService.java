package com.java.service;

import com.java.pojo.User;

/**
 * @author lingfeng
 * @date 2021-10-22
 */
public interface UserService {
    public User getLoginUser(String userCode,String userPassword);

    public boolean updatePwd(int id ,String pwd);
}
