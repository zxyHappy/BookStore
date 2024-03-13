package com.bluemsun.util;

import com.bluemsun.entity.Book;
import com.bluemsun.entity.Comment;
import com.bluemsun.entity.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

public class JSONUtil {

//    public static User jsonToUser(String json){
//        Gson gson = new Gson();
//        User user = gson.fromJson(json,User.class);
//        return user;
//    }

    public static String readJSON(HttpServletRequest request){
        StringBuffer json=new StringBuffer();
        String lineString=null;
        try {
//            BufferedReader reader=request.getReader();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(request.getInputStream(), StandardCharsets.UTF_8));
            while ((lineString=bufferedReader.readLine())!=null) {
                json.append(lineString);
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return json.toString();
    }
//
//    public static String userTOJson(User user){
//        if(user != null){
//            Gson gson = new Gson();
//            String json = gson.toJson(user);
//            return json;
//        }
//        return null;
//    }


    public static String mapTOJson(Map<String,Object> map){
        Gson gson = new Gson();
        String json = gson.toJson(map);
        return json;
    }

//    public static Book jsonToBook(String json){
//        Gson gson = new Gson();
//        Book book = gson.fromJson(json,Book.class);
//        return book;
//    }


    public static Map<String,Object> jsonToMap(String json){
        Gson gson = new Gson();
        Type type = new TypeToken<Map<String,Object>>(){}.getType();
        Map<String,Object> map = gson.fromJson(json,type);
        return map;
    }


//    public static List<String> jsonToList(String json){
//        Gson gson = new Gson();
//        Type type = new TypeToken<List<String>>(){}.getType();
//        List<String> list = null;
//        if(gson.fromJson(json,type)!=null) list = gson.fromJson(json,type);
//        return list;
//    }

//    public static Comment jsonToComment(String json){
//        Gson gson = new Gson();
//        Comment comment = gson.fromJson(json,Comment.class);
//        return comment;
//    }

//    public static String commentToJson(Comment comment){
//        if(comment != null){
//            Gson gson = new Gson();
//            return gson.toJson(comment);
//        }else return null;
//    }

    //其他类型转成json
    public static <T> String toJson(T t){
        if(t != null){
            Gson gson = new Gson();
            return gson.toJson(t);
        }return null;
    }

    //json转成其他类
    public static <T> T jsonTo(String json,Class<T> tClass){
        Gson gson = new Gson();
        return gson.fromJson(json,(Type) tClass);
    }



}
