package controller;

import com.bluemsun.entity.User;
import com.bluemsun.service.impl.LoginServiceImpl;
import com.bluemsun.service.impl.RegisterServiceImpl;
import com.bluemsun.util.JSONUtil;
import org.junit.Test;

import java.io.IOException;
import java.util.Map;

public class RegisterTest {
    @Test
    public void doRegisterTest() {
        RegisterServiceImpl registerService = new RegisterServiceImpl();
        User user = new User();
        user.setNickName("www");
        user.setUserName("123456");
        user.setPassword("www");
        user.setTelephone("1111");
        user.setUserPhoto("#");
        Map<String,Object> map = registerService.returnMessage(user);
        String json = JSONUtil.toJson(user);
        System.out.println(json);
        System.out.println(map.toString());
    }
}
