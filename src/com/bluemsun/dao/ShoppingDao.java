package com.bluemsun.dao;

import com.bluemsun.entity.Book;
import com.bluemsun.entity.ShoppingItem;
import com.bluemsun.util.C3P0Util;
import com.mysql.cj.jdbc.ConnectionImpl;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ShoppingDao {

    //获取购物车中的条目
    public ShoppingItem getItemById(int id){
        Connection connection = C3P0Util.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String sql = "select * from tb_shopping where id = ?";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,id);
            resultSet = preparedStatement.executeQuery();
            if(!resultSet.next()) return null;
            ShoppingItem shoppingItem = new ShoppingItem(resultSet.getInt("id"),resultSet.getInt("userid"),resultSet.getInt("book_id"),resultSet.getInt("number"));
            return shoppingItem;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            C3P0Util.releaseConnection(connection,preparedStatement,resultSet);
        }
        return null;
    }

    //获取user购物车的所有条目
    public List<ShoppingItem> getItemByUser(int userId) {
        Connection connection = C3P0Util.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String sql = "select * from tb_shopping where userid = ? order by id desc ";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,userId);
            resultSet = preparedStatement.executeQuery();
            if(!resultSet.isBeforeFirst()) return null;
            BookDao bookDao = new BookDao();
            List<ShoppingItem> list = new ArrayList<>();
            while(resultSet.next()){
                ShoppingItem shoppingItem = new ShoppingItem(resultSet.getInt("id"),resultSet.getInt("userid"),resultSet.getInt("book_id"),resultSet.getInt("number"));
                Book book = bookDao.getBookById(shoppingItem.getBookId());
                shoppingItem.setBookName(book.getBookName());
                shoppingItem.setPhoto(book.getBookPhoto());
                shoppingItem.setUnitPrice(book.getPrice());
                shoppingItem.setPrice(shoppingItem.getUnitPrice().multiply(new BigDecimal(shoppingItem.getNumber())));
                shoppingItem.setTotalNumber(bookDao.getBookById(shoppingItem.getBookId()).getBookNumber());
                list.add(shoppingItem);
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            C3P0Util.releaseConnection(connection,preparedStatement,resultSet);
        }
        return null;
    }

    //更改该条目的数量
    public boolean alertItem(int id,int number){
        Connection connection = C3P0Util.getConnection();
        PreparedStatement preparedStatement = null;
        String sql = "update tb_shopping set number = ? where id = ?";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,number);
            preparedStatement.setInt(2,id);
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

    //删除该条目
    public boolean delItem(int id){
        Connection connection = C3P0Util.getConnection();
        PreparedStatement preparedStatement = null;
        String sql = "delete from tb_shopping where id = ?";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,id);
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

    //检查购物车里是否已有该商品,没有是false，有是true
    public boolean checkItem(int userId,int bookId){
        Connection connection = C3P0Util.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String sql = "select * from tb_shopping where userid = ? and book_id = ?";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,userId);
            preparedStatement.setInt(2,bookId);
            resultSet = preparedStatement.executeQuery();
            if(!resultSet.next()) return false;
            else return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            C3P0Util.releaseConnection(connection,preparedStatement,resultSet);
        }
        return false;
    }

    public boolean addItem(ShoppingItem item){
        Connection connection = C3P0Util.getConnection();
        PreparedStatement preparedStatement = null;
        String sql = "insert into tb_shopping (book_id,userid,number) values (?,?,?)";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,item.getBookId());
            preparedStatement.setInt(2,item.getUserId());
            preparedStatement.setInt(3,item.getNumber());
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

    public boolean addNumber(int id,int number2){
        Connection connection = C3P0Util.getConnection();
        PreparedStatement preparedStatement = null;
        String sql = "update tb_shopping set number = number + ? where id = ?";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,number2);
            preparedStatement.setInt(2,id);
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


    //获取购物车中商品
    public ShoppingItem getItem(int userId,int bookId){
        Connection connection = C3P0Util.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String sql = "select * from tb_shopping where userid = ? and book_id = ?";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,userId);
            preparedStatement.setInt(2,bookId);
            resultSet = preparedStatement.executeQuery();
            if(!resultSet.next()) return null;
            ShoppingItem item = new ShoppingItem();
            item.setUserId(resultSet.getInt("userid"));
            item.setBookId(resultSet.getInt("book_id"));
            item.setId(resultSet.getInt("id"));
            return item;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            C3P0Util.releaseConnection(connection,preparedStatement,resultSet);
        }
        return null;
    }

    public int countItem(int userId){
        Connection connection = C3P0Util.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String sql = "select count(*) from tb_shopping where userid = ?";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,userId);
            resultSet = preparedStatement.executeQuery();
            if(!resultSet.next()) return 0;
            else return resultSet.getInt("count(*)");
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            C3P0Util.releaseConnection(connection,preparedStatement,resultSet);
        }
        return 0;
    }



}
