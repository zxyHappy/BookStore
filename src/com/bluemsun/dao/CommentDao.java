package com.bluemsun.dao;

import com.bluemsun.entity.Comment;
import com.bluemsun.util.C3P0Util;
import com.mysql.cj.jdbc.ConnectionImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CommentDao {

    //添加评论,需要判断用户的订单是否已被完成
    public boolean addComment(Comment comment){
        Connection connection = C3P0Util.getConnection();
        PreparedStatement preparedStatement = null;
        String sql ="insert into tb_comment(body,date,userid,bookid) values (?,now(),?,?)";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,comment.getBody());
            preparedStatement.setInt(2,comment.getUserId());
            preparedStatement.setInt(3,comment.getBookId());
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

    //删除评论，需要评论的id;需要判断该评论是否是该用户发的
    public boolean delComment(int id){
        Connection connection = C3P0Util.getConnection();
        PreparedStatement preparedStatement = null;
        String sql = "delete from tb_comment where id = ?";
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

    //在书本页面展示评论,index是page里的startIndex
    public List<Comment> showCommentByBook(int bookId,int index){
        Connection connection = C3P0Util.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String sql = "select tb_comment.*,tb_user.nickname from tb_comment,tb_user where tb_comment.userid = tb_user.userid and tb_comment.bookid = ? order by tb_comment.id desc limit ?,7";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,bookId);
            preparedStatement.setInt(2,index);
            resultSet = preparedStatement.executeQuery();
            if(!resultSet.isBeforeFirst()) return null;
            List<Comment> list = new ArrayList<>();
            while(resultSet.next()){
//                Comment comment = new Comment();
//                comment.setBookId(resultSet.getInt("bookid"));
//                comment.setDate(resultSet.getString("date"));
//                comment.setBody(resultSet.getString("body"));
//                comment.setUserId(resultSet.getInt("userid"));
//                comment.setId(resultSet.getInt("id"));
                Comment comment = new Comment(resultSet.getInt("id"),resultSet.getString("body"),resultSet.getString("date"),resultSet.getInt("userid"),resultSet.getInt("bookid"));
                comment.setNickName(resultSet.getString("nickname"));
                list.add(comment);
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            C3P0Util.releaseConnection(connection,preparedStatement,resultSet);
        }
        return null;
    }

    //在用户页面查看属于自己的评论
    public List<Comment> showCommentByUser(int userid,int index){
        Connection connection = C3P0Util.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String sql = "select tb_comment.*,tb_book.bookname from tb_comment,tb_book where tb_comment.bookid = tb_book.bookid and tb_comment.userid = ? order by tb_comment.id desc limit ?,10";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,userid);
            preparedStatement.setInt(2,index);
            resultSet = preparedStatement.executeQuery();
            if(!resultSet.isBeforeFirst()) return null;
            List<Comment> list = new ArrayList<>();
            while(resultSet.next()){
                Comment comment = new Comment(resultSet.getInt("id"),resultSet.getString("body"),resultSet.getString("date"),resultSet.getInt("userid"),resultSet.getInt("bookid"));
                comment.setBookName(resultSet.getString("bookname"));
                list.add(comment);
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            C3P0Util.releaseConnection(connection,preparedStatement,resultSet);
        }
        return null;
    }

    //用评论id找出对应的userid
    public int getUserById(int id){
        Connection connection = C3P0Util.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String sql = "select * from tb_comment where id = ?";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,id);
            resultSet = preparedStatement.executeQuery();
            if(!resultSet.next()) return 0;
            return resultSet.getInt("userid");
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            C3P0Util.releaseConnection(connection,preparedStatement,resultSet);
        }
        return 0;
    }

    //查看该书所有评论数
    public int countCommentByBook(int bookId){
        Connection connection = C3P0Util.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String sql = "select count(*) from tb_comment where bookid = ?";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,bookId);
            resultSet = preparedStatement.executeQuery();
            if(!resultSet.next()) return 0; // 没有这个书的评论
            return resultSet.getInt("count(*)");
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            C3P0Util.releaseConnection(connection,preparedStatement,resultSet);
        }
        return 0;
    }

    //查看该用户的评论数
    public int countCommentByUser(int userId){
        Connection connection = C3P0Util.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String sql = "select count(*) from tb_comment where userid = ?";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,userId);
            resultSet = preparedStatement.executeQuery();
            if(!resultSet.next()) return 0;     // 没有这个书的评论
            return resultSet.getInt("count(*)");
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            C3P0Util.releaseConnection(connection,preparedStatement,resultSet);
        }
        return 0;
    }

    //评论权限添加
    public boolean addCmtLimit(int userId,int bookId) {
        Connection connection = C3P0Util.getConnection();
        PreparedStatement preparedStatement = null;
        String sql = "insert into tb_cmtlimit (book_id,userid) values (?,?)";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,bookId);
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

    //检查用户评论权限
    public boolean checkCmtLimit(int userid,int bookId) {
        Connection connection = C3P0Util.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String sql = "select * from tb_cmtlimit where userid = ? and book_id = ?";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,userid);
            preparedStatement.setInt(2,bookId);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) return true;
            else return false;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            C3P0Util.releaseConnection(connection,preparedStatement,resultSet);
        }
        return false;
    }
}
