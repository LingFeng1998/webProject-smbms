package com.java.servlet;

import com.java.pojo.User;
import com.java.service.impl.UserServiceImpl;
import com.java.util.Constants;
import com.mysql.jdbc.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class checkOldPwd extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getSession() == null){
            req.setAttribute("message","sessionerror");
        }else{
            Object user = req.getSession().getAttribute(Constants.USER_SESSION);
            String oldPwd = req.getParameter("oldpassword");
            if(StringUtils.isNullOrEmpty(oldPwd)){
                req.setAttribute("message","error");
            }else{
                UserServiceImpl userService = new UserServiceImpl();
                User loginUser = userService.getLoginUser(((User) user).getUserCode(), oldPwd);
                if (loginUser != null){
                    req.setAttribute("message","true");
                }else{
                    req.setAttribute("message","false");
                }
            }
        }
        req.getRequestDispatcher("pwdmodify.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
