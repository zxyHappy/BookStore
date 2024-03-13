package entity;

import com.bluemsun.entity.Book;
import com.bluemsun.entity.User;
import com.bluemsun.util.JSONUtil;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class JSONUtilTest {
    @Test
    public void toJsonTest(){
        Map<String,Object> map = new HashMap<>();
        map.put("1",1);
        map.put("222","222");
        User user = new User();
        user.setUserId(1);
        user.setPassword("156");
        Book book = new Book();
        book.setBookId(222);
        book.setBookStatus(0);
        book.setBookName("欸嘿");
        System.out.println(JSONUtil.toJson(map));
        System.out.println(JSONUtil.toJson(user));
        System.out.println(JSONUtil.toJson(book));

        String json = "{\n" +
                "            \"userId\": 1,\n" +
                "            \"nickName\": \"123456\",\n" +
                "            \"userName\": \"123456\",\n" +
                "            \"Password\": \"123456\",\n" +
                "            \"userPhoto\": \"http://eihei.natapp1.cc:80/Userhead/6c3c1fa0-cb86-4422-a0a3-2067ea89e644.png\",\n" +
                "            \"status\": 1,\n" +
                "            \"userStatus\": 1,\n" +
                "            \"Telephone\": \"123456\"\n" +
                "        }";

        System.out.println(JSONUtil.jsonTo(json,User.class).getUserId());

        String json2 = "{\n" +
                "        \"bookId\": 178,\n" +
                "        \"bookName\": \"jia\",\n" +
                "        \"Writer\": \"123\",\n" +
                "        \"Synopsis\": \"qwd\",\n" +
                "        \"bookStatus\": 1,\n" +
                "        \"bookType\": \"文学历史科学\",\n" +
                "        \"bookNumber\": 103,\n" +
                "        \"Price\": 123,\n" +
                "        \"bookPhoto\": \"http://eihei.natapp1.cc:80/Bookphoto/7739d45f-eb59-42c0-8c80-9a1dc043e414.png\",\n" +
                "        \"Press\": \"123\",\n" +
                "        \"likeNumber\": 0\n" +
                "    }";
        System.out.println(JSONUtil.jsonTo(json2,Book.class).getBookId());

    }
}
