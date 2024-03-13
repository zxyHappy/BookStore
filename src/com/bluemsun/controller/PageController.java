package com.bluemsun.controller;

import com.bluemsun.entity.*;
import com.bluemsun.service.PageService;
import com.bluemsun.service.impl.PageServiceImpl;
import com.bluemsun.util.JSONUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class PageController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uri = request.getRequestURI();
        switch (uri){
            case "/page/book":
                bookSearch(request,response);
                break;
            case "/page/search":
                getSearch(request,response);
                break;
            case "/page/user/comment":
                commentByUser(request,response);
                break;
            case "/page/book/comment":
                commentByBook(request,response);
                break;
            case "/page/message/manger":
                showMessage(request,response);
                break;
            case "/page/message/user":
                showMessageByUser(request,response);
                break;
            case "/page/user":
                userSearch(request,response);
                break;
            case "/page/book/type":
                showBookByType(request,response);
                break;
            default:response.sendError(404);
        }
    }

    public void userSearch(HttpServletRequest request,HttpServletResponse response) throws IOException {
//        LoginStatusService loginStatusService = new LoginStatusServiceImpl();
//        if(loginStatusService.checkManger(request,response)){
            String value = request.getParameter("value");
            int index = Integer.parseInt(request.getParameter("index"));
            PageServiceImpl pageService = new PageServiceImpl();
            Page<User> page = pageService.userPage(value,index);
            Map<String,Object> map = new HashMap<>();
            map.put("totalPage",page.getTotalPage());
            map.put("index",page.getCurrentPage());
            map.put("userList",page.getList());
            response.getWriter().println(JSONUtil.mapTOJson(map));
//        }
    }


    //商家界面搜索页面书本分页
    public void bookSearch(HttpServletRequest request,HttpServletResponse response) throws IOException {
//        LoginStatusService loginStatusService = new LoginStatusServiceImpl();
//        if(loginStatusService.checkManger(request,response)){
            String value = request.getParameter("value");
            int index = Integer.parseInt(request.getParameter("index"));
            PageService pageService = new PageServiceImpl();
            Page<Book> page = pageService.bookPage(value,index);
            Map<String,Object> map = new HashMap<>();
            map.put("totalPage",page.getTotalPage());
            map.put("index",page.getCurrentPage());
            map.put("bookList",page.getList());
            response.getWriter().println(JSONUtil.mapTOJson(map));
//        }else {
//            try {
//                response.sendError(404);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
    }


    //加载搜索页面默认显示（商家）（已弃用）
    public void getSearch(HttpServletRequest request,HttpServletResponse response) throws IOException {
//        LoginStatusService loginStatusService = new LoginStatusServiceImpl();
//        if(loginStatusService.checkManger(request,response)){
            int index = Integer.parseInt(request.getParameter("index"));
            PageService pageService = new PageServiceImpl();
            Page<Book> page = pageService.getSearch(index);
            Map<String,Object> map = new HashMap<>();
            map.put("totalPage",page.getTotalPage());
            map.put("index",page.getCurrentPage());
            map.put("bookList",page.getList());
            response.getWriter().println(JSONUtil.mapTOJson(map));
//        }else {
//            try {
//                response.sendError(404);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
    }

    // 书本页面评论
    public void commentByBook(HttpServletRequest request,HttpServletResponse response) throws IOException {
//        LoginStatusService loginStatusService = new LoginStatusServiceImpl();
//        if(loginStatusService.checkLogin(request,response)){
            int bookId = Integer.parseInt(request.getParameter("bookId"));
            int index = Integer.parseInt(request.getParameter("index"));
            PageService pageService = new PageServiceImpl();
            Page<Comment> page = pageService.showCommentByBook(bookId,index);
            Map<String,Object> map = new HashMap<>();
            map.put("totalPage",page.getTotalPage());
            map.put("index",page.getCurrentPage());
            map.put("commentList",page.getList());
            response.getWriter().println(JSONUtil.mapTOJson(map));
    }
//    }

    //用户界面查看评论
    public void commentByUser(HttpServletRequest request,HttpServletResponse response) throws IOException {
//        LoginStatusService loginStatusService = new LoginStatusServiceImpl();
//        if(loginStatusService.checkLogin(request,response)){
            int userId = (int) request.getSession().getAttribute("login_status");
            int index = Integer.parseInt(request.getParameter("index"));
            PageService pageService = new PageServiceImpl();
            Page<Comment> page = pageService.showCommentByUser(userId,index);
            Map<String,Object> map = new HashMap<>();
            map.put("totalPage",page.getTotalPage());
            map.put("index",page.getCurrentPage());
            map.put("commentList",page.getList());
            response.getWriter().println(JSONUtil.mapTOJson(map));
//        }
    }

    //商家查看通知
    public void showMessage(HttpServletRequest request,HttpServletResponse response) throws IOException {
//        LoginStatusService loginStatusService = new LoginStatusServiceImpl();
//        if(loginStatusService.checkManger(request,response)){
            int index = Integer.parseInt(request.getParameter("index"));
            PageService pageService = new PageServiceImpl();
            Page<Message> page = pageService.showMessage(index);
            Map<String,Object> map = new HashMap<>();
            map.put("totalPage",page.getTotalPage());
            map.put("index",page.getCurrentPage());
            map.put("messageList",page.getList());
            response.getWriter().println(JSONUtil.mapTOJson(map));
//        }
    }

    //用户查看通知
    public void showMessageByUser(HttpServletRequest request,HttpServletResponse response) throws IOException {
//        LoginStatusService loginStatusService = new LoginStatusServiceImpl();
//        if(loginStatusService.checkLogin(request,response)){
            int userId = (int) request.getSession().getAttribute("login_status");
            int index = Integer.parseInt(request.getParameter("index"));
            PageService pageService = new PageServiceImpl();
            Page<Message> page = pageService.showMessageByUser(userId,index);
            Map<String,Object> map = new HashMap<>();
            map.put("totalPage",page.getTotalPage());
            map.put("index",page.getCurrentPage());
            map.put("messageList",page.getList());
//            System.out.println(map);
            response.getWriter().println(JSONUtil.mapTOJson(map));
//        }
    }

    //书本分类查看分页展示
    public void showBookByType(HttpServletRequest request,HttpServletResponse response) throws IOException {
//        LoginStatusService loginStatusService = new LoginStatusServiceImpl();
//        if(loginStatusService.checkLogin(request,response) || loginStatusService.checkManger(request,response)){
            String value = request.getParameter("value");
            int index = Integer.parseInt(request.getParameter("index"));
            PageService pageService = new PageServiceImpl();
            Page<Book> page = pageService.showBookByType(value,index);
            Map<String,Object> map = new HashMap<>();
            map.put("totalPage",page.getTotalPage());
            map.put("index",page.getCurrentPage());
            map.put("bookList",page.getList());
            response.getWriter().println(JSONUtil.mapTOJson(map));
//        }
    }


}
