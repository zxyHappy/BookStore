package dao;

import com.bluemsun.dao.BookDao;
import com.bluemsun.dao.UserDao;
import org.junit.Test;

public class CountNumberTest {
    @Test
    public void test(){
        BookDao bookDao = new BookDao();
        System.out.println(bookDao.countNumber());
    }
    @Test
    public void test2(){
        BookDao bookDao = new BookDao();
        System.out.println(bookDao.countNumberBySearch("111"));
    }
    @Test
    public void test3(){
        UserDao userDao = new UserDao();
        userDao.countNumberBySearch("4");
    }
}
