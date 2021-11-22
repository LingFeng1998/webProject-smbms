package com.java.service.impl;

import com.java.dao.BaseDao;
import com.java.dao.UserDao;
import com.java.dao.impl.UserDaoImpl;
import com.java.pojo.User;
import com.java.service.UserService;
import org.junit.Test;


import java.sql.Connection;
import java.sql.SQLException;

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

//    @Test
//    public void test(){
//        UserServiceImpl userService = new UserServiceImpl();
//        User user = userService.getLoginUser("wen", "qwqw");
//        System.out.println(user.getUserPassword());
//    }
}
