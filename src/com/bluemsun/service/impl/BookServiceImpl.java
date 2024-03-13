package com.bluemsun.service.impl;

import com.bluemsun.dao.BookDao;
import com.bluemsun.dao.UserDao;
import com.bluemsun.entity.Book;
import com.bluemsun.entity.Page;
import com.bluemsun.service.BookService;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.util.*;

@MultipartConfig(maxFileSize = 5*1024*1024,fileSizeThreshold = 6*1024*1024)
public class BookServiceImpl implements BookService {
    @Override
    public String addBook(Book book) { //book里有bookPhoto
        BookDao bookDao = new BookDao();
        if(bookDao.checkRepeat(book)){
            if(bookDao.addBook(book)){
                return "添加成功";
            }else{
                return "添加失败";
            }
        }else return "该书已存在";
    }

    //弃用
    @Override
    public boolean delBook(Book book) {
        return false;
    }

    //弃用
    @Override
    public boolean alertData(String key, String value,int bookId) {
        return false;
    }

    @Override
    public String addPhotos(String photo, int bookId) { //添加详细图片信息,参数为图片的url和所属书本的id
        BookDao bookDao = new BookDao();
        if(bookDao.addPhoto(photo,bookId)) return "添加成功";
        else return "添加失败";
    }

    @Override
    public String getFile(HttpServletRequest request) { //存入图片，返回url
        Part part = null;
        try {
            part = request.getPart("bookPhoto");
        } catch (IOException | ServletException e) {
            e.printStackTrace();
        }
        String disposition = part.getSubmittedFileName();
        if("".equals(disposition)) return null;
        String suffix = disposition.substring(disposition.lastIndexOf(".")); //获取文件后缀名
        String fileName = UUID.randomUUID()+suffix;

        String server_path =request.getServletContext().getRealPath("Bookphoto"); // 目录路径
        File fileDisk = new File(server_path);
        if(!fileDisk.exists()){
            fileDisk.mkdir();
        }

        String file_part =server_path+'/'+fileName;
        try {
            part.write(file_part); //写入文件
        } catch (IOException e) {
            e.printStackTrace();
        }

        String projectServerPath = request.getScheme()+"://"+request.getServerName()+":"
                +request.getServerPort()+request.getContextPath()+"/Bookphoto/"+fileName;  //前端访问用的url

        return projectServerPath;
    }

    @Override
    public String addPhotosToBase(List<String> list,int bookId) { //处理url的list,添加详细图片url到数据库
        BookDao bookDao = new BookDao();
        if(!list.isEmpty()){
            for(String photo:list){
                if(!bookDao.addPhoto(photo,bookId)) return "未找到该书本";
            }
            return "添加成功";
        }else return "添加失败";
    }

    @Override
    public String getFiles(HttpServletRequest request,int bookId){ //返回url的list
        List<String> list = new ArrayList<>();
        try {
            if(request.getParts()==null) return "添加失败";
//            List<Part> parts = (List<Part>)request.getParts();
            Collection<Part> parts = request.getParts();
            System.out.println(parts);
            for(Part part:parts){
                System.out.println(part.getSize());
                String disposition = part.getSubmittedFileName();
                if("".equals(disposition)) return "添加失败";
                System.out.println(disposition);
                String suffix = disposition.substring(disposition.lastIndexOf(".")); //获取文件后缀名
                String fileName = UUID.randomUUID()+suffix;

                String server_path =request.getServletContext().getRealPath("Bookphoto"); // 目录路径
                File fileDisk = new File(server_path);
                if(!fileDisk.exists()){
                    fileDisk.mkdir();
                }

                String file_part =server_path+'/'+fileName;
                try {
                    part.write(file_part); //写入文件
                } catch (IOException e) {
                    e.printStackTrace();
                }

                String projectServerPath = request.getScheme()+"://"+request.getServerName()+":"
                        +request.getServerPort()+request.getContextPath()+"/Bookphoto/"+fileName;

                list.add(projectServerPath);
            }
            return addPhotosToBase(list,bookId);
        } catch (IOException | ServletException e) {
            e.printStackTrace();
        }
        return "添加失败";
    }

    @Override
    public String alertBook(Book book) {
        if(book == null) return "更新失败";
        BookDao bookDao = new BookDao();
        if(book.getBookNumber()==0) book.setBookNumber(bookDao.getBookById(book.getBookId()).getBookNumber());
        if(book.getPrice() == null) book.setPrice(bookDao.getBookById(book.getBookId()).getPrice());
        if(bookDao.alertBook(book)) return "更新成功";
        else return "更新失败";
    }

    @Override
    public String setStatus(Book book) {
        if(book == null) return "设置失败";
        BookDao bookDao = new BookDao();
        if(bookDao.setStatus(book.getBookId(),book.getBookStatus())) return "设置成功";
        else return "设置失败";
     }

    @Override
    public List<Book> getHot() {
        BookDao bookDao = new BookDao();
        return bookDao.getHot();
    }

    @Override
    public boolean checkBook(Book book) {
        if(book.getBookName() != null && book.getWriter() != null && book.getSynopsis()!=null && book.getBookType()!=null && book.getBookPhoto()!=null && book.getPress()!=null){
            if(!"".equals(book.getBookName()) && !"".equals(book.getWriter()) && !"".equals(book.getSynopsis()) && !"".equals(book.getBookType()) && !"".equals(book.getBookPhoto()) && !"".equals(book.getPress())){
                return true;
            }else return false;
        }else return false;
    }

    @Override
    public Map<String,Object> showBook(int userId,int bookId) {
        BookDao bookDao = new BookDao();
        UserDao userDao = new UserDao();
        Book book = bookDao.getBookById(bookId);
        if(book == null) return null;
        Map<String,Object> map = new HashMap<>();
        map.put("likeStatus",userDao.checkLike(userId,bookId));
        map.put("book",book);
        List<String> list = bookDao.getPhotos(bookId);
        map.put("photoList",list);
        return map;
    }
}
