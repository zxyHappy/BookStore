package com.bluemsun.entity;

import java.math.BigDecimal;

public class Order {
    private int id;
    private int bookId;
    private int userId;
    private String date;
    private BigDecimal unitPrice;
    private BigDecimal price;
    private int number;
    private String address;
    private int status;
    private String bookName;
    private String nickName;


    public String getBookName() {
        return bookName;
    }
    public Order(){}

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getNiceName() {
        return nickName;
    }

    public void setNickName(String niceName) {
        this.nickName = niceName;
    }

    public Order(int userId, int bookId, BigDecimal unitPrice, int number, String address){ //这是需要的数据,除日期外的数据初始化
        this.userId = userId;
        this.bookId = bookId;
        this.unitPrice = unitPrice;
        BigDecimal bigDecimal = new BigDecimal(number);
        this.price = unitPrice.multiply(bigDecimal);
        this.number = number;
        this.status = 0; //默认订单未被确认，若是订单被确认，需要单独设置setStatus
        this.address = address;
    }

    public Order(int userId, int bookId, BigDecimal unitPrice, int number, String address,String date, String bookName,String nickName, int id,int status){ //这是需要的数据,除日期外的数据初始化
        this.userId = userId;
        this.bookId = bookId;
        this.unitPrice = unitPrice;
        BigDecimal bigDecimal = new BigDecimal(number);
        this.price = unitPrice.multiply(bigDecimal);
        this.number = number;
        this.status = status;
        this.address = address;
        this.date = date;
        this.bookName = bookName;
        this.nickName = nickName;
        this.id = id;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
