package com.bluemsun.service;

import com.bluemsun.entity.Page;
import com.bluemsun.entity.User;

import java.util.List;

public interface UserControlService {
    /**
     * 通过昵称和账号搜索用户（用户管理）
     * @param value
     * @return
     */
    Page<User> searchUserByName(String value, int index);

    /**
     * 通过id获取用户信息（用户管理）
     * @param userId
     * @return
     */
    User searchUserById(int userId);

    /**
     * 更新用户状态（解封/封禁用户）
     * @param userId
     * @return
     */
    String alertUserStatus(int userId);

}
