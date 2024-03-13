package com.bluemsun.service.impl;

import com.bluemsun.dao.UserDao;
import com.bluemsun.entity.User;
import com.bluemsun.service.RegisterService;
import com.bluemsun.util.JSONUtil;

import java.util.HashMap;
import java.util.Map;

public class RegisterServiceImpl implements RegisterService {
    @Override
    public boolean CheckRepeat(String userName) { // 检查账户是否已注册
        UserDao userDao = new UserDao();
        User user = userDao.getUserByName(userName);
        // 尚未注册，可以注册
        if(user == null) return true;
        else return false;
    }

    @Override
    public String RegisterStatus(User user) { //注册
        if(CheckRepeat(user.getUserName())){
            UserDao userDao = new UserDao();
            if(userDao.addUser(user)) return "注册成功";
            else return "注册失败";
        }else{
            return "账户已存在";
        }
    }

    public Map<String,Object> returnMessage(User user){
        Map<String,Object> map = new HashMap<>();
        String message = RegisterStatus(user);
        map.put("message",message);
        return map;
    }
}
