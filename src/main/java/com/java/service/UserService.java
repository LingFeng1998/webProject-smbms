package com.java.service;

import com.java.pojo.User;

import java.sql.Connection;
import java.util.List;

/**
 * @author lingfeng
 * @date 2021-10-22
 */
public interface UserService {
    public User getLoginUser(String userCode,String userPassword);

    public boolean updatePwd(int id ,String pwd);

    public int getUserCount(String userName,String roleId);

    public List<User> getUserList(String userName, String roleId, int currentPageNo, int pageSize);
}
