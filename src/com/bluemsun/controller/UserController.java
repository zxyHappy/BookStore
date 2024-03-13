package com.bluemsun.controller;

import com.bluemsun.dao.UserDao;
import com.bluemsun.entity.Book;
import com.bluemsun.entity.Comment;
import com.bluemsun.entity.Page;
import com.bluemsun.entity.User;
import com.bluemsun.service.*;
import com.bluemsun.service.impl.*;
import com.bluemsun.util.JSONUtil;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;


@MultipartConfig(maxFileSize = 5*1024*1024,fileSizeThreshold = 6*1024*1024)
public class UserController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uri = request.getRequestURI();
        switch (uri){
            case "/user/register":
                doRegister(request,response);
                break;
            case "/user/login":
                doLogin(request,response);
                break;
            case "/user/photo":
                alertPhoto(request,response);
                break;
            case "/user/alert":
                alertMessage(request,response);
                break;
            case "/user/comment":
                addComment(request,response);
                break;
            default:response.sendError(404);
        }
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uri = request.getRequestURI();
        switch (uri){
            case "/user/center":
//                System.out.println(22222);
                getCenter(request,response);
//                System.out.println(11111);
                break;
            case "/user/search":
                searchBook(request,response);
                break;
            case "/user/exit":
                doExit(request,response);
                break;
            case "/user/delcomment":
                delComment(request,response);
                break;
            case "/user/message/confirm":
                confirmMessage(request,response);
                break;
            case "/user/message/del":
                delMessage(request,response);
                break;
            case "/user/alert/like":
                alertLike(request,response);
                break;
            case "/user/search/id":
                searchUserById(request,response);
                break;
            case "/user/ban":
                banUser(request,response);
                break;
            default:response.sendError(404);
        }
    }

    public void doRegister(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        LoginStatusService loginStatusService = new LoginStatusServiceImpl();
//        if(loginStatusService.checkLogin(request,response) || loginStatusService.checkManger(request,response)) {
//            try {
//                response.sendError(404);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            return;
//        }
        String json = JSONUtil.readJSON(request);
        User user = JSONUtil.jsonTo(json,User.class);
        if("".equals(user.getUserName()) || "".equals(user.getPassword()) || user.getPassword() == null || user.getUserName() == null || user.getNickName()==null || "".equals(user.getNickName()) || user.getTelephone() == null || "".equals(user.getTelephone())){
            response.getWriter().println(new Gson().toJson("输入信息有误"));
            return;
        }
        RegisterService registerService = new RegisterServiceImpl();
        Map<String,Object> map = registerService.returnMessage(user);
        response.getWriter().print(JSONUtil.mapTOJson(map));
    }

    public void doLogin(HttpServletRequest request,HttpServletResponse response) throws IOException {
//        LoginStatusService loginStatusService = new LoginStatusServiceImpl();
//        if(loginStatusService.checkLogin(request,response) || loginStatusService.checkManger(request,response)) {
//            try {
//                response.sendError(404);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            return;
//        }
        String json = JSONUtil.readJSON(request);
        User user = JSONUtil.jsonTo(json,User.class);
        if("".equals(user.getUserName()) || "".equals(user.getPassword()) || user.getPassword() == null || user.getUserName() == null){
            response.getWriter().println(new Gson().toJson("输入信息有误"));
            return;
        }
        LoginService loginService = new LoginServiceImpl();
        UserDao userDao = new UserDao();
        Map<String,Object> map = loginService.returnMessage(user);

        String msg = (String) map.get("message");
        if("登录成功".equals(msg)){
            request.getSession().setAttribute("login_status",userDao.getIdByName(user.getUserName()));// 登陆的用户id
//                System.out.println(userDao.getIdByName(user.getUserName()));
            request.getSession().setAttribute("user_status",map.get("status"));//判断是商家/用户
//                System.out.println(request.getSession().getId());
        }else{
            request.getSession().setAttribute("login_status",0);
            request.getSession().setAttribute("user_status",0);
        }
        response.getWriter().print(JSONUtil.mapTOJson(map));

    }

    public void doExit(HttpServletRequest request,HttpServletResponse response) throws IOException {
//        LoginStatusService loginStatusService = new LoginStatusServiceImpl();
//        if(!loginStatusService.checkLogin(request,response) && !loginStatusService.checkManger(request,response)) {
//            try {
//                response.sendError(404);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            return;
//        }
        request.getSession().setAttribute("login_status",0);
        request.getSession().setAttribute("user_status",0);
        String message = "账号已登出";
        Map<String,Object> map = new HashMap<>();
        map.put("message",message);
        response.getWriter().print(JSONUtil.mapTOJson(map));

    }

    //获取用户中心用户数据
    public void getCenter(HttpServletRequest request,HttpServletResponse response) throws IOException {
//        System.out.println(33333);
//        LoginStatusService loginStatusService = new LoginStatusServiceImpl();
//        if(loginStatusService.checkLogin(request,response) || loginStatusService.checkManger(request,response)){
            int userId =(int) request.getSession().getAttribute("login_status");
            String msg = "获取成功";
            UserCenterService userCenterService = new UserCenterServiceImpl();
            User user = userCenterService.getUser(userId);
            Map<String,Object> map = new HashMap<>();
            map.put("msg",msg);
            map.put("user",user);

            System.out.println(map);
            response.getWriter().println(JSONUtil.mapTOJson(map));

//        }else{
//            String msg = "获取失败，请重新登录";
//            Map<String,Object> map = new HashMap<>();
//            map.put("msg",msg);
//            map.put("user",null);
//            try {
//                System.out.println(JSONUtil.mapTOJson(map));
//                response.getWriter().println(JSONUtil.mapTOJson(map));
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
    }

    //更新头像,返回新图片url
    public void alertPhoto(HttpServletRequest request,HttpServletResponse response) throws IOException {
//        LoginStatusService loginStatusService = new LoginStatusServiceImpl();
//        if(loginStatusService.checkLogin(request,response) || loginStatusService.checkManger(request,response)){
            UserCenterService userCenterService = new UserCenterServiceImpl();
            String msg = userCenterService.alertPhoto(request); //正常的话msg为新图片的url
            Map<String,Object> map = new HashMap<>();
            map.put("msg",msg);
            response.getWriter().println(JSONUtil.mapTOJson(map));

//        }else {
//            try {
//                response.sendError(404);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
    }

    public void alertMessage(HttpServletRequest request,HttpServletResponse response) throws IOException {
//        LoginStatusService loginStatusService = new LoginStatusServiceImpl();
//        if(loginStatusService.checkLogin(request,response) || loginStatusService.checkManger(request,response)){
            int userId = (int)request.getSession().getAttribute("login_status");
            UserCenterService userCenterService = new UserCenterServiceImpl();
            String json = JSONUtil.readJSON(request);
            User user = JSONUtil.jsonTo(json,User.class);
            user.setUserId(userId);
            String msg = userCenterService.alertMessage(user);
            Map<String,Object> map = new HashMap<>();
            map.put("msg",msg);
            map.put("nickName",user.getNickName());
            map.put("Password",user.getPassword());
            map.put("Telephone",user.getTelephone());
            response.getWriter().println(JSONUtil.mapTOJson(map));

//        }else {
//            try {
//                response.sendError(404);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
    }

    //用户搜索书本
    public void searchBook(HttpServletRequest request,HttpServletResponse response) throws IOException {
//        LoginStatusService loginStatusService = new LoginStatusServiceImpl();
//        if(loginStatusService.checkLogin(request,response)){
            String value = request.getParameter("value");
            int index = Integer.parseInt(request.getParameter("index"));
            PageService pageService = new PageServiceImpl();
            Page<Book> page = pageService.bookPageByUser(value,index);
            Map<String,Object> map = new HashMap<>();
            map.put("totalPage",page.getTotalPage());
            map.put("bookList",page.getList());
            response.getWriter().println(JSONUtil.mapTOJson(map));
//        }else {
//            response.sendError(404);
//        }
    }

    //添加评论
    public void addComment(HttpServletRequest request,HttpServletResponse response) throws IOException {
//        LoginStatusService loginStatusService = new LoginStatusServiceImpl();
//        if(loginStatusService.checkLogin(request,response)){
            String json = JSONUtil.readJSON(request);
            Map<String,Object> map = JSONUtil.jsonToMap(json);
//            BigDecimal bigDecimal = new BigDecimal(map.get("orderId").toString());
//            int orderId = bigDecimal.intValue();
            Comment comment = new Comment();
            if(map.get("body") == null) {
                response.getWriter().println(new Gson().toJson("用户未填写评价"));
                return;
            }
            comment.setBody(map.get("body").toString());
            comment.setUserId((int)request.getSession().getAttribute("login_status"));
            BigDecimal bigDecimal = new BigDecimal(map.get("bookId").toString());
            comment.setBookId(bigDecimal.intValue());
            CommentService commentService = new CommentServiceImpl();
            String msg = commentService.addComment(comment);
            Map<String,Object> map1 = new HashMap<>();
            map1.put("msg",msg);
            response.getWriter().println(JSONUtil.mapTOJson(map1));
//        }else {
//            response.sendError(404);
//        }
    }


    //删除评论
    public void delComment(HttpServletRequest request,HttpServletResponse response) throws IOException {
//        LoginStatusService loginStatusService = new LoginStatusServiceImpl();
//        if(loginStatusService.checkLogin(request,response)){
            int userId = (int)request.getSession().getAttribute("login_status");
            int id = Integer.parseInt(request.getParameter("id"));
            int bookId = Integer.parseInt(request.getParameter("bookId"));
            CommentService commentService = new CommentServiceImpl();
            String msg = commentService.delComment(userId,bookId,id);
            Map<String,Object> map = new HashMap<>();
            map.put("msg",msg);
            response.getWriter().println(JSONUtil.mapTOJson(map));
//        }
    }

    //确认通知
    public void confirmMessage(HttpServletRequest request,HttpServletResponse response) throws IOException {
//        LoginStatusService loginStatusService = new LoginStatusServiceImpl();
//        if(loginStatusService.checkLogin(request,response) || loginStatusService.checkManger(request,response)){
            int id = Integer.parseInt(request.getParameter("id"));
            int userId = (int)request.getSession().getAttribute("login_status");
            MessageService messageService = new MessageServiceImpl();
            String msg = messageService.confirmMessage(userId,id);
            Map<String,Object> map = new HashMap<>();
            map.put("msg",msg);
            response.getWriter().println(JSONUtil.mapTOJson(map));
//        }
    }

    //删除通知
    public void delMessage(HttpServletRequest request,HttpServletResponse response) throws IOException {
//        LoginStatusService loginStatusService = new LoginStatusServiceImpl();
//        if(loginStatusService.checkLogin(request,response) || loginStatusService.checkManger(request,response)){
            int id = Integer.parseInt(request.getParameter("id"));
            int userId = (int)request.getSession().getAttribute("login_status");
            MessageService messageService = new MessageServiceImpl();
            String msg = messageService.delMessage(userId,id);
            Map<String,Object> map = new HashMap<>();
            map.put("msg",msg);
            response.getWriter().println(JSONUtil.mapTOJson(map));
//        }
    }

    //更新点赞
    public void alertLike(HttpServletRequest request,HttpServletResponse response) throws IOException {
//        LoginStatusService loginStatusService = new LoginStatusServiceImpl();
//        if(loginStatusService.checkLogin(request,response)){
            int userId = (int)request.getSession().getAttribute("login_status");
            int bookId = Integer.parseInt(request.getParameter("bookId"));
            LikeService likeService = new LikeServiceImpl();
            int likeStatus = likeService.alertLike(userId,bookId);
            String msg;
            if(likeStatus == 0) msg = "取消成功";
            else msg = "点赞成功";
            Map<String,Object> map = new HashMap<>();
            map.put("msg",msg);
            map.put("likeStatus",likeStatus);
            response.getWriter().println(JSONUtil.mapTOJson(map));
//        }
    }

    //商家用id精准查询用户
    public void searchUserById(HttpServletRequest request,HttpServletResponse response) throws IOException {
//        LoginStatusService loginStatusService = new LoginStatusServiceImpl();
//        if(loginStatusService.checkManger(request,response)){
            int userId = Integer.parseInt(request.getParameter("userId"));
            UserControlService userControlService = new UserControlServiceImpl();
            String msg;
            User user = userControlService.searchUserById(userId);
            if(user==null) msg = "无此用户";
            else msg = "查询成功";
            Map<String,Object> map = new HashMap<>();
            map.put("msg",msg);
            map.put("user",user);
            response.getWriter().println(JSONUtil.mapTOJson(map));
//        }
    }

    public void banUser(HttpServletRequest request,HttpServletResponse response) throws IOException {
//        LoginStatusService loginStatusService = new LoginStatusServiceImpl();
//        if(loginStatusService.checkManger(request,response)){
            int userId = Integer.parseInt(request.getParameter("userId"));
            UserControlService userControlService = new UserControlServiceImpl();
            String msg = userControlService.alertUserStatus(userId);
            Map<String,Object> map = new HashMap<>();
            map.put("msg",msg);
            response.getWriter().println(JSONUtil.mapTOJson(map));
//        }
    }

}
