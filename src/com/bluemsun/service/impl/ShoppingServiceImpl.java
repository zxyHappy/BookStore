package com.bluemsun.service.impl;

import com.bluemsun.dao.BookDao;
import com.bluemsun.dao.OrderDao;
import com.bluemsun.dao.ShoppingDao;
import com.bluemsun.entity.Book;
import com.bluemsun.entity.ShoppingItem;
import com.bluemsun.service.OrderService;
import com.bluemsun.service.ShoppingService;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShoppingServiceImpl implements ShoppingService {


    @Override
    public String addOrder(String address, List<Integer> list) {
        ShoppingDao shoppingDao = new ShoppingDao();
        OrderService orderService = new OrderServiceImpl();
        BookDao bookDao = new BookDao();
        if(list == null || list.isEmpty() || "".equals(address) || address == null) return "提交失败";
        for(int id:list){
            ShoppingItem item = shoppingDao.getItemById(id);
            int bookId = item.getBookId();
            int userId = item.getUserId();
            int number = item.getNumber();
            Book book = bookDao.getBookById(bookId);
            BigDecimal unitPrice = book.getPrice();
            String msg = orderService.addOrder(userId,bookId,unitPrice,number,address);
            if(msg.equals("添加成功")){
                if(!delShopping(userId,id).equals("删除成功")) return "提交失败";
            }else return "提交失败，《"+bookDao.getBookById(bookId).getBookName() +"》库存不足";
        }
        return "提交成功";
    }

    @Override
    public String addShopping(ShoppingItem item) {
        if(item.getBookId()!=0&&item.getUserId()!=0&&item.getNumber()>0){
            ShoppingDao shoppingDao = new ShoppingDao();
            if(shoppingDao.checkItem(item.getUserId(),item.getBookId())) {
                if(shoppingDao.addNumber(shoppingDao.getItem(item.getUserId(),item.getBookId()).getId(),item.getNumber())) return "添加成功";
                else return "添加失败";
            }
            else{
                if(shoppingDao.addItem(item)) return "添加成功";
                else return "添加失败";
            }
        }else return "提交信息不完善，请补充";
    }

    @Override
    public String delShopping(int userId,int id) {
        ShoppingDao shoppingDao = new ShoppingDao();
        if(userId == shoppingDao.getItemById(id).getUserId()){
            if(shoppingDao.delItem(id)) return "删除成功";
            else return "删除失败";
        }else return "无删除权限";
    }

    @Override
    public int alertNumber(int id, int number) {
        ShoppingDao shoppingDao = new ShoppingDao();
        BookDao bookDao = new BookDao();
        ShoppingItem item = shoppingDao.getItemById(id);
        Book book = bookDao.getBookById(item.getBookId());
        if(book.getBookNumber()<number) return 0;
        if(shoppingDao.alertItem(id,number)) return number;
        else return 0;
    }



    @Override
    public Map<String,Object> showShopping(int userId) {
        ShoppingDao shoppingDao = new ShoppingDao();
        Map<String,Object> map = new HashMap<>();
        map.put("list",shoppingDao.getItemByUser(userId));
        map.put("total",shoppingDao.countItem(userId));
        return map;
    }

//    @Override
//    public BigDecimal getPrice(int id) {
//        ShoppingDao shoppingDao = new ShoppingDao();
//        ShoppingItem item = shoppingDao.getItemById(id);
//        BookDao bookDao = new BookDao();
//        Book book = bookDao.getBookById(item.getBookId());
//        BigDecimal unitPrice = book.getPrice();
//        BigDecimal bigDecimal = new BigDecimal(item.getNumber());
//        return bigDecimal.multiply(unitPrice);
//    }


}
