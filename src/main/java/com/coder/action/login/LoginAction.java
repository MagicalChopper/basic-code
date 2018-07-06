package com.coder.action.login;

import com.alibaba.fastjson.JSONObject;
import com.coder.action.BaseAction;
import com.coder.dao.UserDao;
import com.coder.entity.User;
import com.coder.util.md5.EncryptMd5Util;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.coder.factory.DaoFactory.getUserDao;

public class LoginAction extends BaseAction {

    private static final Log log = LogFactory.getLog(LoginAction.class);

    private static final UserDao userDao = getUserDao();

    @Override
    public String jump(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String identifyingCode = request.getParameter("identifyingCode");
        String sessionIdentifyingCode = (String) request.getSession().getAttribute("identifyingCode");
        JSONObject result = new JSONObject();
        if(identifyingCode!=null&&identifyingCode.equals(sessionIdentifyingCode)){
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            User user = userDao.selectOneByUserName(username);
            if(user==null){
                result.put("succ",false);
                result.put("msg","用户名或密码出错！！！");
                sendJsonMsg(response,result);
            }else{
                if(user.getPassword().equals(EncryptMd5Util.encryptMd5(password))){
                    result.put("succ",true);
                    result.put("msg","登陆成功");
                    request.getSession().setAttribute("user",user);//设置登录信息
                    sendJsonMsg(response,result);
                }else{
                    result.put("succ",false);
                    result.put("msg","用户名或密码出错！！！");
                    sendJsonMsg(response,result);
                }
            }
        }else{
            result.put("msg","非法登录");
            sendJsonMsg(response,result);
        }
        return null;
    }
}
