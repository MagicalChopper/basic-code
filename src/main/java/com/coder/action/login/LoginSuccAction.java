package com.coder.action.login;

import com.coder.action.BaseAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginSuccAction extends BaseAction {
    @Override
    public String jump(HttpServletRequest request, HttpServletResponse response) throws RuntimeException {
        return "main";
    }
}
