package com.bluemsun.service;

import com.bluemsun.entity.User;

import java.util.Map;

public interface RegisterService {
    /**
     *检查该账号是否被注册过
     * @param userName
     * @return
     */
    boolean CheckRepeat(String userName);

    /**
     *实现注册
     * @param user
     * @return 注册成功/注册失败/账户已存在
     */
    String RegisterStatus(User user);

    /**
     * 返回注册信息（上面那三条），转成map
     * @param user
     * @return
     */
    Map<String,Object> returnMessage(User user);
}
