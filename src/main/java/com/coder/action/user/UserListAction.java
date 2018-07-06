package com.coder.action.user;

import com.alibaba.fastjson.JSONObject;
import com.coder.action.BaseAction;
import com.coder.dao.UserDao;
import com.coder.entity.User;
import com.coder.util.convert.RequestParamToEntity;
import com.coder.util.page.PageBean;
import net.sf.json.JSONArray;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.coder.factory.DaoFactory.getDaoFactory;

/**
 * @Author: liuhao
 * @Descirption:
 * @Date: 2018/7/6_15:02
 */
public class UserListAction extends BaseAction {

    private static final UserDao userDao = getDaoFactory().getUserDao();

    @Override
    public String jump(HttpServletRequest request, HttpServletResponse response) throws SQLException {

        PageBean pb  =  RequestParamToEntity.convert(request, PageBean.class);

        long totalCount = userDao.getTotalCount();
        List<User> list = userDao.getPageList(pb.getStart(),pb.getRows());

        JSONObject result = new JSONObject();
        JSONArray jsonArray = JSONArray.fromObject(list);
        result.put("total",totalCount);
        result.put("rows",jsonArray);
        result.put("success",true);
        sendJsonMsg(response, result);
        return null;
    }
}
