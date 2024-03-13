package com.bluemsun.service.impl;

import com.bluemsun.dao.UserDao;
import com.bluemsun.entity.Page;
import com.bluemsun.entity.User;
import com.bluemsun.service.UserControlService;

import java.util.List;

public class UserControlServiceImpl implements UserControlService {

    @Override
    public Page<User> searchUserByName(String value, int index) {
        UserDao userDao = new UserDao();
        Page<User> page = new Page<>(index,10,userDao.countNumberBySearch(value));
        List<User> list = userDao.getUserBySearch(value,index);
        page.setList(list);
        return page;
    }

    @Override
    public User searchUserById(int userId) {
        UserDao userDao = new UserDao();
        return userDao.getUserById(userId);
    }

    @Override
    public String alertUserStatus(int userId) {
        UserDao userDao = new UserDao();
        if(userDao.alertUser(userId)) return "用户状态更新成功";
        else return "用户状态更新失败";
    }
}
