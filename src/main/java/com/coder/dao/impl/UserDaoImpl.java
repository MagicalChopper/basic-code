package com.coder.dao.impl;

import com.coder.dao.UserDao;
import com.coder.entity.User;
import com.coder.util.jdbc.C3P0JdbcUtil;
import com.coder.util.md5.EncryptMd5Util;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;


public class UserDaoImpl implements UserDao {

    private static final Log log = LogFactory.getLog(UserDaoImpl.class);

    private static final DataSource dataSource = C3P0JdbcUtil.getDataSource();

    private static final QueryRunner qr = new QueryRunner(dataSource);

    @Override
    public void regist(User user) throws SQLException {
        String sql = "INSERT INTO user(username,password,email,head,gender,phone,userInfo) VALUES (?,?,?,?,?,?,?)";
        log.info("sql--->{"+sql+"}");
        Object[] params = {
                user.getUsername(),
                EncryptMd5Util.encryptMd5(user.getPassword()),
                user.getEmail(),
                user.getHead(),
                user.getGender(),
                user.getPhone(),
                user.getUserInfo()
        };
        qr.update(sql,params);
    }

    @Override
    public User selectOneByUserName(String username) throws SQLException {
        String sql = "select * from user where username = ? ";
        log.info("sql--->{"+sql+"}");
        return qr.query(sql,username,new BeanHandler<>(User.class));
    }

    @Override
    public void remove() {

    }

    @Override
    public void update() {

    }

    @Override
    public List<User> selectAll() {
        return null;
    }

    @Override
    public long getTotalCount() throws SQLException {
        QueryRunner qr = new QueryRunner(dataSource);
        String sql = " select count(*) from user ";
        long totalCount = qr.query(sql, new ScalarHandler<Long>(1));
        return totalCount;
    }

    @Override
    public List<User> getPageList(int start, int rows) throws SQLException {
        QueryRunner qr = new QueryRunner(dataSource);
        String sql = " select id,username,password,email,head,gender,phone,userInfo "
                + "from user where 1=1 LIMIT ?,?  ";
        Object[] params={
                start,
                rows
        };
        List<User> list = qr.query(sql,params,new BeanListHandler<>(User.class));
        return list;
    }
}
