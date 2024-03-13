package com.bluemsun.controller;

import com.bluemsun.entity.Book;
import com.bluemsun.entity.Order;
import com.bluemsun.entity.Page;
import com.bluemsun.service.OrderService;
import com.bluemsun.service.PageService;
import com.bluemsun.service.impl.OrderServiceImpl;
import com.bluemsun.service.impl.PageServiceImpl;
import com.bluemsun.util.JSONUtil;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class OrderController extends HttpServlet { //确认订单时记得减库存，提交订单前记得判断库存是否够
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uri = request.getRequestURI();
        switch (uri){
            case "/order/add": //提交订单(购买)
                addOrder(request,response);
                break;
            default:response.sendError(404);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uri = request.getRequestURI();
        switch (uri){
            case "/order/show": //商家页面展示订单
                showOrderByManger(request,response);
                break;
            case "/order/confirm": //商家确认订单
                confirmOrder(request,response);
                break;
            case "/order/del": // 商家删除订单
                delOrder(request,response);
                break;
            case "/order/finish": // 用户确认收货，完成订单
                finishOrder(request,response);
                break;
            case "/order/search": //用户端查询订单
                showOrderByUser(request,response);
                break;
            case "/order/toorder":
                toOrder(request,response);
                break;
            default:response.sendError(404);
        }
    }

    //用户提交订单
    public void addOrder(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        LoginStatusService loginStatusService = new LoginStatusServiceImpl();
//        if(loginStatusService.checkLogin(request,response)){
            int userId = (int) request.getSession().getAttribute("login_status");
            String json = JSONUtil.readJSON(request);
            Map<String,Object> map1 = JSONUtil.jsonToMap(json);
//            int bookId = (int)map1.get("bookId");
            BigDecimal bigDecimal = new BigDecimal(map1.get("bookId").toString());
            int bookId = bigDecimal.intValue();
            BigDecimal unitPrice = new BigDecimal(map1.get("unitPrice").toString());
//            int number = (int)map1.get("number");
            bigDecimal = new BigDecimal(map1.get("number").toString());
            int number = bigDecimal.intValue();
            String address = (String) map1.get("address");
            OrderService orderService = new OrderServiceImpl();
            String msg = orderService.addOrder(userId,bookId,unitPrice,number,address);
            Map<String,Object> map2 = new HashMap<>();
            map2.put("msg",msg);
            response.getWriter().println(JSONUtil.mapTOJson(map2));
//        }else {
//            response.sendError(404);
//        }
    }

    //商家查看订单
    public void showOrderByManger(HttpServletRequest request,HttpServletResponse response) throws IOException {
//        LoginStatusServiceImpl loginStatusService = new LoginStatusServiceImpl();
//        if(loginStatusService.checkManger(request,response)){
            int index = 1;
            int status = 0;
            if(request.getParameter("index") != null && !"".equals(request.getParameter("index"))){
                index = Integer.parseInt(request.getParameter("index"));
            }
            if(request.getParameter("status") != null && !"".equals(request.getParameter("status"))){
                status = Integer.parseInt(request.getParameter("status"));
            }
            PageService pageService = new PageServiceImpl();
            Page<Order> page = null;
            if(status == 0 ){
                page = pageService.orderPageByManger(index);
            }else if(status == 1){
                page = pageService.orderPageByMangerNotConfirm(index);
            }else if(status == 2){
                page = pageService.orderPageByMangerNotFinish(index);
            }else {
                page = pageService.orderPageByMangerFinish(index);
            }
            Map<String,Object> map = new HashMap<>();
            map.put("totalPage",page.getTotalPage());
            map.put("index",page.getCurrentPage());
            map.put("orderList",page.getList());
            response.getWriter().println(JSONUtil.mapTOJson(map));
//        }else {
//            try {
//                response.sendError(404);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
    }

    //商家确认订单
    public void confirmOrder(HttpServletRequest request,HttpServletResponse response) throws IOException {
//        LoginStatusService loginStatusService = new LoginStatusServiceImpl();
//        if(loginStatusService.checkManger(request,response)){
            int orderId = Integer.parseInt(request.getParameter("id"));
            OrderService orderService = new OrderServiceImpl();
            Map<String,Object> map = new HashMap<>();
            if(orderService.checkOrder((orderId)) == -1){
                response.getWriter().println(new Gson().toJson("未查询到该订单"));
            }
            else if(orderService.checkOrder(orderId) != 0){
                String msg = "该订单已经被确认过了";
                map = new HashMap<>();
                map.put("msg",msg);
                map.put("status",orderService.checkOrder(orderId));
            }else {
                String msg = orderService.confirmOrder(orderId);
                map = new HashMap<>();
                map.put("msg",msg);
                map.put("status",orderService.checkOrder(orderId));
            }
            response.getWriter().println(JSONUtil.mapTOJson(map));

//        }else {
//            try {
//                response.sendError(404);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
    }

    //删除订单
    public void delOrder(HttpServletRequest request,HttpServletResponse response) throws IOException {
//        LoginStatusService loginStatusService = new LoginStatusServiceImpl();
//        if(loginStatusService.checkManger(request,response)){
            int orderId = Integer.parseInt(request.getParameter("id"));
            OrderService orderService = new OrderServiceImpl();
            String msg = orderService.delOrder(orderId);
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

    //用户完成订单(确认收货)，要判断此订单是否属于该用户以及此订单的状态，只有已确认未完成的订单可以被用户完成
    public void finishOrder(HttpServletRequest request,HttpServletResponse response) throws IOException {
//        LoginStatusService loginStatusService = new LoginStatusServiceImpl();
//        if(loginStatusService.checkLogin(request,response)){
            int orderId = Integer.parseInt(request.getParameter("id"));
            int userId = (int)request.getSession().getAttribute("login_status"); //获取当前登录的用户id
            OrderService orderService = new OrderServiceImpl();
            Map<String,Object> map = new HashMap<>();
            if(userId != orderService.showUser(orderId) || orderService.checkOrder(orderId) == -1){ // 登录账户和订单所属不符/订单不存在
                response.getWriter().println(new Gson().toJson("登录账户和订单所属不符或者订单不存在"));
            }else if(orderService.checkOrder(orderId) == 2){ // 订单已经被完成
                map.put("msg","订单已经处于完成状态");
                map.put("status",2);
            }else if(orderService.checkOrder(orderId) == 0){
                map.put("msg","订单还未被商家确认");
                map.put("status",0);
            }else {
                map.put("msg",orderService.finishOrder(orderId));
                map.put("status",2);
            }
            response.getWriter().println(JSONUtil.mapTOJson(map));
//        }else {
//            try {
//                response.sendError(404);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
    }

    //用户页面展示订单
    public void showOrderByUser(HttpServletRequest request,HttpServletResponse response) throws IOException {
//        LoginStatusService loginStatusService = new LoginStatusServiceImpl();
//        if(loginStatusService.checkLogin(request,response)){
            int userId =(int) request.getSession().getAttribute("login_status");
            int index = Integer.parseInt(request.getParameter("index"));
            int status = 0;
            String status1 = request.getParameter("status");
            if(status1 != null && !"".equals(status1)) {
                status =  Integer.parseInt(status1);
            }
            PageService pageService = new PageServiceImpl();
            Page<Order> page = pageService.showOrderByUser(index,userId,status);
            String msg;
            if(page.getList() == null) msg = "快去买点儿东西吧";
            else msg = "订单获取成功";
            Map<String,Object> map = new HashMap<>();
            map.put("msg",msg);
            map.put("totalPage",page.getTotalPage());
            map.put("index",page.getCurrentPage());
            map.put("list",page.getList());
            response.getWriter().println(JSONUtil.mapTOJson(map));

//        }else {
//            try {
//                response.sendError(404);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
    }

    public void toOrder(HttpServletRequest request,HttpServletResponse response) throws IOException {
//        LoginStatusService loginStatusService = new LoginStatusServiceImpl();
//        if(loginStatusService.checkLogin(request,response)){
            int bookId = Integer.parseInt(request.getParameter("bookId"));
            OrderService orderService = new OrderServiceImpl();
            Book book = orderService.toOrder(bookId);
            Map<String,Object> map = new HashMap<>();
            map.put("book",book);
            response.getWriter().println(JSONUtil.mapTOJson(map));
//        }
    }




}
