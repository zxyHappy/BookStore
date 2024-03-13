package com.bluemsun.service;

import com.bluemsun.entity.Comment;

public interface CommentService {
    /**
     * 添加评论
     * @param comment
     * @return
     */
    String addComment(Comment comment);

    /**
     * session里的userid用来判断是否评论属于该用户
     * @param userId 评论的用户的id
     * @param bookId 评论所属的书本id
     * @param id 评论id
     * @return
     */
    String delComment(int userId,int bookId,int id);
}
