package com.bluemsun.dao;

import com.bluemsun.entity.Book;
import com.bluemsun.util.C3P0Util;
import com.mysql.cj.jdbc.ClientPreparedStatement;
import com.mysql.cj.jdbc.ConnectionImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookDao {

    //通过书名获取同名书籍列表
    public List<Book> getBookByName(String bookName){
        Connection connection = C3P0Util.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String sql = "select * from tb_book where bookName = ? and bookStatus = 1";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,bookName);
            resultSet = preparedStatement.executeQuery();
            if(!resultSet.isBeforeFirst()) return null;
            List<Book> list = new ArrayList<>();
            while (resultSet.next()){
                Book book = new Book(resultSet.getInt("bookId"),resultSet.getString("bookName"),resultSet.getString("Writer"),resultSet.getString("Synopsis"),resultSet.getInt("bookStatus"),resultSet.getString("bookType"),resultSet.getInt("bookNumber"),resultSet.getBigDecimal("Price"),resultSet.getString("bookPhoto"),resultSet.getString("Press"),resultSet.getInt("like_number"));
                list.add(book);
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            C3P0Util.releaseConnection(connection,preparedStatement,resultSet);
        }
        return null;
    }

    public boolean addBook(Book book){ //使用前先把图片的url放进去,这里默认book的信息已经完整
        Connection connection = C3P0Util.getConnection();
        PreparedStatement preparedStatement = null;
        String sql = "insert into tb_book (bookName,Writer,Synopsis,bookStatus,bookType,bookNumber,Price,bookPhoto,Press) values (?,?,?,?,?,?,?,?,?)";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, book.getBookName());
            preparedStatement.setString(2,book.getWriter());
            preparedStatement.setString(3,book.getSynopsis());
            preparedStatement.setInt(4,1);
            preparedStatement.setString(5,book.getBookType());
            preparedStatement.setInt(6,book.getBookNumber());
            preparedStatement.setBigDecimal(7,book.getPrice());
            preparedStatement.setString(8,book.getBookPhoto());
            preparedStatement.setString(9,book.getPress());
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

    public boolean addPhoto(String url,int bookId){ //url和bookId都不能为null，把url加到数据库里
        Connection connection = C3P0Util.getConnection();
        PreparedStatement preparedStatement = null;
        String sql = "insert into tb_bookphotos(photo,bookId) values (?,?)";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,url);
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

    public boolean checkRepeat(Book book){ //判断一本书是否已经存入,未存入为true，已存入为false

        String bookName = book.getBookName();
        String Press = book.getPress();
        String Writer = book.getWriter();

        Connection connection = C3P0Util.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        String sql = "select * from tb_book where bookName = ? and Press = ? and Writer = ?";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,bookName);
            preparedStatement.setString(2,Press);
            preparedStatement.setString(3,Writer);
            resultSet = preparedStatement.executeQuery();
            if(!resultSet.next()) return true;
            else return false;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            C3P0Util.releaseConnection(connection,preparedStatement,resultSet);
        }
        return false;
    }

    public Book getBook(String bookName,String Press,String Writer){  //通过书名，出版社，作者找出book

        Connection connection = C3P0Util.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        String sql = "select * from tb_book where bookName = ? and Press = ? and Writer = ? and bookStatus = 1";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,bookName);
            preparedStatement.setString(2,Press);
            preparedStatement.setString(3,Writer);
            resultSet = preparedStatement.executeQuery();
            if(!resultSet.next()) return null;
            Book book = new Book(resultSet.getInt("bookId"),resultSet.getString("bookName"),resultSet.getString("Writer"),resultSet.getString("Synopsis"),resultSet.getInt("bookStatus"),resultSet.getString("bookType"),resultSet.getInt("bookNumber"),resultSet.getBigDecimal("Price"),resultSet.getString("bookPhoto"),resultSet.getString("Press"),resultSet.getInt("like_number"));
            return book;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            C3P0Util.releaseConnection(connection,preparedStatement,resultSet);
        }
        return null;
    }

    //通过书的id去修改该书信息
    public boolean alertBook(String key,String value,int bookId){
        Connection connection = C3P0Util.getConnection();
        PreparedStatement preparedStatement = null;
        String sql = "update tb_book set "+key+" = ? where bookId = ?";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,value);
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

    //重载上一个方法
    public boolean alertBook(Book book){
        Connection connection = C3P0Util.getConnection();
        PreparedStatement preparedStatement = null;

        String sql = "update tb_book set bookNumber = ?,Price = ? where bookId = ?";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,book.getBookNumber());
            preparedStatement.setBigDecimal(2,book.getPrice());
            preparedStatement.setInt(3,book.getBookId());
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

    //book总数据量
    public int countNumber(){
        Connection connection = C3P0Util.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String sql = "select count(*) from tb_book";
        try {
            preparedStatement = connection.prepareStatement(sql);
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

    //搜索栏用的,分页,最开始index为0
    /*
    * 搜索按钮传搜索的value和index（值为1），这边可以在响应里返回value供前端之后使用
    * 然后点击页码按钮时，将value和index（页码数）作为请求参数
    * */
    public List<Book> getBookBySearch(String value,int index){
        Connection connection = C3P0Util.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String sql = "select * from tb_book where bookId in (select bookId from tb_book where bookName like '%"+value+"%' or Writer = ? or Press = ?) order by like_number desc limit ?,7"; // and bookStatus = 1
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,value);
            preparedStatement.setString(2,value);
            preparedStatement.setInt(3,index);
            resultSet = preparedStatement.executeQuery();
            if(!resultSet.isBeforeFirst()) return null;
            List<Book> list = new ArrayList<>();
            while (resultSet.next()){
                Book book = new Book(resultSet.getInt("bookId"),resultSet.getString("bookName"),resultSet.getString("Writer"),resultSet.getString("Synopsis"),resultSet.getInt("bookStatus"),resultSet.getString("bookType"),resultSet.getInt("bookNumber"),resultSet.getBigDecimal("Price"),resultSet.getString("bookPhoto"),resultSet.getString("Press"),resultSet.getInt("like_number"));
                list.add(book);
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            C3P0Util.releaseConnection(connection,preparedStatement,resultSet);
        }
        return null;
    }

    // 上下架书本,为false的话该id对应的书不存在或者操作无效（已上架的书设置上架，下架的书又设置下架）
    public boolean setStatus(int bookId,int bookStatus){
        Connection connection = C3P0Util.getConnection();
        PreparedStatement preparedStatement = null;
        String sql = "update tb_book set bookStatus = ? where bookId = ?";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,bookStatus);
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

    //通过id获取book,用于书本详情页
    public Book getBookById(int bookId){
        Connection connection = C3P0Util.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String sql = "select * from tb_book where bookId = ?";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,bookId);
            resultSet = preparedStatement.executeQuery();
            if(!resultSet.next()) return null;
            Book book = new Book(resultSet.getInt("bookId"),resultSet.getString("bookName"),resultSet.getString("Writer"),resultSet.getString("Synopsis"),resultSet.getInt("bookStatus"),resultSet.getString("bookType"),resultSet.getInt("bookNumber"),resultSet.getBigDecimal("Price"),resultSet.getString("bookPhoto"),resultSet.getString("Press"),resultSet.getInt("like_number"));
            return book;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            C3P0Util.releaseConnection(connection,preparedStatement,resultSet);
        }
        return null;
    }

    //获取详细图片的url列表，用于详情页
    public List<String> getPhotos(int bookId){
        Connection connection = C3P0Util.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String sql = "select * from tb_bookphotos where bookid = ?";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,bookId);
            resultSet = preparedStatement.executeQuery();
            if(!resultSet.isBeforeFirst()) return null;
            List<String> list = new ArrayList<>();
            while(resultSet.next()){
                list.add(resultSet.getString("photo"));
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            C3P0Util.releaseConnection(connection,preparedStatement,resultSet);
        }
        return null;
    }

    // 获得分页展示中某一页的书的信息
//    public List<Book> getPageBook(int index){
//        Connection connection = C3P0Util.getConnection();
//        PreparedStatement preparedStatement = null;
//        ResultSet resultSet = null;
//        List<Book> list = new ArrayList<>();
//        String sql = "select * from book limit ?,7";
//        try {
//            preparedStatement = connection.prepareStatement(sql);
//            preparedStatement.setInt(1,index);
//            resultSet = preparedStatement.executeQuery();
//            if(!resultSet.isBeforeFirst()) return null;
//            while(resultSet.next()){
//                Book book = new Book();
//                book.setBookPhoto(resultSet.getString("bookPhoto"));
//                book.setBookName(resultSet.getString("bookName"));
//                book.setBookId(resultSet.getInt("bookId"));
//                book.setPrice(resultSet.getBigDecimal("Price"));
//                book.setSynopsis(resultSet.getString("Synopsis"));
//                book.setWriter(resultSet.getString("Writer"));
//                book.setBookNumber(resultSet.getInt("bookNumber"));
//                book.setBookType(resultSet.getString("bookType"));
//                book.setPress(resultSet.getString("Press"));
//                book.setBookStatus(resultSet.getInt("bookStatus"));
//                book.setLikeNumber(resultSet.getInt("like_number"));
//                list.add(book);
//            }
//            return list;
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

    //获取搜索结果总数据量
    public int countNumberBySearch(String value){
        Connection connection = C3P0Util.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String sql = "select count(*) from tb_book where bookName like '%"+value+"%' or Writer = ? or Press = ?";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,value);
            preparedStatement.setString(2,value);
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

    public List<Book> getHot(){
        Connection connection = C3P0Util.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String sql = "select * from tb_book where bookStatus = 1 order by like_number desc limit 0,10";
        try {
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            List<Book> list = new ArrayList<>();
            while(resultSet.next()){
                Book book = new Book(resultSet.getInt("bookId"),resultSet.getString("bookName"),resultSet.getString("Writer"),resultSet.getString("Synopsis"),resultSet.getInt("bookStatus"),resultSet.getString("bookType"),resultSet.getInt("bookNumber"),resultSet.getBigDecimal("Price"),resultSet.getString("bookPhoto"),resultSet.getString("Press"),resultSet.getInt("like_number"));
                list.add(book);
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            C3P0Util.releaseConnection(connection,preparedStatement,resultSet);
        }
        return null;
    }

    //获取搜索结果总数据量,用户页面
    public int countNumberByUser(String value){
        Connection connection = C3P0Util.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String sql = "select count(*) from tb_book where (bookName like '%"+value+"%' or Writer = ? or Press = ?) and bookStatus = 1";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,value);
            preparedStatement.setString(2,value);
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


    //判断库存是否充足
    public boolean judgeNumber(int bookId,int number){
        Connection connection = C3P0Util.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String sql = "select * from tb_book where bookId = ?";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,bookId);
            resultSet = preparedStatement.executeQuery();
            if(!resultSet.next()) return false;
            if(number > resultSet.getInt("bookNumber")) return false;
            else return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            C3P0Util.releaseConnection(connection,preparedStatement,resultSet);
        }
        return false;
    }

    //用户端搜索书籍
    public List<Book> getBookByUser(String value,int index){
        Connection connection = C3P0Util.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String sql = "select * from tb_book where bookId in ( select bookId from tb_book where( bookName like '%"+value+"%' or Writer = ? or Press = ?) and bookStatus = 1 ) order by like_number desc limit ?,10"; // and bookStatus = 1
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,value);
            preparedStatement.setString(2,value);
            preparedStatement.setInt(3,index);
            resultSet = preparedStatement.executeQuery();
            if(!resultSet.isBeforeFirst()) return null;
            List<Book> list = new ArrayList<>();
            while (resultSet.next()){
                Book book = new Book(resultSet.getInt("bookId"),resultSet.getString("bookName"),resultSet.getString("Writer"),resultSet.getString("Synopsis"),resultSet.getInt("bookStatus"),resultSet.getString("bookType"),resultSet.getInt("bookNumber"),resultSet.getBigDecimal("Price"),resultSet.getString("bookPhoto"),resultSet.getString("Press"),resultSet.getInt("like_number"));
                list.add(book);
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            C3P0Util.releaseConnection(connection,preparedStatement,resultSet);
        }
        return null;
    }


    //搜索界面加载时用到
    public List<Book> getSearch(int index){
        Connection connection = C3P0Util.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String sql = "select * from tb_book limit ?,7";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,index);
            resultSet = preparedStatement.executeQuery();
            List<Book> list = new ArrayList<>();
            while(resultSet.next()){
//                Book book = new Book();
//                book.setBookPhoto(resultSet.getString("bookPhoto"));
//                book.setBookName(resultSet.getString("bookName"));
//                book.setBookId(resultSet.getInt("bookId"));
//                book.setPrice(resultSet.getBigDecimal("Price"));
//                book.setSynopsis(resultSet.getString("Synopsis"));
//                book.setWriter(resultSet.getString("Writer"));
//                book.setBookNumber(resultSet.getInt("bookNumber"));
//                book.setBookType(resultSet.getString("bookType"));
//                book.setPress(resultSet.getString("Press"));
//                book.setBookStatus(resultSet.getInt("bookStatus"));
//                book.setLikeNumber(resultSet.getInt("like_number"));
                Book book = new Book(resultSet.getInt("bookId"),resultSet.getString("bookName"),resultSet.getString("Writer"),resultSet.getString("Synopsis"),resultSet.getInt("bookStatus"),resultSet.getString("bookType"),resultSet.getInt("bookNumber"),resultSet.getBigDecimal("Price"),resultSet.getString("bookPhoto"),resultSet.getString("Press"),resultSet.getInt("like_number"));
                list.add(book);
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            C3P0Util.releaseConnection(connection,preparedStatement,resultSet);
        }
        return null;
    }

    //点赞数+1
    public boolean addLikeNumber(int bookId){
        Connection connection = C3P0Util.getConnection();
        PreparedStatement preparedStatement = null;
        String sql = "update tb_book set like_number = like_number+1 where bookid = ?";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,bookId);
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

    //点赞数-1
    public boolean delLikeNumber(int bookId) {
        Connection connection = C3P0Util.getConnection();
        PreparedStatement preparedStatement = null;
        String sql = "update tb_book set like_number = like_number-1 where bookid = ?";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,bookId);
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


    //书本分类查询
    public List<Book> getBookByType(String value,int index){
        Connection connection = C3P0Util.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String sql = "select * from tb_book where booktype like '%"+value+"%' and bookStatus = 1 order by like_number desc limit ?,10";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,index);
            resultSet = preparedStatement.executeQuery();
            if(!resultSet.isBeforeFirst()) return null;
            List<Book> list = new ArrayList<>();
            while (resultSet.next()){
                Book book = new Book(resultSet.getInt("bookId"),resultSet.getString("bookName"),resultSet.getString("Writer"),resultSet.getString("Synopsis"),resultSet.getInt("bookStatus"),resultSet.getString("bookType"),resultSet.getInt("bookNumber"),resultSet.getBigDecimal("Price"),resultSet.getString("bookPhoto"),resultSet.getString("Press"),resultSet.getInt("like_number"));
                list.add(book);
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            C3P0Util.releaseConnection(connection,preparedStatement,resultSet);
        }
        return null;
    }

    //某一类别书本的数量
    public int countBookByType(String value){
        Connection connection = C3P0Util.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String sql = "select count(*) from tb_book where booktype like '%"+value+"%' and bookstatus = 1";
        try {
            preparedStatement = connection.prepareStatement(sql);
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
