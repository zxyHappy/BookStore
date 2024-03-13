package com.bluemsun.service.impl;

import com.bluemsun.dao.CommentDao;
import com.bluemsun.dao.OrderDao;
import com.bluemsun.entity.Comment;
import com.bluemsun.service.CommentService;

public class CommentServiceImpl implements CommentService {

    @Override
    public String addComment(Comment comment) { //在订单页面点击评论按钮写评论
        CommentDao commentDao = new CommentDao();
        if(commentDao.checkCmtLimit(comment.getUserId(),comment.getBookId())){
            if(commentDao.addComment(comment)) return "评论成功";
            else return "评论失败";
        }else {
            return "无评论权限";
        }
    }

    @Override
    public String delComment(int userId, int bookId,int id) {
        CommentDao commentDao = new CommentDao();
        if(commentDao.checkCmtLimit(userId,bookId) && commentDao.getUserById(id)==userId){
            if(commentDao.delComment(id)) return "删除成功";
            else return "删除失败";
        }else {
            return "无删除权限";
        }
    }
}
