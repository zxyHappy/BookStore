package com.bluemsun.controller;

import com.bluemsun.entity.ShoppingItem;
import com.bluemsun.service.ShoppingService;
import com.bluemsun.service.impl.ShoppingServiceImpl;
import com.bluemsun.util.JSONUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShoppingController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uri = request.getRequestURI();
        switch (uri){
            case "/shopping/submit":
                addOrder(request,response);
                break;
            default:response.sendError(404);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uri = request.getRequestURI();
        switch (uri){
            case "/shopping/add":
                addShopping(request,response);
                break;
            case "/shopping/del":
                delShopping(request,response);
                break;
            case "/shopping/alert":
                alertNumber(request,response);
                break;
            case "/shopping/show":
                showShopping(request,response);
                break;
            default:response.sendError(404);
        }
    }

    public void addShopping(HttpServletRequest request,HttpServletResponse response) throws IOException {
//        LoginStatusService loginStatusService = new LoginStatusServiceImpl();
//        if(loginStatusService.checkLogin(request,response)){
            int bookId = Integer.parseInt(request.getParameter("bookId"));
            int userId = (int)request.getSession().getAttribute("login_status");
            ShoppingItem shoppingItem = new ShoppingItem();
            shoppingItem.setBookId(bookId);
            shoppingItem.setUserId(userId);
            ShoppingService shoppingService = new ShoppingServiceImpl();
            String msg = shoppingService.addShopping(shoppingItem);
            Map<String,Object> map = new HashMap<>();
            map.put("msg",msg);
            response.getWriter().println(JSONUtil.mapTOJson(map));
//        }
    }

    public void delShopping(HttpServletRequest request,HttpServletResponse response) throws IOException {
//        LoginStatusService loginStatusService = new LoginStatusServiceImpl();
//        if(loginStatusService.checkLogin(request,response)){
            int id = Integer.parseInt(request.getParameter("id"));
            int userId = (int)request.getSession().getAttribute("login_status");
            ShoppingService shoppingService = new ShoppingServiceImpl();
            String msg = shoppingService.delShopping(userId,id);
            Map<String,Object> map = new HashMap<>();
            map.put("msg",msg);
            response.getWriter().println(JSONUtil.mapTOJson(map));
//        }
    }


    //更新数量
    public void alertNumber(HttpServletRequest request,HttpServletResponse response) throws IOException {
//        LoginStatusService loginStatusService = new LoginStatusServiceImpl();
//        if(loginStatusService.checkLogin(request,response)){
            int id = Integer.parseInt(request.getParameter("id"));
            int number = Integer.parseInt(request.getParameter("number"));
            ShoppingService shoppingService = new ShoppingServiceImpl();
            String msg;
            if(shoppingService.alertNumber(id,number)!=0){
                msg = "修改成功";
            }else msg = "修改失败";
            Map<String,Object> map = new HashMap<>();
            map.put("msg",msg);
            map.put("number",number);
            response.getWriter().println(JSONUtil.mapTOJson(map));
//        }
    }

    //购物车商品提交
    public void addOrder(HttpServletRequest request,HttpServletResponse response) throws IOException {
//        LoginStatusService loginStatusService = new LoginStatusServiceImpl();
//        if(loginStatusService.checkLogin(request,response)){
            String json = JSONUtil.readJSON(request);
            Map<String,Object> map = JSONUtil.jsonToMap(json);
            String address = (String) map.get("address");
//            List<Double> doubles = (List<Double>) map.get("list");
//            List<Integer> list = new ArrayList<>();
//            for(Double d: doubles){
//                list.add(new BigDecimal(d).intValue());
//            }
            List<String> strings = (List<String>) map.get("list");
            List<Integer> list = new ArrayList<>();
            for(String s:strings){
                list.add(Integer.parseInt(s));
            }
            ShoppingService shoppingService = new ShoppingServiceImpl();
            String msg = shoppingService.addOrder(address,list);
            Map<String,Object> map1 = new HashMap<>();
            map1.put("msg",msg);
            response.getWriter().println(JSONUtil.mapTOJson(map1));
//        }
    }

    //展示购物车
    public void showShopping(HttpServletRequest request,HttpServletResponse response) throws IOException {
//        LoginStatusService loginStatusService = new LoginStatusServiceImpl();
//        if(loginStatusService.checkLogin(request,response)){
            int userId =(int) request.getSession().getAttribute("login_status");
            ShoppingService shoppingService = new ShoppingServiceImpl();
            Map<String,Object> map = shoppingService.showShopping(userId);
            response.getWriter().println(JSONUtil.mapTOJson(map));
//        }
    }


}
