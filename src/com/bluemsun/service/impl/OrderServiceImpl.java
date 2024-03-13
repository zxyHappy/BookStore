package com.bluemsun.service.impl;

import com.bluemsun.dao.BookDao;
import com.bluemsun.dao.CommentDao;
import com.bluemsun.dao.MessageDao;
import com.bluemsun.dao.OrderDao;
import com.bluemsun.entity.Book;
import com.bluemsun.entity.Order;
import com.bluemsun.service.OrderService;

import java.math.BigDecimal;
import java.sql.SQLException;

public class OrderServiceImpl implements OrderService {
    @Override
    public String addOrder(int userId, int bookId, BigDecimal unitPrice, int number, String address) {
        BookDao bookDao = new BookDao();
        if(bookDao.judgeNumber(bookId,number)){
            OrderDao orderDao = new OrderDao();
            Order order = new Order(userId,bookId,unitPrice,number,address);
            if(orderDao.addOrder(order)) {
                String msg = "您有新订单待确认";
                MessageDao messageDao = new MessageDao();
                messageDao.addMessage(msg,1);
                return "添加成功";
            }
            else return "添加失败";
        }else {
            return "库存不足";
        }
    }

    @Override
    public String confirmOrder(int orderId) {
        OrderDao orderDao = new OrderDao();
        Order order = orderDao.getOrderById(orderId);
        if(orderDao.confirmOrder(orderId,order.getNumber(),order.getBookId())){
            MessageDao messageDao = new MessageDao();
            String msg = "您的订单已发货，订单号:"+orderId;
            messageDao.addMessage(msg,orderDao.getOrderById(orderId).getUserId());
            return "确认成功";
        }
        else return "确认失败";
    }

    //检查订单状态
    @Override
    public int checkOrder(int orderId) {
        OrderDao orderDao = new OrderDao();
        return orderDao.checkOrder(orderId);
    }


    //删除订单
    @Override
    public String delOrder(int orderId) {
        if(checkOrder(orderId) == -1) return "无此订单";
        OrderDao orderDao = new OrderDao();
        if(orderDao.delOrder(orderId)) return "删除成功";
        else return "删除失败";
    }


    //用户完成订单后，发送通知，同时设置评论权限
    @Override
    public String finishOrder(int orderId) {
        OrderDao orderDao = new OrderDao();
        if(orderDao.finishOrder(orderId)){
            String msg = "用户"+orderDao.getOrderById(orderId).getUserId()+"的"+orderId+"号订单已完成";
            MessageDao messageDao = new MessageDao();
            messageDao.addMessage(msg,1);
            CommentDao commentDao = new CommentDao();
            if(!commentDao.checkCmtLimit(orderDao.getOrderById(orderId).getUserId(),orderDao.getOrderById(orderId).getBookId())) commentDao.addCmtLimit(orderDao.getOrderById(orderId).getUserId(),orderDao.getOrderById(orderId).getBookId());
            return "确认成功";
        }
        else return "确认失败";
    }

    //找出订单所属的用户
    @Override
    public int showUser(int orderId) {
        OrderDao orderDao = new OrderDao();
        return orderDao.showUser(orderId);
    }

    @Override
    public Book toOrder(int bookId) {
        BookDao bookDao = new BookDao();
        return bookDao.getBookById(bookId);
    }
}
