package com.java.servlet;

import com.alibaba.fastjson.JSONArray;
import com.java.pojo.Role;
import com.java.pojo.User;
import com.java.service.UserService;
import com.java.service.impl.RoleServiceImpl;
import com.java.service.impl.UserServiceImpl;
import com.java.util.Constants;
import com.java.util.PageSupport;
import com.mysql.jdbc.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

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
        }else if("query".equals(req.getParameter("method"))){
            query(req,resp);
        }else if("ucexist".equals(req.getParameter("method"))){
            ucexist(req,resp);
        }else if("getrolelist".equals(req.getParameter("method"))){
            getrolelist(req,resp);
        }else if("add".equals(req.getParameter("method"))){
            add(req,resp);
        }else if("deluser".equals(req.getParameter("method"))){
            deluser(req,resp);
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

    public void query(HttpServletRequest req,HttpServletResponse resp) throws ServletException, IOException{
        String userName = req.getParameter("queryname");
        String roleId = req.getParameter("queryUserRole");
        String pageIndex = req.getParameter("pageIndex");
        int pageSize = 5;//默认页面大小为5
        int currentPageNo = 1;//默认查找第一页
        if(pageIndex!=null){
            currentPageNo = Integer.parseInt(pageIndex);
        }
        if("0".equals(roleId)){
            roleId = "";
        }

        UserServiceImpl userService = new UserServiceImpl();
        RoleServiceImpl roleService = new RoleServiceImpl();

        int totalCount = userService.getUserCount(userName, roleId);
        PageSupport pageSupport = new PageSupport();
        pageSupport.setCurrentPageNo(currentPageNo);
        pageSupport.setPageSize(pageSize);
        pageSupport.setTotalCount(totalCount);
        int totalPageCount = pageSupport.getTotalPageCount();//总页数

        if(currentPageNo<1){ //查询页数<1则查询第一页
            currentPageNo = 1;
        }else if(currentPageNo > totalPageCount){ //查询页数大于最大页数
            currentPageNo = totalPageCount;
        }

        List<User> userList = userService.getUserList(userName, roleId, currentPageNo, pageSize);
        List<Role> roleList = roleService.getRoleList();

        req.setAttribute("userList",userList);
        req.setAttribute("roleList",roleList);
        req.setAttribute("totalCount",totalCount);
        req.setAttribute("currentPageNo",currentPageNo);
        req.setAttribute("totalPageCount",totalPageCount);
        req.setAttribute("queryUserName",userName);
        req.setAttribute("queryUserRole",roleId);

        req.getRequestDispatcher("userlist.jsp").forward(req,resp);

    }

    private void ucexist(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        HashMap<String, String> resultMap = new HashMap<String, String>();
        String userCode = req.getParameter("userCode");
        if(StringUtils.isNullOrEmpty(userCode)){
            resultMap.put("userCode","null");
        }else{
            UserServiceImpl userService = new UserServiceImpl();
            User user = userService.selectUserByCode(userCode);
            if(user == null){
                resultMap.put("userCode","notexist");
            }else{
                resultMap.put("userCode","exist");
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

    private void getrolelist(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RoleServiceImpl roleService = new RoleServiceImpl();
        List<Role> roleList = roleService.getRoleList();
        try {
           // resp.setContentType("application/json");
            resp.setContentType("text/html;charset=UTF-8");
            PrintWriter writer = resp.getWriter();
            writer.write(JSONArray.toJSONString(roleList));
            writer.flush();
            writer.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private void add(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        String userCode = req.getParameter("userCode");
        String userName = req.getParameter("userName");
        String userPassword = req.getParameter("userPassword");
        String gender = req.getParameter("gender");
        String birthday = req.getParameter("birthday");
        String phone = req.getParameter("phone");
        String address = req.getParameter("address");
        String userRole = req.getParameter("userRole");

        User user = new User();
        user.setUserCode(userCode);
        user.setUserName(userName);
        user.setUserPassword(userPassword);
        user.setAddress(address);
        try {
            user.setBirthday(new SimpleDateFormat("yyyy-MM-dd").parse(birthday));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        user.setGender(Integer.valueOf(gender));
        user.setPhone(phone);
        user.setUserRole(Integer.valueOf(userRole));
        user.setCreationDate(new Date());
        user.setCreatedBy(((User) req.getSession().getAttribute(Constants.USER_SESSION)).getId());
        UserService userService = new UserServiceImpl();
        if(userService.addUser(user)){
            resp.sendRedirect(req.getContextPath() + "/jsp/user.do?method=query");
        }else{
            req.getRequestDispatcher("useradd.jsp").forward(req, resp);
        }
    }

    private void deluser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        String uid = req.getParameter("uid");
        int delId = 0;
        delId = Integer.parseInt(uid);
        HashMap<String, String> resultMap = new HashMap<String, String>();
        UserService userService = new UserServiceImpl();
        if(userService.delUser(delId)){
            resultMap.put("delResult","true");
        }else{
            resultMap.put("delResult","false");
        }
        resp.setContentType("application/json");
        PrintWriter writer = resp.getWriter();
        writer.write(JSONArray.toJSONString(resultMap));
        writer.flush();
        writer.close();
    }


}
