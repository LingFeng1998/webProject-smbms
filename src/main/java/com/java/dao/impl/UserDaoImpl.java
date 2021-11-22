package com.java.dao.impl;

import com.java.dao.BaseDao;
import com.java.dao.UserDao;
import com.java.pojo.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author lingfeng
 * @date 2021-10-22
 */
public class UserDaoImpl implements UserDao {

    /**
     * 获取登录的用户的信息
     * @param userCode
     * @param connection
     * @return
     * @throws SQLException
     */
    public User getLoginUser(String userCode,String userPassword, Connection connection) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        User user = null;
        if(connection != null){
            String sql = "select * from smbms_user where userCode=? and userPassword=?";
            Object[] params = {userCode,userPassword};
            resultSet = BaseDao.execute(connection, sql, params, resultSet,preparedStatement);
            if (resultSet.next()) {
                user = new User();
                user.setId(resultSet.getInt("id"));
                user.setUserCode(resultSet.getString("userCode"));
                user.setUserName(resultSet.getString("userName"));
                user.setUserPassword(resultSet.getString("userPassword"));
                user.setGender(resultSet.getInt("gender"));
                user.setBirthday(resultSet.getDate("birthday"));
                user.setPhone(resultSet.getString("phone"));
                user.setAddress(resultSet.getString("address"));
                user.setUserRole(resultSet.getInt("userRole"));
                user.setCreatedBy(resultSet.getInt("createdBy"));
                user.setCreationDate(resultSet.getTimestamp("creationDate"));
                user.setModifyBy(resultSet.getInt("modifyBy"));
                user.setModifyDate(resultSet.getTimestamp("modifyDate"));
            }
            BaseDao.closeResource(null,preparedStatement,resultSet);
        }
        return user;
    }

    public int updatePwd(int id, String pwd, Connection connection) throws SQLException {
        PreparedStatement preparedStatement = null;
        int i = 0;
        if(connection != null){
            String sql = "update smbms_user set userPassword=? where id=? ";
            Object[] params = {pwd,id};
            i = BaseDao.execute(connection,preparedStatement,sql,params);
            BaseDao.closeResource(connection,preparedStatement,null);
        }
        return i;
    }
}
