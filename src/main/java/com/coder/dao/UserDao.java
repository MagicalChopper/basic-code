package com.coder.dao;

import com.coder.entity.User;

import java.sql.SQLException;
import java.util.List;

public interface UserDao {

    void regist(User user) throws Exception;

    User selectOneByUserName(String username) throws Exception;

    void remove();

    void update();

    List<User> selectAll();

    long getTotalCount() throws SQLException;

    List<User> getPageList(int start, int rows) throws SQLException;
}
