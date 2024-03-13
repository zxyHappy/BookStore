package com.bluemsun.filter;

import com.google.gson.Gson;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CheckStatusFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String uri = request.getRequestURI();

        int loginStatus = (int) request.getSession().getAttribute("login_status"); //登录用户的id
        int userStatus = (int) request.getSession().getAttribute("user_status"); // 0是正常用户，1是商家

        if(uri.indexOf(".jpg")>0 ||uri.indexOf(".png")>0 || uri.indexOf(".gif")>0 || uri.indexOf(".css") >0 ||uri.indexOf(".bmp")>0 || uri.indexOf(".svg")>0 || uri.indexOf(".webp")>0 ){
            filterChain.doFilter(request,response);
            return;
        }

        if(loginStatus!=0&&userStatus==0){ //用户
            if(uri.equals("/book/hot") || uri.equals("/book/show") || uri.equals("/order/add") || uri.equals("/order/finish") || uri.equals("/order/search") || uri.equals("/order/toorder") || uri.equals("/page/user/comment") || uri.equals("/page/book/comment") || uri.equals("/page/message/user") || uri.equals("/page/book/type") || uri.equals("/shopping/submit") || uri.equals("/shopping/add") || uri.equals("/shopping/del") || uri.equals("/shopping/alert") || uri.equals("/shopping/show") || uri.equals("/user/center") || uri.equals("/user/photo") || uri.equals("/user/alert") || uri.equals("/user/comment") || uri.equals("/user/search") || uri.equals("/user/delcomment") || uri.equals("/user/message/confirm") || uri.equals("/user/message/del") || uri.equals("/user/alert/like") || uri.equals("/user/exit")){
                filterChain.doFilter(request,response);
            }else {
                response.getWriter().println(new Gson().toJson("权限未通过或未找到页面"));
            }
        }else if(loginStatus!=0&&userStatus==1){ //商家
            if(uri.equals("/book/add") || uri.equals("/book/addPhoto") || uri.equals("/book/addPhotos") || uri.equals("/book/alert") || uri.equals("/book/status") || uri.equals("/order/show") || uri.equals("/order/confirm") || uri.equals("/order/del") || uri.equals("/page/book") || uri.equals("/page/message/manger") || uri.equals("/page/user") || uri.equals("/page/book/type") || uri.equals("/user/center") || uri.equals("/user/photo") || uri.equals("/user/alert") || uri.equals("/user/message/confirm") || uri.equals("/user/message/del") || uri.equals("/user/search/id") || uri.equals("/user/ban") || uri.equals("/user/exit")){
                filterChain.doFilter(request,response);
            }else {
                response.getWriter().println(new Gson().toJson("权限未通过或未找到页面"));
            }
        }else {
            if(uri.equals("/user/register") || uri.equals("/user/login")){
                filterChain.doFilter(request,response);
            }else  {
                response.getWriter().println(new Gson().toJson("权限未通过或未找到页面"));
            }
        }

    }

    @Override
    public void destroy() {

    }
}
