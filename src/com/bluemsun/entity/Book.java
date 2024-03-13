package com.bluemsun.entity;

import java.math.BigDecimal;

public class Book {

    private int bookId;
    private String bookName;
    private String Writer;
    private String Synopsis;
    private int bookStatus = 1;
    private String bookType;
    private int bookNumber;
    private BigDecimal Price;
    private String bookPhoto = "http://eihei.natapp1.cc/Userhead/m.png";
    private String Press;
    private int likeNumber;

    public Book(){}

    public Book(int bookId,String bookName,String Writer,String Synopsis, int bookStatus,String bookType,int bookNumber,BigDecimal Price,String bookPhoto,String Press,int likeNumber){
        this.bookId = bookId;
        this.bookName = bookName;
        this.Writer = Writer;
        this.Synopsis = Synopsis;
        this.bookStatus = bookStatus;
        this.bookType = bookType;
        this.bookNumber = bookNumber;
        this.Price = Price;
        this.bookPhoto = bookPhoto;
        this.Press = Press;
        this.likeNumber = likeNumber;
    }

    public int getLikeNumber() {
        return likeNumber;
    }

    public void setLikeNumber(int likeNumber) {
        this.likeNumber = likeNumber;
    }

    public String getPress() {
        return Press;
    }

    public void setPress(String press) {
        Press = press;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getWriter() {
        return Writer;
    }

    public void setWriter(String writer) {
        Writer = writer;
    }

    public String getSynopsis() {
        return Synopsis;
    }

    public void setSynopsis(String synopsis) {
        Synopsis = synopsis;
    }

    public int getBookStatus() {
        return bookStatus;
    }

    public void setBookStatus(int bookStatus) {
        this.bookStatus = bookStatus;
    }

    public String getBookType() {
        return bookType;
    }

    public void setBookType(String bookType) {
        this.bookType = bookType;
    }

    public int getBookNumber() {
        return bookNumber;
    }

    public void setBookNumber(int bookNumber) {
        this.bookNumber = bookNumber;
    }

    public BigDecimal getPrice() {
        return Price;
    }

    public void setPrice(BigDecimal price) {
        Price = price;
    }

    public String getBookPhoto() {
        return bookPhoto;
    }

    public void setBookPhoto(String bookPhoto) {
        this.bookPhoto = bookPhoto;
    }
}
