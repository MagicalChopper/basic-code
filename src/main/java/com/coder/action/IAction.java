package com.coder.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface IAction {

    String jump(HttpServletRequest request, HttpServletResponse response) throws Exception;
}
