package com.coder.action.user;


import com.alibaba.fastjson.JSONObject;
import com.coder.action.BaseAction;
import com.coder.dao.UserDao;
import com.coder.entity.User;
import com.coder.util.convert.RequestParamToEntity;
import com.coder.util.email.SendEmailThread;
import com.coder.util.thread.ThreadPool;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.coder.factory.DaoFactory.getDaoFactory;

public class UserRegistAction extends BaseAction {


    private static final UserDao userDao = getDaoFactory().getUserDao();

    @Override
    public String jump(HttpServletRequest request, HttpServletResponse response) throws Exception {
        User user = RequestParamToEntity.convert(request,User.class);
        userDao.regist(user);
//        new Thread(new SendEmailThread(user.getEmail(),"系统注册信息","用户已注册成功！")).start();//单线程
        ThreadPool.getInstance().addTask(new SendEmailThread(user.getEmail(),"系统注册信息","用户已注册成功！"));
        JSONObject result = new JSONObject();
        result.put("succ",true);
        result.put("msg","注册成功！！！");
        sendJsonMsg(response,result);
        return null;
    }
}
