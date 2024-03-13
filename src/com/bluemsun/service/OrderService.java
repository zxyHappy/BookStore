package com.bluemsun.service;

import com.bluemsun.entity.Book;
import com.bluemsun.entity.Order;

import java.math.BigDecimal;

public interface OrderService {
    /**
     * 添加订单
     * @param userId 订单所属用户
     * @param bookId 订单所属书本
     * @param unitPrice 单价
     * @param number 数量
     * @param address 地址
     * @return 添加成功/添加失败/库存不足
     */
    String addOrder(int userId, int bookId, BigDecimal unitPrice,int number,String address);

    /**
     *同时会给用户发送通知
     * @param orderId 商家确认订单
     * @return
     */
    String confirmOrder(int orderId);

    /**
     * 获取订单当前的状态
     * @param orderId
     * @return
     */
    int checkOrder(int orderId);

    /**
     * 删除订单
     * @param orderId
     * @return
     */
    String delOrder(int orderId);

    /**
     * 用户完成订单，设置评论权限，给商家发送通知
     * @param orderId
     * @return
     */
    String finishOrder(int orderId);

    /**
     * 找出订单所属用户
     * @param orderId
     * @return
     */
    int showUser(int orderId);

    /**
     * 跳转至下单页面，需要把书本的信息传过去
     * @param bookId
     * @return
     */
    Book toOrder(int bookId); //跳转到下单页面
}
