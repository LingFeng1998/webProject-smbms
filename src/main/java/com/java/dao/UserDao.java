package com.java.dao;

import com.java.pojo.User;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author lingfeng
 * @date 2021-10-22
 */
public interface UserDao {
    public User getLoginUser(String userCode,String userPassword, Connection connection) throws SQLException;

    public int updatePwd(int id,String pwd,Connection connection)throws SQLException;
}
