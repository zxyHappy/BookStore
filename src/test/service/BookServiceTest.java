package service;

import com.bluemsun.service.impl.BookServiceImpl;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class BookServiceTest {
    @Test
    public void addPhotosTest(){
        BookServiceImpl bookService = new BookServiceImpl();
        List<String> list = new ArrayList<>();
        list.add("#");
        list.add("*");
        System.out.println(bookService.addPhotosToBase(list,1));
    }
}
