package com.coder.factory;

import com.coder.dao.UserDao;
import com.coder.dao.impl.UserDaoImpl;

import java.text.DateFormat;

public class DaoFactory {

    private static DaoFactory daoFactory = null;

    private DaoFactory(){}

    public static DaoFactory getDaoFactory(){
        if(daoFactory==null){
            return new DaoFactory();
        }
        return daoFactory;
    }

    public static UserDao getUserDao(){
        return new UserDaoImpl();
    }
}
