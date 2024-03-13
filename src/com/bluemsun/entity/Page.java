package com.bluemsun.entity;

import java.util.List;

public class Page<T> {

    private int currentPage;
    private int pageSize;
    private int totalRecord;
    List<T> list;
    private int totalPage;
    private int startIndex;

    public Page(int currentPage,int pageSize,int totalRecord){
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.totalRecord = totalRecord;

        if(totalRecord % pageSize == 0){
            this.totalPage = totalRecord/pageSize;
        }else{
            this.totalPage = totalRecord/pageSize+1;
        }

        this.startIndex = (currentPage-1)*pageSize;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalRecord() {
        return totalRecord;
    }

    public void setTotalRecord(int totalRecord) {
        this.totalRecord = totalRecord;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }
}
