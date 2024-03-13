package com.bluemsun.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CharSetFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String uri = request.getRequestURI();
        if(uri.indexOf(".jpg")>0 ||uri.indexOf(".png")>0 || uri.indexOf(".gif")>0 || uri.indexOf(".css") >0 ||uri.indexOf(".bmp")>0 || uri.indexOf(".svg")>0 || uri.indexOf(".webp")>0){
            filterChain.doFilter(request,response);
            return;
        }
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");
        filterChain.doFilter(request,response);

    }

    @Override
    public void destroy() {

    }
}
