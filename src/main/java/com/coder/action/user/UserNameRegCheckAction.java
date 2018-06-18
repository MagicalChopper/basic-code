package com.coder.action.user;

import com.alibaba.fastjson.JSONObject;
import com.coder.action.BaseAction;
import com.coder.dao.UserDao;
import com.coder.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.coder.factory.DaoFactory.getUserDao;

public class UserNameRegCheckAction extends BaseAction {

    private static final UserDao userDao = getUserDao();

    @Override
    public String jump(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String username = request.getParameter("username");
        JSONObject result = new JSONObject();
        if(username==null||username.equals("")){
            result.put("succ",false);
        }else{
            User user = userDao.selectOneByUserName(username);
            if(user==null){
                result.put("succ",true);
            }else{
                result.put("succ",false);
            }
        }
        sendJsonMsg(response, result);
        return null;
    }
}
