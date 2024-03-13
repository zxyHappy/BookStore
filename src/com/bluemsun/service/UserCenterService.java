package com.bluemsun.service;

import com.bluemsun.entity.User;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;

@MultipartConfig(maxFileSize = 5*1024*1024,fileSizeThreshold = 6*1024*1024)
public interface UserCenterService {
    /**
     * 通过id获取用户(用户中心)
     * @param userId
     * @return
     */
    User getUser(int userId);

    /**
     * 更新头像
     * @param request
     * @return
     */
    String alertPhoto(HttpServletRequest request);

    /**
     * 更新用户信息
     * @param user 里面的信息可以不完整（方法里进行判空，不完整的信息认为是不作修改，从数据库中取出原来的信息给它补全）
     * @return
     */
    String alertMessage(User user);
}
