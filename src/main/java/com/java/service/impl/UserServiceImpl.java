package com.java.service.impl;

import com.java.dao.BaseDao;
import com.java.dao.UserDao;
import com.java.dao.impl.UserDaoImpl;
import com.java.pojo.User;
import com.java.service.UserService;
import org.junit.Test;


import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lingfeng
 * @date 2021-10-22
 */
public class UserServiceImpl implements UserService {
    private UserDao userDao = null;

    public UserServiceImpl(){
        userDao = new UserDaoImpl();
    }

    public User getLoginUser(String userCode, String userPassword) {
        Connection connection = null;
        User user = null;
        try {
            connection = BaseDao.getConnection();
            user = userDao.getLoginUser(userCode, userPassword,connection);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            BaseDao.closeResource(connection,null,null);
        }
        return user;
    }

    public boolean updatePwd(int id, String pwd) {
        Connection connection = null;
        boolean falg = false;
        try {
            connection = BaseDao.getConnection();
            int result = userDao.updatePwd(id,pwd,connection);
            if(result > 0){
                falg = true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            BaseDao.closeResource(connection,null,null);
        }
        return falg;
    }

    public int getUserCount(String userName, String roleId) {
        Connection connection = null;
        int count = 0;
        try {
            connection = BaseDao.getConnection();
            count = userDao.getUserCount(userName, roleId,connection);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            BaseDao.closeResource(connection,null,null);
        }
        return count;
    }

    public List<User> getUserList(String userName, String roleId, int currentPageNo, int pageSize) {
        Connection connection = null;
        List<User> users = new ArrayList<User>();
        try {
            connection = BaseDao.getConnection();
            users = userDao.getUserList(userName,roleId,currentPageNo,pageSize,connection);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            BaseDao.closeResource(connection,null,null);
        }
        return users;
    }

    public User selectUserByCode(String userCode) {
        Connection connection = null;
        User user = null;
        try {
            connection = BaseDao.getConnection();
            user = userDao.selectUserByCode(userCode,connection);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            BaseDao.closeResource(connection,null,null);
        }
        return user;
    }

    public boolean addUser(User user) {
        Connection connection = null;
        boolean falg = false;
        try {
            connection = BaseDao.getConnection();
            int result = userDao.addUser(user,connection);
            if(result > 0){
                falg = true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            BaseDao.closeResource(connection,null,null);
        }
        return falg;
    }

    public boolean delUser(int id) {
        Connection connection = null;
        boolean falg = false;
        try {
            connection = BaseDao.getConnection();
            int result = userDao.delUser(id,connection);
            if(result > 0){
                falg = true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            BaseDao.closeResource(connection,null,null);
        }
        return falg;
    }

    public User getUserById(String id) {
        Connection connection = null;
        User user = null;
        try {
            connection = BaseDao.getConnection();
            user = userDao.getUserById(id,connection);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            BaseDao.closeResource(connection,null,null);
        }
        return user;
    }

    public boolean modify(User user) {
        Connection connection = null;
        boolean falg = false;
        try {
            connection = BaseDao.getConnection();
            int result = userDao.modify(user,connection);
            if(result > 0){
                falg = true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            BaseDao.closeResource(connection,null,null);
        }
        return falg;
    }

    @Test
    public void test(){
        UserServiceImpl userService = new UserServiceImpl();
        List<User> users= userService.getUserList("","",1,5);
        for (User user : users) {
            System.out.println(user.toString());
        }
    }
}
