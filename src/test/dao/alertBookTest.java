package dao;

import com.bluemsun.dao.BookDao;
import org.junit.Test;

public class alertBookTest {
    @Test
    public void test(){
        BookDao bookDao = new BookDao();
        String key = "Writer";
        String value = "eihei";
        if(bookDao.alertBook(key,value,9)) System.out.println(111);
        else System.out.println(222);
    }

//    @Test
//    public void test2(){
//        BookDao bookDao = new BookDao();
//        if(bookDao.alertBook("bookNumber",10,9)) System.out.println(333);
//        else System.out.println(444);
//    }
}
