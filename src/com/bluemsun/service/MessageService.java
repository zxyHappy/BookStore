package com.bluemsun.service;

public interface MessageService {
    /**
     * 确认通知
     * @param userId 通知所属的用户
     * @param id 通知的id
     * @return
     */
    String confirmMessage(int userId, int id);

    /**
     * 删除通知
     * @param userId
     * @param id
     * @return
     */
    String delMessage(int userId,int id);
}
