package dao;

import com.bluemsun.dao.UserDao;
import com.bluemsun.entity.User;
import org.junit.Test;

public class getIdByNameTest {
    @Test
    public void test(){
        String name = "www";
        UserDao userDao = new UserDao();

        int id = userDao.getIdByName(name);
        System.out.println(id);
    }
}
