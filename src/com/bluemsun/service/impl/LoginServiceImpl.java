package com.bluemsun.service.impl;

import com.bluemsun.dao.UserDao;
import com.bluemsun.entity.User;
import com.bluemsun.service.LoginService;
import com.bluemsun.util.JSONUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.HashMap;
import java.util.Map;

public class LoginServiceImpl implements LoginService {

    @Override
    public String login(User user) { //user为用户输入
        UserDao userDao = new UserDao();
        User realUser = userDao.getUserByName(user.getUserName()); //数据库信息
        if(realUser == null) return "无此账户";
        if(realUser.getUserStatus() == 0) return "该账号禁止登录";
        if(!user.getPassword().equals(realUser.getPassword())) return "密码错误";
        else return "登录成功";
    }

    public Map<String,Object> returnMessage(User user){
        String message = login(user);
        Map<String,Object> map = new HashMap<>();
        map.put("message",message);
        map.put("status",-1); //无用
        map.put("userStatus",-1); //无用
        if(message.equals("登录成功")){
            UserDao userDao = new UserDao();
            User realUser = userDao.getUserByName(user.getUserName());
            map.put("status",realUser.getStatus());
            map.put("userStatus",realUser.getUserStatus());
        }
        return map;
    }
}
