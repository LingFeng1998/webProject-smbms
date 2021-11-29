package com.java.dao.impl;

import com.java.dao.BaseDao;
import com.java.dao.UserDao;
import com.java.pojo.User;
import com.mysql.jdbc.StringUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
            BaseDao.closeResource(null,preparedStatement,null);
        }
        return i;
    }

    public int getUserCount(String userName, String roleId, Connection connection) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int count = 0;
        if(connection != null){
            StringBuffer sql = new StringBuffer();
            ArrayList<Object> paramList = new ArrayList<Object>();
            sql.append("select count(1) as count from smbms_user a , smbms_role b where a.userRole = b.id ");
            if(!StringUtils.isNullOrEmpty(userName)){
                sql.append(" and a.userName like ? ");
                paramList.add("%"+userName+"%");
            }
            if(!StringUtils.isNullOrEmpty(roleId)){
                sql.append(" and b.id = ? ");
                paramList.add(roleId);
            }
            Object[] param = paramList.toArray();
            System.out.println(sql);
            resultSet = BaseDao.execute(connection, sql.toString(), param, resultSet, preparedStatement);
            if(resultSet.next()){
                count = resultSet.getInt("count");
            }
            BaseDao.closeResource(null,preparedStatement,resultSet);
        }
        return count;
    }

    public List<User> getUserList(String userName, String roleId, int currentPageNo, int pageSize, Connection connection) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<User> users = new ArrayList<User>();
        if (connection != null){
            StringBuffer sql = new StringBuffer();
            ArrayList<Object> paramList = new ArrayList<Object>();
            sql.append("select a.*,b.roleName as userRoleName from smbms_user a , smbms_role b where a.userRole = b.id ");
            if(!StringUtils.isNullOrEmpty(userName)){
                sql.append(" and a.userName like ? ");
                paramList.add("%"+userName+"%");
            }
            if(!StringUtils.isNullOrEmpty(roleId)){
                sql.append(" and b.id = ? ");
                paramList.add(roleId);
            }
            sql.append("order by a.creationDate desc limit ?,?");
            paramList.add((currentPageNo-1)*pageSize);
            paramList.add(pageSize);
            Object[] param = paramList.toArray();
            System.out.println(sql);
            resultSet = BaseDao.execute(connection, sql.toString(), param, resultSet, preparedStatement);
            while (resultSet.next()){
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setUserCode(resultSet.getString("userCode"));
                user.setUserName(resultSet.getString("userName"));
                user.setGender(resultSet.getInt("gender"));
                user.setBirthday(resultSet.getDate("birthday"));
                user.setPhone(resultSet.getString("phone"));
                user.setUserRole(resultSet.getInt("userRole"));
                user.setUserRoleName(resultSet.getString("userRoleName"));
                users.add(user);
            }
            BaseDao.closeResource(null,preparedStatement,resultSet);
        }
        return users;
    }
}
