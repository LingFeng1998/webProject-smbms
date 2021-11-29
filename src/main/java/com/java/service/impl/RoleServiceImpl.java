package com.java.service.impl;

import com.java.dao.BaseDao;
import com.java.dao.RoleDao;
import com.java.dao.UserDao;
import com.java.dao.impl.RoleDaoImpl;
import com.java.dao.impl.UserDaoImpl;
import com.java.pojo.Role;
import com.java.pojo.User;
import com.java.service.RoleService;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lingfeng
 * @date 2021-11-29
 */
public class RoleServiceImpl implements RoleService {
    private RoleDao roleDao = null;

    public RoleServiceImpl(){
        roleDao = new RoleDaoImpl();
    }

    public List<Role> getRoleList() {
        Connection connection = null;
        List<Role> roleList = new ArrayList<Role>();
        try {
            connection = BaseDao.getConnection();
            roleList = roleDao.getRoleList(connection);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            BaseDao.closeResource(connection,null,null);
        }
        return roleList;
    }

    @Test
    public void test(){
        RoleServiceImpl roleService = new RoleServiceImpl();
        List<Role> list= roleService.getRoleList();
        for (Role s : list) {
            System.out.println(s.getRoleName());
        }
    }
}
