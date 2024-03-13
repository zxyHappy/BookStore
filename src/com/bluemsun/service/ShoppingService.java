package com.bluemsun.service;

import com.bluemsun.entity.ShoppingItem;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface ShoppingService {
    /**
     * 提交购物车里的商品
     *先将这些id对应的条目转成一个个订单提交，然后把购物车里这些条目删掉
     * @param list 购物车里勾选的条目的id列表
     * @return
     */
    String addOrder(String address,List<Integer> list);

    /**
     * 加入购物车时商品数量默认是1，记得判断购物车里是否有此商品,item里number必须设置
     * @param item
     * @return
     */
    String addShopping(ShoppingItem item);

    String delShopping(int userId,int id);

    /**
     * 修改购物车中id号条目的数量
     * @param id
     * @param number
     * @return
     */
    int alertNumber(int id,int number);

    /**
     * 展示购物车
     * @param userId
     * @return 购物车里的商品条目和总数
     */
    Map<String,Object> showShopping(int userId);

//    BigDecimal getPrice(int id);


}
