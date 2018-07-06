package com.coder.action;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Map;

public abstract class BaseAction implements IAction {
    private static final Log log = LogFactory.getLog(BaseAction.class);

    public void sendJsonMsg(HttpServletResponse response, Map result){
        response.setContentType("text/html;charset=UTF-8");
        try {
            PrintWriter out = response.getWriter();
            out.println(result.toString());
            out.flush();
        } catch (Exception e) {
            log.error("通过response向前台输出json数据时报错:"+e.getMessage());
        }
    }

}
