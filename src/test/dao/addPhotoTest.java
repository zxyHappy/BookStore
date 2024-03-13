package dao;

import com.bluemsun.dao.BookDao;
import org.junit.Test;

public class addPhotoTest {
    @Test
    public void test(){
        BookDao bookDao = new BookDao();
        if(bookDao.addPhoto("#",2)) System.out.println(1);
        else System.out.println(0);
    }
}
