package com.bluemsun.service.impl;

import com.bluemsun.dao.MessageDao;
import com.bluemsun.service.MessageService;

public class MessageServiceImpl implements MessageService {
    @Override
    public String confirmMessage(int userId,int id) {
        MessageDao messageDao = new MessageDao();
        if(messageDao.getMessage(id).getUserId() == userId && messageDao.checkStatus(id)==0){
            if(messageDao.confirmMessage(id)) return "已确认";
            else return "确认失败";
        }else {
            return "无法进行确认";
        }
    }

    @Override
    public String delMessage(int userId,int id) {
        MessageDao messageDao = new MessageDao();
        if(messageDao.getMessage(id).getUserId() == userId){
            if(messageDao.delMessage(id)) return "已删除";
            else return "删除失败";
        }else {
            return "无删除权限";
        }
    }
}
