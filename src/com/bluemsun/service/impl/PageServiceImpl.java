package com.bluemsun.service.impl;

import com.bluemsun.dao.*;
import com.bluemsun.entity.*;
import com.bluemsun.service.PageService;

import java.util.List;

public class PageServiceImpl implements PageService {


    @Override
    public Page<Book> bookPage(String value,int index) {
        BookDao bookDao = new BookDao();
        Page<Book> page = new Page<>(index,7,bookDao.countNumberBySearch(value));
        List<Book> list = bookDao.getBookBySearch(value,page.getStartIndex());
        page.setList(list);
        return page;
    }

    @Override
    public Page<User> userPage(String value, int index) {
        UserDao userDao = new UserDao();
        Page<User> page = new Page<>(index,10,userDao.countNumberBySearch(value));
        List<User> list = userDao.getUserBySearch(value,page.getStartIndex());
        page.setList(list);
        return page;
    }

    //用户端商品搜索分页
    @Override
    public Page<Book> bookPageByUser(String value,int index) {
        BookDao bookDao = new BookDao();
        Page<Book> page = new Page<>(index,10,bookDao.countNumberByUser(value));
        List<Book> list = bookDao.getBookByUser(value,page.getStartIndex());
        page.setList(list);
        return page;
    }

    //商家订单通知(商家订单展示)
    @Override
    public Page<Order> orderPageByManger(int index) {
        OrderDao orderDao = new OrderDao();
        Page<Order> page = new Page<>(index,7,orderDao.countOrder());
        List<Order> list = orderDao.showOrderByManger(page.getStartIndex());
        page.setList(list);
        return page;
    }

    //商家订单展示（仅显示未确认）
    public Page<Order> orderPageByMangerNotConfirm(int index){
        OrderDao orderDao = new OrderDao();
        Page<Order> page = new Page<>(index,7,orderDao.countOrderNotConfirm());
        List<Order> list = orderDao.showOrderByMangerNotConfirm(page.getStartIndex());
        page.setList(list);
        return page;
    }

    //商家订单展示（仅显示未完成）
    public Page<Order> orderPageByMangerNotFinish(int index){
        OrderDao orderDao = new OrderDao();
        Page<Order> page = new Page<>(index,7,orderDao.countOrderNotFinish());
        List<Order> list = orderDao.showOrderByMangerNotFinish(page.getStartIndex());
        page.setList(list);
        return page;
    }

    //商家订单展示（仅显示已完成）
    public Page<Order> orderPageByMangerFinish(int index){
        OrderDao orderDao = new OrderDao();
        Page<Order> page = new Page<>(index,7,orderDao.countOrderFinish());
        List<Order> list = orderDao.showOrderByMangerFinish(page.getStartIndex());
        page.setList(list);
        return page;
    }


    //用户端展示订单

    @Override
    public Page<Order> showOrderByUser(int index, int userId,int status) {
        OrderDao orderDao = new OrderDao();
        if(status == 0){
            Page<Order> page = new Page<>(index,7,orderDao.countOrderByUser(userId));
            List<Order> list = orderDao.showOrderByUser(page.getStartIndex(),userId);
            page.setList(list);
            return page;
        }else if(status == 1){
            Page<Order> page = new Page<>(index,7,orderDao.countOrderByUserNotConfirm(userId));
            List<Order> list = orderDao.showOrderByUserNotConfirm(page.getStartIndex(),userId);
            page.setList(list);
            return page;
        }else if(status == 2){
            Page<Order> page = new Page<>(index,7,orderDao.countOrderByUserNotFinish(userId));
            List<Order> list = orderDao.showOrderByUserNotFinish(page.getStartIndex(),userId);
            page.setList(list);
            return page;
        }else {
            Page<Order> page = new Page<>(index,7,orderDao.countOrderByUserFinish(userId));
            List<Order> list = orderDao.showOrderByUserFinish(page.getStartIndex(),userId);
            page.setList(list);
            return page;
        }

    }

    //搜索页面默认显示数据
    @Override
    public Page<Book> getSearch(int index) {
        BookDao bookDao = new BookDao();
        Page<Book> page = new Page<>(index,10,bookDao.countNumber());
        List<Book> list = bookDao.getSearch(page.getStartIndex());
        page.setList(list);
        return page;
    }

    //书本详情页展示评论
    @Override
    public Page<Comment> showCommentByBook(int bookId, int index) {
        CommentDao commentDao = new CommentDao();
        Page<Comment> page = new Page<>(index,7,commentDao.countCommentByBook(bookId));
        List<Comment> list = commentDao.showCommentByBook(bookId,page.getStartIndex());
        page.setList(list);
        return page;
    }

    //用户端展示评论
    @Override
    public Page<Comment> showCommentByUser(int userId, int index) {
        CommentDao commentDao = new CommentDao();
        Page<Comment> page = new Page<>(index,10,commentDao.countCommentByUser(userId));
        List<Comment> list = commentDao.showCommentByUser(userId,page.getStartIndex());
        page.setList(list);
        return page;
    }

    //商家通知
    @Override
    public Page<Message> showMessage(int index) {
        MessageDao messageDao = new MessageDao();
        Page<Message> page = new Page<>(index,10,messageDao.countMessage());
        List<Message> list = messageDao.showMessage(page.getStartIndex());
        page.setList(list);
        return page;
    }

    //用户通知
    @Override
    public Page<Message> showMessageByUser(int userId, int index) {
        MessageDao messageDao = new MessageDao();
        Page<Message> page = new Page<>(index,10,messageDao.countMessageByUser(userId));
        List<Message> list = messageDao.showMessageByUser(userId,page.getStartIndex());
        page.setList(list);
        return page;
    }

    @Override
    public Page<Book> showBookByType(String value,int index) {
        BookDao bookDao = new BookDao();
        Page<Book> page = new Page<>(index,10,bookDao.countBookByType(value));
        List<Book> list = bookDao.getBookByType(value,page.getStartIndex());
        page.setList(list);
        return page;
    }
}
