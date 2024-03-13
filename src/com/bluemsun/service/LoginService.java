package com.bluemsun.service;

import com.bluemsun.entity.User;

import java.util.Map;

public interface LoginService {
    /**
     * 用户登录，判断用户是否被封禁
     * @param user
     * @return
     */
    String login(User user);

    /**
     * 返回登录信息
     * @param user
     * @return
     */
    Map<String,Object> returnMessage(User user);
}
