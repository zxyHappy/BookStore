package service;

import com.bluemsun.service.ShoppingService;
import com.bluemsun.service.impl.ShoppingServiceImpl;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class ShoppingServiceTest {
    @Test
    public void test(){
        List<Integer> list = new ArrayList<>();
        list.add(1);
        ShoppingService shoppingService = new ShoppingServiceImpl();
        System.out.println(shoppingService.addOrder("地球",list));
    }
}
