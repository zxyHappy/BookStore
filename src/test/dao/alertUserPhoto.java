package dao;

import com.bluemsun.dao.UserDao;
import com.bluemsun.entity.User;
import org.junit.Test;

public class alertUserPhoto {
    @Test
    public void test(){
        UserDao userDao = new UserDao();
        System.out.println(userDao.alertPhoto("#",1));
    }
}
