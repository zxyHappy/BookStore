package dao;

import com.bluemsun.dao.UserDao;
import com.bluemsun.entity.User;
import org.junit.Test;

public class getUserTest {
    @Test
    public void getUserTest(){
        String userName = "www";
        UserDao userDao = new UserDao();
        User user =  userDao.getUserByName(userName);
        System.out.println(user.getPassword());
    }


    @Test
    public void search(){
        UserDao userDao = new UserDao();
        userDao.getUserBySearch("4",0);
    }
}
