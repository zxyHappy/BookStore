package com.bluemsun.service;

import com.bluemsun.entity.*;

import java.util.List;

public interface PageService {

    /**
     *搜索栏查询用的分页
     *点击搜索时把搜索栏里的信息和常量1发过来，点击页码按钮时把搜索信息和页码值发过来
     * index要通过page，然后换成limit要用到的位置-1的那个
     * @param value 搜索框里的内容
     * @param index 页码值
     * @return 返回该页所需的book数据和总页数
     */
    Page<Book> bookPage(String value, int index);

    /**
     * 用户查询展示页面（用户管理功能）
     * @param value
     * @param index
     * @return
     */
    Page<User> userPage(String value,int index);

    /**
     * 用户端搜索分页
     * @param value
     * @param index
     * @return
     */
    Page<Book> bookPageByUser(String value,int index);

    /**
     * 商家订单展示（全部）
     * @param index
     * @return
     */
    Page<Order> orderPageByManger(int index);

    /**
     * 已弃用
     * @param index
     * @return
     */
    Page<Book> getSearch(int index);

    /**
     * 未确认订单展示（商家）
     * @param index
     * @return
     */
    Page<Order> orderPageByMangerNotConfirm(int index);

    /**
     * 用户展示订单
     * @param index
     * @param userId
     * @param status 0为展示所有订单，1为展示为被商家确认的订单，2为展示未完成订单，3为展示已完成订单
     * @return
     */
    Page<Order> showOrderByUser(int index,int userId,int status);

    /**
     * 在书本详情页展示评论
     * @param bookId
     * @param index 页码值
     * @return
     */
    Page<Comment> showCommentByBook(int bookId,int index);

    /**
     * 展示用户自己的评论
     * @param userId
     * @param index
     * @return
     */
    Page<Comment> showCommentByUser(int userId,int index);

    /**
     * 显示商家通知
     * @param index
     * @return
     */
    Page<Message> showMessage(int index);

    /**
     * 显示用户通知
     * @param userId
     * @param index
     * @return
     */
    Page<Message> showMessageByUser(int userId,int index);

    /**
     *未完成订单展示（商家）
     * @param index
     * @return
     */
    Page<Order> orderPageByMangerNotFinish(int index);

    /**
     * 已完成订单展示（商家）
     * @param index
     * @return
     */
    Page<Order> orderPageByMangerFinish(int index);

    /**
     * 书本分类查询
     * @param value 类型名
     * @param index
     * @return
     */
    Page<Book> showBookByType(String value,int index);
}
