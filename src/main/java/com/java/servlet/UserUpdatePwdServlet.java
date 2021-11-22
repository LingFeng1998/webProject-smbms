package com.java.servlet;

import com.alibaba.fastjson.JSONArray;
import com.java.pojo.User;
import com.java.service.impl.UserServiceImpl;
import com.java.util.Constants;
import com.mysql.jdbc.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

/**
 * @author lingfeng
 * @date 2021-11-19
 */
public class UserUpdatePwdServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if("savepwd".equals(req.getParameter("method"))){
            updatePwd(req,resp);
        }else if("pwdmodify".equals(req.getParameter("method"))){
            pwdModify(req,resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    public void updatePwd(HttpServletRequest req,HttpServletResponse resp) throws ServletException, IOException {
        Object user = req.getSession().getAttribute(Constants.USER_SESSION);
        String newpassword = req.getParameter("newpassword");
        if(user!=null && !StringUtils.isNullOrEmpty(newpassword)){
            UserServiceImpl userService = new UserServiceImpl();
            boolean falg = userService.updatePwd(((User) user).getId(), newpassword);
            if(falg){
                req.setAttribute("message","密码修改成功！");
                req.getSession().removeAttribute(Constants.USER_SESSION);
            }else{
                req.setAttribute("message","修改失败！");
            }
        }else {
            req.setAttribute("messsage","密码存在问题!");
        }
        req.getRequestDispatcher("pwdmodify.jsp").forward(req,resp);
    }

    public void pwdModify(HttpServletRequest req,HttpServletResponse resp) throws ServletException, IOException{
        HashMap<String, String> resultMap = new HashMap<String, String>();
        if (req.getSession() == null){
            resultMap.put("result","sessionerror");
        }else{
            Object user = req.getSession().getAttribute(Constants.USER_SESSION);
            String oldPwd = req.getParameter("oldpassword");
            if(StringUtils.isNullOrEmpty(oldPwd)){
                resultMap.put("result","error");
            }else if(oldPwd.equals(((User)user).getUserPassword())){
                    resultMap.put("result","true");
            }else{
                resultMap.put("result","false");
            }
        }
        try {
            resp.setContentType("application/json");
            PrintWriter writer = resp.getWriter();
            writer.write(JSONArray.toJSONString(resultMap));
            writer.flush();
            writer.close();
        }catch (IOException e){
            e.printStackTrace();
        }

    }
}