package com.bluemsun.dao;

import com.bluemsun.entity.Message;
import com.bluemsun.util.C3P0Util;
import com.mysql.cj.jdbc.ConnectionImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MessageDao {

    //用户通知数
    public int countMessageByUser(int userId){
        Connection connection = C3P0Util.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String sql = "select count(*) from tb_message where userid = ?";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,userId);
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

    //商家通知数
    public int countMessage(){
        Connection connection = C3P0Util.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String sql = "select count(*) from tb_message where userid = 1";
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


    //发送通知
    public boolean addMessage(String msg,int userId){
        Connection connection = C3P0Util.getConnection();
        PreparedStatement preparedStatement = null;
        String sql = "insert into tb_message (body,date,userid) values (?,now(),?)";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,msg);
            preparedStatement.setInt(2,userId);
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

    //确认通知
    public boolean confirmMessage(int id){
        Connection connection = C3P0Util.getConnection();
        PreparedStatement preparedStatement = null;
        String sql = "update tb_message set status = 1 where id = ?";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,id);
            if(preparedStatement.executeUpdate() == 1) return true;
            else return false;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            C3P0Util.releaseConnection(connection,preparedStatement,null);
        }
        return false;
    }


    //用户页面查看通知
    public List<Message> showMessageByUser(int userId,int index){
        Connection connection = C3P0Util.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String sql = "select * from tb_message where userid = ? order by status asc,id desc limit ?,10";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,userId);
            preparedStatement.setInt(2,index);
            resultSet = preparedStatement.executeQuery();
            if(!resultSet.isBeforeFirst()) return null;
            List<Message> list = new ArrayList<>();
            while(resultSet.next()){
                Message message = new Message();
                message.setDate(resultSet.getString("date"));
                message.setId(resultSet.getInt("id"));
                message.setBody(resultSet.getString("body"));
                message.setStatus(resultSet.getInt("status"));
                message.setUserId(resultSet.getInt("userid"));
                list.add(message);
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            C3P0Util.releaseConnection(connection,preparedStatement,resultSet);
        }
        return null;
    }

    //商家页面查看通知
    public List<Message> showMessage(int index){
        Connection connection = C3P0Util.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String sql = "select * from tb_message where userid = 1 order by status asc,id desc limit ?,10";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,index);
            resultSet = preparedStatement.executeQuery();
            if(!resultSet.isBeforeFirst()) return null;
            List<Message> list = new ArrayList<>();
            while(resultSet.next()){
                Message message = new Message();
                message.setDate(resultSet.getString("date"));
                message.setId(resultSet.getInt("id"));
                message.setBody(resultSet.getString("body"));
                message.setStatus(resultSet.getInt("status"));
                message.setUserId(resultSet.getInt("userid"));
                list.add(message);
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            C3P0Util.releaseConnection(connection,preparedStatement,resultSet);
        }
        return null;
    }

    //删除通知
    public boolean delMessage(int id){
        Connection connection = C3P0Util.getConnection();
        PreparedStatement preparedStatement = null;
        String sql = "delete from tb_message where id = ?";
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

    //获取通知
    public Message getMessage(int id){
        Connection connection = C3P0Util.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String sql = "select * from tb_message where id = ?";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,id);
            resultSet = preparedStatement.executeQuery();
            if(!resultSet.next()) return null;
            Message message = new Message();
            message.setUserId(resultSet.getInt("userid"));
            message.setStatus(resultSet.getInt("status"));
            message.setId(id);
            message.setDate(resultSet.getString("date"));
            message.setBody(resultSet.getString("body"));
            return message;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            C3P0Util.releaseConnection(connection,preparedStatement,resultSet);
        }
        return null;
    }

    public int checkStatus(int id){
        Connection connection = C3P0Util.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String sql = "select * from tb_message where id = ?";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,id);
            resultSet = preparedStatement.executeQuery();
            if(!resultSet.next()) return -1;
            return resultSet.getInt("status");
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            C3P0Util.releaseConnection(connection,preparedStatement,resultSet);
        }
        return -1;
    }



}
