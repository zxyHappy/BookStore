package com.bluemsun.controller;

import com.bluemsun.dao.BookDao;
import com.bluemsun.entity.Book;
import com.bluemsun.service.BookService;
import com.bluemsun.service.impl.BookServiceImpl;
import com.bluemsun.util.JSONUtil;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@MultipartConfig(maxFileSize = 5*1024*1024,fileSizeThreshold = 6*1024*1024)
public class BookController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uri = request.getRequestURI();
        switch (uri){
            case "/book/add":
                addBook(request,response);
                break;
            case "/book/addPhoto": // 仅是写入本地并返回给前端url，photo加入数据库在上一个方法中
                addPhoto(request,response);
                break;
            case "/book/addPhotos":  //添加好多好多图片
                addPhotos(request,response);
                break;
            default:response.sendError(404);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request,HttpServletResponse response) throws IOException {
        String uri = request.getRequestURI();
        switch (uri){
            case "/book/alert":
                alertBook(request,response);
                break;
            case "/book/status":
                setStatus(request,response);
                break;
            case "/book/hot":  //首页书本展示
                getHot(request,response);
                break;
            case "/book/show":
                bookMessage(request,response);
                break;
            default:response.sendError(404);
        }
    }

    public void addBook(HttpServletRequest request,HttpServletResponse response) throws IOException {
//        LoginStatusService loginStatusService = new LoginStatusServiceImpl();
//        if(loginStatusService.checkManger(request,response)){ //判断用户权限
            String json = JSONUtil.readJSON(request);
            Book book = JSONUtil.jsonTo(json,Book.class);
            BookService bookService = new BookServiceImpl();
            if(!bookService.checkBook(book)){
                response.getWriter().println(new Gson().toJson("信息不完整"));
                return;
            }
            String msg = bookService.addBook(book);
            BookDao bookDao = new BookDao();
            int bookId = 0; //添加书本后获取bookId
            if(bookDao.getBook(book.getBookName(),book.getPress(),book.getWriter())!=null) bookId = bookDao.getBook(book.getBookName(),book.getPress(),book.getWriter()).getBookId();
            request.getSession().setAttribute("bookId",bookId); //后面添加详细图片信息时用到
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

    //添加封面,添加单个书本图片到本地,返回url
    public void addPhoto(HttpServletRequest request,HttpServletResponse response) throws IOException {
//        LoginStatusService loginStatusService = new LoginStatusServiceImpl();
//        if(loginStatusService.checkManger(request,response)){
        BookService bookService = new BookServiceImpl();
        String projectServerPath = bookService.getFile(request);
        if(projectServerPath == null) {
            response.getWriter().println(new Gson().toJson("未获取到文件"));
            return;
        }
        Map<String,Object> map = new HashMap<>();
        map.put("bookPhoto",projectServerPath);
        String json = JSONUtil.mapTOJson(map);
        response.getWriter().println(json);
    }
//        else {
//            try {
//                response.sendError(404);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }

    //多文件上传
    public void addPhotos(HttpServletRequest request,HttpServletResponse response) throws IOException { //添加详细图片信息到数据库
//        LoginStatusService loginStatusService = new LoginStatusServiceImpl();
//        if(loginStatusService.checkManger(request,response)){
        if(request.getSession().getAttribute("bookId") == null ||(int) request.getSession().getAttribute("bookId") == 0) {
            response.getWriter().println(new Gson().toJson("获取的信息异常"));
        }
        int bookId = (int)request.getSession().getAttribute("bookId");
        BookService bookService = new BookServiceImpl();
        String msg = bookService.getFiles(request,bookId);
        Map<String,Object> map = new HashMap<>();
        map.put("msg",msg);
        request.getSession().setAttribute("bookId",0);
        response.getWriter().print(JSONUtil.mapTOJson(map));
//        }else{
//            try {
//                response.sendError(404);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
    }

    //需要的参数：bookId,bookNumber,Price
    public void alertBook(HttpServletRequest request,HttpServletResponse response) throws IOException {
//        LoginStatusService loginStatusService = new LoginStatusServiceImpl();
//        if(loginStatusService.checkManger(request,response)){
        Book book = new Book();
        book.setBookId(Integer.parseInt(request.getParameter("bookId")));
        if(!"".equals(request.getParameter("bookNumber")) && request.getParameter("bookNumber")!=null) book.setBookNumber(Integer.parseInt(request.getParameter("bookNumber")));
        if(!"".equals(request.getParameter("Price")) && request.getParameter("Price")!=null) book.setPrice(new BigDecimal(request.getParameter("Price")));
        BookService bookService = new BookServiceImpl();
        String msg = bookService.alertBook(book);
        Map<String,Object> map = new HashMap<>();
        map.put("msg",msg);
        map.put("bookNumber",book.getBookNumber());
        map.put("Price",book.getPrice());
        //                System.out.println(JSONUtil.mapTOJson(map));
         response.getWriter().println(JSONUtil.mapTOJson(map));

    }
//    }

    //设置上下架
    public void setStatus(HttpServletRequest request,HttpServletResponse response) throws IOException {
//        LoginStatusService loginStatusService = new LoginStatusServiceImpl();
//        if(loginStatusService.checkManger(request,response)){
        Book book = new Book();
        book.setBookStatus(Integer.parseInt(request.getParameter("bookStatus")));
        book.setBookId(Integer.parseInt(request.getParameter("bookId")));
        BookService bookService = new BookServiceImpl();
        String msg = bookService.setStatus(book);
        Map<String,Object> map = new HashMap<>();
        map.put("msg",msg);
        map.put("status",book.getBookStatus());
        response.getWriter().println(JSONUtil.mapTOJson(map));
    }
//    }

    //用户首页书本展示
    public void getHot(HttpServletRequest request,HttpServletResponse response) throws IOException {
//        LoginStatusService loginStatusService = new LoginStatusServiceImpl();
//        if(loginStatusService.checkLogin(request,response)){
        BookService bookService = new BookServiceImpl();
        List<Book> list = bookService.getHot();
        Map<String,Object> map = new HashMap<>();
        map.put("list",list);
        response.getWriter().println(JSONUtil.mapTOJson(map));
//        }else{
//            response.sendError(404);
//        }
    }

    //商品详情页,用户用
    public void bookMessage(HttpServletRequest request,HttpServletResponse response) throws IOException {
//        LoginStatusService loginStatusService = new LoginStatusServiceImpl();
//        if(loginStatusService.checkLogin(request,response)){
        BookService bookService = new BookServiceImpl();
        int userId = (int)request.getSession().getAttribute("login_status");
        if("".equals(request.getParameter("bookId")) || request.getParameter("bookId") == null){
            response.getWriter().println(new Gson().toJson("记得给我传参"));
            return;
        }
        int bookId = Integer.parseInt(request.getParameter("bookId"));
        Map<String,Object> map = bookService.showBook(userId,bookId);
        if(map == null){
            response.getWriter().println(new Gson().toJson("没有这本书"));
            return;
        }
        response.getWriter().println(JSONUtil.mapTOJson(map));
//        }else {
//            response.sendError(404);
//        }
    }



}

