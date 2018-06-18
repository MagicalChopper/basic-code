package com.coder.controler;

import com.coder.action.IAction;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.coder.factory.ActionFactory.getActionFactory;

@WebServlet("*.do")
public class MainController extends HttpServlet{

    private static final Log log = LogFactory.getLog(MainController.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String servletPath = req.getServletPath();  //   /user/UserRegistAction.do
        String[] array = servletPath.split("[/.]");
        String path  = "com.coder.action."+array[1]+"."+array[2];
        IAction action = getActionFactory().getAction(path);
        String page = null;
        try {
            page = action.jump(req,resp);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(page==null){
            return;
        }
        req.getRequestDispatcher("../WEB-INF/pages/"+page+".jsp").forward(req,resp);
    }
}
