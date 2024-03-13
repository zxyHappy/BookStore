package com.bluemsun.service;

import com.bluemsun.entity.Book;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface BookService {
     /**
      * 给书添加封面
      * @param book book里bookPhoto不能为空
      * @return
      */
     String addBook(Book book);

     /**
      * 弃用
      * @param book
      * @return
      */
     boolean delBook(Book book);

     /**
      *
      * @param key 要更改的键
      * @param value 要更改的键的值
      * @param bookId 键值对属于哪本书
      * @return
      */
     boolean alertData(String key,String value,int bookId);
     String addPhotos(String photo,int bookId);

     /**
      *
      * @param request 获取书本封面，返回url，在下一步添加书本详细信息里把url加进book信息里
      * @return
      */
     String getFile(HttpServletRequest request); //获取图片，返回url

     /**
      * 上传详细图片
      * @param list 图片的url列表
      * @param bookId 图片所属的id
      * @return
      */
     String addPhotosToBase(List<String> list, int bookId);

     /**
      * 多文件上传
      * @param request
      * @param bookId
      * @return
      */
     String getFiles(HttpServletRequest request,int bookId);

     /**
      * 更新书本信息，实现时
      * @param book
      * @return
      */
     String alertBook(Book book);

     /**
      * 上下架书本
      * @param book
      * @return
      */
     String setStatus(Book book);

     /**
      * 获取首页热门书本
      * @return
      */
     List<Book> getHot();

     /**
      * 判断所需的book里信息是否完整
      * @param book
      * @return
      */
     boolean checkBook(Book book);

     /**
      * 书本详情页使用,展示书本
      * @param userId
      * @param bookId
      * @return
      */
     Map<String,Object> showBook(int userId,int bookId);
}
