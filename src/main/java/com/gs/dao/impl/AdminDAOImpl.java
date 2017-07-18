package com.gs.dao.impl;

import com.gs.bean.Admin;
import com.gs.common.bean.Pager;
import com.gs.dao.AbstractBaseDAO;
import com.gs.dao.AdminDAO;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Wang Genshen on 2017-07-17.
 */
public class AdminDAOImpl extends AbstractBaseDAO implements AdminDAO {

    public void updatePwd(Integer pk, String pwd) {
        getConnection();
        String sql = "update t_admin set password = ? where id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,pwd);
            preparedStatement.setInt(2,pk);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        close();
    }

    public void add(Admin admin) {

    }

    public void update(Admin admin) {

    }

    public void remove(Integer integer) {

    }

    public Admin queryById(Integer integer) {
        return null;
    }

    public List<Admin> queryAll() {
        return null;
    }

    public List<Admin> queryByPager(Pager<Admin> pager) {
        return null;
    }

    public int count() {
        return 0;
    }
}
