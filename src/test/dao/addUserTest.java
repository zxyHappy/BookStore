package dao;

import com.bluemsun.dao.UserDao;
import com.bluemsun.entity.User;
import org.junit.Test;

public class addUserTest {
    @Test
    public void test(){
        User user = new User();
        user.setUserName("aaa");
        user.setNickName("aaa");
        user.setPassword("aaa");
        user.setTelephone("111111");
        UserDao userDao = new UserDao();
        userDao.addUser(user);
    }

}
