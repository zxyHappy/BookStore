package com.bluemsun.dao;

import com.bluemsun.entity.Order;
import com.bluemsun.entity.Page;
import com.bluemsun.util.C3P0Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDao {

    //用户端添加订单
    public boolean addOrder(Order order){
        Connection connection = C3P0Util.getConnection();
        PreparedStatement preparedStatement = null;
        String sql = "insert into tb_orderform(order_book,order_user,date,unit_price,price,number,address,order_status) values (?,?,now(),?,?,?,?,?)";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,order.getBookId());
            preparedStatement.setInt(2,order.getUserId());
            preparedStatement.setBigDecimal(3,order.getUnitPrice());
            preparedStatement.setBigDecimal(4,order.getPrice());
            preparedStatement.setInt(5,order.getNumber());
            preparedStatement.setString(6,order.getAddress());
            preparedStatement.setInt(7,order.getStatus());
            int i = preparedStatement.executeUpdate();
            if(i == 1) return true;
            else return false;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            C3P0Util.releaseConnection(connection,preparedStatement,null);
        }
        return false;
    }

    //商家确认订单
    public boolean confirmOrder(int orderId,int number,int bookId){ //orderId为前端传输，后俩可以自己找出来
        Connection connection = C3P0Util.getConnection();
        PreparedStatement preparedStatement = null;
        String sql1 = "update tb_orderform set order_status = 1 where order_id = ?";
        String sql2 = "update tb_book set bookNumber = bookNumber - ? where bookId = ?";
        try {
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(sql1);
            preparedStatement.setInt(1,orderId);
            int i = preparedStatement.executeUpdate();
            if(i == 1){
                preparedStatement = connection.prepareStatement(sql2);
                preparedStatement.setInt(1,number);
                preparedStatement.setInt(2,bookId);
                int j = preparedStatement.executeUpdate();
                if(j == 1) return true;
                else {
                    connection.rollback();
                    return false;
                }
            }
            else return false;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                connection.commit();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            C3P0Util.releaseConnection(connection,preparedStatement,null);
        }
        return false;
    }

    //商家页面展示订单
    public List<Order> showOrderByManger(int index) {
        Connection connection = C3P0Util.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String sql = "select tb_orderform.*,tb_book.bookName,tb_user.nickName from tb_orderform,tb_user,tb_book where (tb_orderform.order_book = tb_book.bookId and tb_orderform.order_user = tb_user.userId )order by order_id desc limit ?,7";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,index);
            resultSet = preparedStatement.executeQuery();
            if(!resultSet.isBeforeFirst()) return null;
            List<Order> list = new ArrayList<>();
            while(resultSet.next()){
                Order order = new Order(resultSet.getInt("order_user"),resultSet.getInt("order_book"),resultSet.getBigDecimal("unit_price"),resultSet.getInt("number"),resultSet.getString("address"),resultSet.getString("date"),resultSet.getString("bookName"),resultSet.getString("nickName"),resultSet.getInt("order_id"),resultSet.getInt("order_status"));
//                order.setDate(resultSet.getString("date")); //日期得单独设置
//                order.setBookName(resultSet.getString("bookName"));
//                order.setNickName(resultSet.getString("nickName"));
//                order.setId(resultSet.getInt("order_id"));
//                order.setStatus(resultSet.getInt("order_status"));
                list.add(order);
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            C3P0Util.releaseConnection(connection,preparedStatement,resultSet);
        }
        return null;
    }

    //所有订单数查询
    public int countOrder(){
        Connection connection = C3P0Util.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String sql = "select count(*) from tb_orderform";
        try {
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            if(!resultSet.next()) return 0;
            return resultSet.getInt("count(*)");
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            C3P0Util.releaseConnection(connection,preparedStatement,resultSet);
        }
        return 0;
    }

    //已确认未完成订单数查询
//    public int countOrderByUser(){
//        Connection connection = C3P0Util.getConnection();
//        PreparedStatement preparedStatement = null;
//        ResultSet resultSet = null;
//        String sql = "select count(*) from orderform where order_status = 1";
//        try {
//            preparedStatement = connection.prepareStatement(sql);
//            resultSet = preparedStatement.executeQuery();
//            if(!resultSet.next()) return 0;
//            return resultSet.getInt("count(*)");
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return 0;
//    }

    //用户页面展示订单,userId从session中获取,用来选出关于该用户的订单
    public List<Order> showOrderByUser(int index,int userId) {
        Connection connection = C3P0Util.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String sql = "select tb_orderform.*,tb_book.bookName,tb_user.nickName from tb_orderform,tb_user,tb_book where( (tb_orderform.order_book = tb_book.bookId and tb_orderform.order_user = tb_user.userId ) and order_user = ?) order by order_id desc limit ?,7";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,userId);
            preparedStatement.setInt(2,index);
            resultSet = preparedStatement.executeQuery();
            if(!resultSet.isBeforeFirst()) return null;
            List<Order> list = new ArrayList<>();
            while(resultSet.next()){
                Order order = new Order(resultSet.getInt("order_user"),resultSet.getInt("order_book"),resultSet.getBigDecimal("unit_price"),resultSet.getInt("number"),resultSet.getString("address"),resultSet.getString("date"),resultSet.getString("bookName"),resultSet.getString("nickName"),resultSet.getInt("order_id"),resultSet.getInt("order_status"));
                list.add(order);
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            C3P0Util.releaseConnection(connection,preparedStatement,resultSet);
        }
        return null;
    }


    //用户未确认订单展示
    public List<Order> showOrderByUserNotConfirm(int index,int userId) {
        Connection connection = C3P0Util.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String sql = "select tb_orderform.*,tb_book.bookName,tb_user.nickName from tb_orderform,tb_user,tb_book where( (tb_orderform.order_book = tb_book.bookId and tb_orderform.order_user = tb_user.userId ) and order_user = ? and order_status = 0 ) order by order_id desc limit ?,7";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,userId);
            preparedStatement.setInt(2,index);
            resultSet = preparedStatement.executeQuery();
            if(!resultSet.isBeforeFirst()) return null;
            List<Order> list = new ArrayList<>();
            while(resultSet.next()){
                Order order = new Order(resultSet.getInt("order_user"),resultSet.getInt("order_book"),resultSet.getBigDecimal("unit_price"),resultSet.getInt("number"),resultSet.getString("address"),resultSet.getString("date"),resultSet.getString("bookName"),resultSet.getString("nickName"),resultSet.getInt("order_id"),resultSet.getInt("order_status"));
                list.add(order);
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            C3P0Util.releaseConnection(connection,preparedStatement,resultSet);
        }
        return null;
    }

    //用户未完成订单展示
    public List<Order> showOrderByUserNotFinish(int index,int userId) {
        Connection connection = C3P0Util.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String sql = "select tb_orderform.*,tb_book.bookName,tb_user.nickName from tb_orderform,tb_user,tb_book where( (tb_orderform.order_book = tb_book.bookId and tb_orderform.order_user = tb_user.userId ) and order_user = ? and order_status = 1 ) order by order_id desc limit ?,7";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,userId);
            preparedStatement.setInt(2,index);
            resultSet = preparedStatement.executeQuery();
            if(!resultSet.isBeforeFirst()) return null;
            List<Order> list = new ArrayList<>();
            while(resultSet.next()){
                Order order = new Order(resultSet.getInt("order_user"),resultSet.getInt("order_book"),resultSet.getBigDecimal("unit_price"),resultSet.getInt("number"),resultSet.getString("address"),resultSet.getString("date"),resultSet.getString("bookName"),resultSet.getString("nickName"),resultSet.getInt("order_id"),resultSet.getInt("order_status"));
                list.add(order);
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            C3P0Util.releaseConnection(connection,preparedStatement,resultSet);
        }
        return null;
    }

    //用户已完成订单展示
    public List<Order> showOrderByUserFinish(int index,int userId) {
        Connection connection = C3P0Util.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String sql = "select tb_orderform.*,tb_book.bookName,tb_user.nickName from tb_orderform,tb_user,tb_book where( (tb_orderform.order_book = tb_book.bookId and tb_orderform.order_user = tb_user.userId ) and order_user = ? and order_status = 2 ) order by order_id desc limit ?,7";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,userId);
            preparedStatement.setInt(2,index);
            resultSet = preparedStatement.executeQuery();
            if(!resultSet.isBeforeFirst()) return null;
            List<Order> list = new ArrayList<>();
            while(resultSet.next()){
                Order order = new Order(resultSet.getInt("order_user"),resultSet.getInt("order_book"),resultSet.getBigDecimal("unit_price"),resultSet.getInt("number"),resultSet.getString("address"),resultSet.getString("date"),resultSet.getString("bookName"),resultSet.getString("nickName"),resultSet.getInt("order_id"),resultSet.getInt("order_status"));
                list.add(order);
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            C3P0Util.releaseConnection(connection,preparedStatement,resultSet);
        }
        return null;
    }

    //检查订单状态
    public int checkOrder(int orderId){
        Connection connection = C3P0Util.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String sql = "select * from tb_orderform where order_id = ?";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,orderId);
            resultSet = preparedStatement.executeQuery();
            if(!resultSet.next()) return -1; //无此订单，返回-1
            return resultSet.getInt("order_status");
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            C3P0Util.releaseConnection(connection,preparedStatement,resultSet);
        }
        return 0;
    }

    //通过orderId找到order,这个order中只用到了number和bookId
    public Order getOrderById(int orderId){
        Connection connection = C3P0Util.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String sql = "select * from tb_orderform where order_id = ?";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,orderId);
            resultSet = preparedStatement.executeQuery();
            if(!resultSet.next()) return null;
            Order order = new Order();
            order.setNumber(resultSet.getInt("number"));
            order.setBookId(resultSet.getInt("order_book"));
            order.setUserId(resultSet.getInt("order_user"));
            return order;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            C3P0Util.releaseConnection(connection,preparedStatement,resultSet);
        }
        return null;
    }

    //仅查看未确认的订单
    public List<Order> showOrderByMangerNotConfirm(int index) {
        Connection connection = C3P0Util.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String sql = "select tb_orderform.*,tb_book.bookName,tb_user.nickName from tb_orderform,tb_user,tb_book where (tb_orderform.order_book = tb_book.bookId and tb_orderform.order_user = tb_user.userId and tb_orderform.order_status = 0 )order by order_id desc limit ?,7";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,index);
            resultSet = preparedStatement.executeQuery();
            if(!resultSet.isBeforeFirst()) return null;
            List<Order> list = new ArrayList<>();
            while(resultSet.next()){
                Order order = new Order(resultSet.getInt("order_user"),resultSet.getInt("order_book"),resultSet.getBigDecimal("unit_price"),resultSet.getInt("number"),resultSet.getString("address"),resultSet.getString("date"),resultSet.getString("bookName"),resultSet.getString("nickName"),resultSet.getInt("order_id"),resultSet.getInt("order_status"));
                list.add(order);
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            C3P0Util.releaseConnection(connection,preparedStatement,resultSet);
        }
        return null;
    }

    //未确认订单数
    public int countOrderNotConfirm(){
        Connection connection = C3P0Util.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String sql = "select count(*) from tb_orderform where order_status = 0";
        try {
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            if(!resultSet.next()) return 0;
            return resultSet.getInt("count(*)");
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            C3P0Util.releaseConnection(connection,preparedStatement,resultSet);
        }
        return 0;
    }

    //未完成订单数
    public int countOrderNotFinish(){
        Connection connection = C3P0Util.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String sql = "select count(*) from tb_orderform where order_status = 1";
        try {
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            if(!resultSet.next()) return 0;
            return resultSet.getInt("count(*)");
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            C3P0Util.releaseConnection(connection,preparedStatement,resultSet);
        }
        return 0;
    }

    //已完成订单数
    public int countOrderFinish(){
        Connection connection = C3P0Util.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String sql = "select count(*) from tb_orderform where order_status = 2";
        try {
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            if(!resultSet.next()) return 0;
            return resultSet.getInt("count(*)");
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            C3P0Util.releaseConnection(connection,preparedStatement,resultSet);
        }
        return 0;
    }

    //商家删除订单
    public boolean delOrder(int orderId){
        Connection connection = C3P0Util.getConnection();
        PreparedStatement preparedStatement = null;
        String sql = "delete from tb_orderform where order_id = ?";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,orderId);
            int i = preparedStatement.executeUpdate();
            if(i == 1) return true;
            else return false;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            C3P0Util.releaseConnection(connection,preparedStatement,null);
        }
        return false;
    }

    //用户完成订单,使用前先检查订单状态
    public boolean finishOrder(int orderId){
        Connection connection = C3P0Util.getConnection();
        PreparedStatement preparedStatement = null;
        String sql = "update tb_orderform set order_status = 2 where order_id = ?";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,orderId);
            int i = preparedStatement.executeUpdate();
            if(i == 1) return true;
            else return false;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            C3P0Util.releaseConnection(connection,preparedStatement,null);
        }
        return false;
    }

    //查看订单属于哪个用户
    public int showUser(int orderId){
        Connection connection = C3P0Util.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String sql = "select * from tb_orderform where order_id = ?";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,orderId);
            resultSet = preparedStatement.executeQuery();
            if(!resultSet.next()) return 0;
            return resultSet.getInt("order_user");
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            C3P0Util.releaseConnection(connection,preparedStatement,resultSet);
        }
        return 0;
    }

    //用户订单数查询
    public int countOrderByUser(int userId){
        Connection connection = C3P0Util.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String sql = "select count(*) from tb_orderform where order_user = ?";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,userId);
            resultSet = preparedStatement.executeQuery();
            if(!resultSet.next()) return 0; //莫得这个用户的订单
            return resultSet.getInt("count(*)");
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            C3P0Util.releaseConnection(connection,preparedStatement,resultSet);
        }
        return 0;
    }

    //用户未确认订单数查询
    public int countOrderByUserNotConfirm(int userId){
        Connection connection = C3P0Util.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String sql = "select count(*) from tb_orderform where order_user = ? and order_status = 0";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,userId);
            resultSet = preparedStatement.executeQuery();
            if(!resultSet.next()) return 0; //莫得这个用户的订单
            return resultSet.getInt("count(*)");
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            C3P0Util.releaseConnection(connection,preparedStatement,resultSet);
        }
        return 0;
    }


    //用户未完成订单数查询
    public int countOrderByUserNotFinish(int userId){
        Connection connection = C3P0Util.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String sql = "select count(*) from tb_orderform where order_user = ? and order_status = 1";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,userId);
            resultSet = preparedStatement.executeQuery();
            if(!resultSet.next()) return 0; //莫得这个用户的订单
            return resultSet.getInt("count(*)");
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            C3P0Util.releaseConnection(connection,preparedStatement,resultSet);
        }
        return 0;
    }

    //用户已完成完成订单数查询
    public int countOrderByUserFinish(int userId){
        Connection connection = C3P0Util.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String sql = "select count(*) from tb_orderform where order_user = ? and order_status = 2";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,userId);
            resultSet = preparedStatement.executeQuery();
            if(!resultSet.next()) return 0; //莫得这个用户的订单
            return resultSet.getInt("count(*)");
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            C3P0Util.releaseConnection(connection,preparedStatement,resultSet);
        }
        return 0;
    }

    //仅查看未完成的订单
    public List<Order> showOrderByMangerNotFinish(int index) {
        Connection connection = C3P0Util.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String sql = "select tb_orderform.*,tb_book.bookName,tb_user.nickName from tb_orderform,tb_user,tb_book where (tb_orderform.order_book = tb_book.bookId and tb_orderform.order_user = tb_user.userId and tb_orderform.order_status = 1 )order by order_id desc limit ?,7";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,index);
            resultSet = preparedStatement.executeQuery();
            if(!resultSet.isBeforeFirst()) return null;
            List<Order> list = new ArrayList<>();
            while(resultSet.next()){
                Order order = new Order(resultSet.getInt("order_user"),resultSet.getInt("order_book"),resultSet.getBigDecimal("unit_price"),resultSet.getInt("number"),resultSet.getString("address"),resultSet.getString("date"),resultSet.getString("bookName"),resultSet.getString("nickName"),resultSet.getInt("order_id"),resultSet.getInt("order_status"));
                list.add(order);
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            C3P0Util.releaseConnection(connection,preparedStatement,resultSet);
        }
        return null;
    }

    //仅查看已完成的订单
    public List<Order> showOrderByMangerFinish(int index) {
        Connection connection = C3P0Util.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String sql = "select tb_orderform.*,tb_book.bookName,tb_user.nickName from tb_orderform,tb_user,tb_book where (tb_orderform.order_book = tb_book.bookId and tb_orderform.order_user = tb_user.userId and tb_orderform.order_status = 2 )order by order_id desc limit ?,7";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,index);
            resultSet = preparedStatement.executeQuery();
            if(!resultSet.isBeforeFirst()) return null;
            List<Order> list = new ArrayList<>();
            while(resultSet.next()){
                Order order = new Order(resultSet.getInt("order_user"),resultSet.getInt("order_book"),resultSet.getBigDecimal("unit_price"),resultSet.getInt("number"),resultSet.getString("address"),resultSet.getString("date"),resultSet.getString("bookName"),resultSet.getString("nickName"),resultSet.getInt("order_id"),resultSet.getInt("order_status"));
                list.add(order);
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            C3P0Util.releaseConnection(connection,preparedStatement,resultSet);
        }
        return null;
    }


}
