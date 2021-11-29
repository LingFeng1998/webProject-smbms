package com.java.dao.impl;

import com.java.dao.BaseDao;
import com.java.dao.RoleDao;
import com.java.pojo.Role;
import com.java.pojo.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lingfeng
 * @date 2021-11-29
 */
public class RoleDaoImpl implements RoleDao {
    public List<Role> getRoleList(Connection connection) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Role> roleList = new ArrayList<Role>();
        if(connection != null){
            String sql = "select * from smbms_role";
            resultSet = BaseDao.execute(connection,sql,new Object[]{},resultSet,preparedStatement);
            while (resultSet.next()){
                Role role = new Role();
                role.setId(resultSet.getInt("id"));
                role.setRoleCode(resultSet.getString("rolecode"));
                role.setRoleName(resultSet.getString("rolename"));
                roleList.add(role);
            }
            BaseDao.closeResource(null,preparedStatement,resultSet);
        }
        return roleList;
    }
}
