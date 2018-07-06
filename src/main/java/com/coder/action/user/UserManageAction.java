package com.coder.action.user;

import com.coder.action.BaseAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: liuhao
 * @Descirption:
 * @Date: 2018/7/6_11:26
 */
public class UserManageAction extends BaseAction {
    @Override
    public String jump(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return "user/userManage";
    }
}
