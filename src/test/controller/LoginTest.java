package controller;

import com.bluemsun.entity.User;
import com.bluemsun.service.impl.LoginServiceImpl;
import com.bluemsun.util.JSONUtil;
import org.junit.Test;

import java.util.Map;

public class LoginTest {
    @Test
    public void loginTest(){
        User user = new User();
        user.setUserName("www");
        user.setPassword("www");
        LoginServiceImpl loginService = new LoginServiceImpl();
        Map<String,Object> map = loginService.returnMessage(user);
        String json = JSONUtil.toJson(user);
        System.out.println(json);
        System.out.println(map.toString());
        String json2 = JSONUtil.mapTOJson(map);
        System.out.println(json2);
    }
}
