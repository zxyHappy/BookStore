package com.bluemsun.dao;

import com.bluemsun.entity.User;
import com.bluemsun.util.C3P0Util;
import com.mysql.cj.jdbc.ConnectionImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDao {

    public User getUserByName(String userName){ // 检查账户是否重复,获取账户
        Connection connection = C3P0Util.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String sql = "select * from tb_user where userName = ?";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,userName);
            resultSet = preparedStatement.executeQuery();
            if(!resultSet.next()) return null;
            else {
//                User user = new User();
//                user.setUserId(resultSet.getInt("userId"));
//                user.setNickName(resultSet.getString("nickName"));
//                user.setUserName(resultSet.getString("userName"));
//                user.setPassword(resultSet.getString("Password"));
//                user.setUserPhoto(resultSet.getString("userPhoto"));
//                user.setStatus(resultSet.getInt("status"));
//                user.setUserStatus(resultSet.getInt("userStatus"));
//                user.setTelephone(resultSet.getString("Telephone"));
                User user = new User(resultSet.getInt("userId"),resultSet.getString("nickName"),resultSet.getString("userName"),resultSet.getString("Password"),resultSet.getString("userPhoto"),resultSet.getInt("status"),resultSet.getInt("userStatus"),resultSet.getString("Telephone"));
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            C3P0Util.releaseConnection(connection,preparedStatement,resultSet);
        }
        return null;
    }

    public boolean addUser(User user){ // 添加用户
        Connection connection = C3P0Util.getConnection();
        PreparedStatement preparedStatement = null;
        String sql = "insert into tb_user (userName,Password,nickName,Telephone) values (?,?,?,?)";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,user.getUserName());
            preparedStatement.setString(2,user.getPassword());
            preparedStatement.setString(3,user.getNickName());
            preparedStatement.setString(4,user.getTelephone());
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

    public int getIdByName(String userName){
        Connection connection = C3P0Util.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String sql = "select * from tb_user where userName = ?";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,userName);
            resultSet = preparedStatement.executeQuery();
            if(!resultSet.next()) return 0;
            else{
                int id = resultSet.getInt("userId");
                return id;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            C3P0Util.releaseConnection(connection,preparedStatement,resultSet);
        }
        return 0;
    }

    public User getUserById(int userId){
        Connection connection = C3P0Util.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String sql = "select * from tb_user where userId = ?";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,userId);
            resultSet = preparedStatement.executeQuery();
            if(!resultSet.next()) return null;
            else {
                User user = new User(resultSet.getInt("userId"),resultSet.getString("nickName"),resultSet.getString("userName"),resultSet.getString("Password"),resultSet.getString("userPhoto"),resultSet.getInt("status"),resultSet.getInt("userStatus"),resultSet.getString("Telephone"));
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            C3P0Util.releaseConnection(connection,preparedStatement,resultSet);
        }
        return null;
    }

    //图片url加到数据库里
    public boolean alertPhoto(String userPhoto,int userId){
        Connection connection = C3P0Util.getConnection();
        PreparedStatement preparedStatement = null;
        String sql = "update tb_user set userPhoto = ? where userId = ?";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,userPhoto);
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

    public boolean alertMessage(String nickName,String Password,String Telephone,int userId){
        Connection connection = C3P0Util.getConnection();
        PreparedStatement preparedStatement = null;
        String sql = "update tb_user set nickName = ?,Password = ?,Telephone = ? where userId = ?";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,nickName);
            preparedStatement.setString(2,Password);
            preparedStatement.setString(3,Telephone);
            preparedStatement.setInt(4,userId);
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

    public List<User> getUserBySearch(String value,int index){
        Connection connection = C3P0Util.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String sql = "select * from tb_user where userId in (select userId from tb_user where (nickName like '%"+value+"%' or userName = ?) and userid <> 1) limit ?,10";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,value);
            preparedStatement.setInt(2,index);
            resultSet = preparedStatement.executeQuery();
            if(!resultSet.isBeforeFirst()) return null;
            List<User> list = new ArrayList<>();
            while(resultSet.next()){
                User user = new User(resultSet.getInt("userId"),resultSet.getString("nickName"),resultSet.getString("userName"),resultSet.getString("Password"),resultSet.getString("userPhoto"),resultSet.getInt("status"),resultSet.getInt("userStatus"),resultSet.getString("Telephone"));
                list.add(user);
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            C3P0Util.releaseConnection(connection,preparedStatement,resultSet);
        }
        return null;
    }

    public int countNumberBySearch(String value){
        Connection connection = C3P0Util.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String sql = "select count(*) from tb_user where nickName like '%"+value+"%' or userName = ?";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,value);
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

    //点赞
    public boolean addLike(int userId,int bookId){
        Connection connection = C3P0Util.getConnection();
        PreparedStatement preparedStatement = null;
        String sql = "insert into tb_like(userid,book_id) values (?,?) ";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,userId);
            preparedStatement.setInt(2,bookId);
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

    //取消点赞
    public boolean delLike(int userId,int bookId){
        Connection connection = C3P0Util.getConnection();
        PreparedStatement preparedStatement = null;
        String sql = "delete from tb_like where userid = ? and book_id = ?";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,userId);
            preparedStatement.setInt(2,bookId);
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

    //判断是否已点赞,返回值为0和1，也要给前端用
    public int checkLike(int userId,int bookId){
        Connection connection = C3P0Util.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String sql = "select * from tb_like where userid = ? and book_id = ?";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,userId);
            preparedStatement.setInt(2,bookId);
            resultSet = preparedStatement.executeQuery();
            if(!resultSet.next()) return 0;
            else return 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            C3P0Util.releaseConnection(connection,preparedStatement,resultSet);
        }
        return 0;
    }

    //封禁用户/解封用户
    public boolean alertUser(int userId){
        Connection connection = C3P0Util.getConnection();
        PreparedStatement preparedStatement = null;
        String sql = "update tb_user set userstatus = userstatus ^ 1 where userid = ?";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,userId);
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

}
