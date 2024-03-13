package dao;

import com.bluemsun.dao.OrderDao;
import org.junit.Test;

public class confirmOrderTest {
    @Test
    public void test(){
        OrderDao orderDao = new OrderDao();
        System.out.println(orderDao.confirmOrder(8,4,61));
    }
}
