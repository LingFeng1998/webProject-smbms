package com.java.dao;

import com.java.pojo.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @author lingfeng
 * @date 2021-10-22
 */
public interface UserDao {
    public User getLoginUser(String userCode,String userPassword, Connection connection) throws SQLException;

    public int updatePwd(int id,String pwd,Connection connection)throws SQLException;

    public int getUserCount(String userName,String roleId,Connection connection) throws SQLException;

    public List<User> getUserList(String userName,String roleId,int currentPageNo, int pageSize,Connection connection) throws SQLException;

    public User selectUserByCode(String userCode, Connection connection) throws SQLException;

    public int addUser(User user, Connection connection) throws SQLException;

    public int delUser(int id,Connection connection) throws SQLException;

    public User getUserById(String id,Connection connection) throws SQLException;

    int modify(User user, Connection connection) throws SQLException;
}
