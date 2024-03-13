package com.bluemsun.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginStatusFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        int login_status = 0;
        int user_status = 0;

        if(request.getSession().getAttribute("login_status")!= null){
            login_status = (int) request.getSession().getAttribute("login_status");
            if(request.getSession().getAttribute("user_status")!=null){
                user_status = (int)request.getSession().getAttribute("user_status");
            }
        }
//        System.out.println(request.getSession().getId());
        request.getSession().setAttribute("login_status",login_status);
        request.getSession().setAttribute("user_status",user_status);
        filterChain.doFilter(request,response);
    }

    @Override
    public void destroy() {

    }
}
