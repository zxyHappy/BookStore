package com.bluemsun.service;

public interface LikeService {
    /**
     *检查目前是否已经点赞
     * @param userId
     * @param bookId
     * @return 返回0说明未点赞，返回1说明已点赞
     */
    int checkLike(int userId,int bookId);

    /**
     * @param userId  session里的login_status
     * @param bookId 当前详情页的书本id
     * @return 返回值是0时，说明当前状态为未点赞，返回1时说明当前状态是已点赞
     */
    int alertLike(int userId,int bookId);
}
