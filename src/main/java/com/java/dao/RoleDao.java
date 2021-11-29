package com.java.dao;

import com.java.pojo.Role;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @author lingfeng
 * @date 2021-11-29
 */
public interface RoleDao {
    public List<Role> getRoleList(Connection connection) throws SQLException;
}
