package com.bluemsun.service.impl;

import com.bluemsun.dao.UserDao;
import com.bluemsun.entity.User;
import com.bluemsun.service.UserCenterService;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

@MultipartConfig(maxFileSize = 5*1024*1024,fileSizeThreshold = 6*1024*1024)
public class UserCenterServiceImpl implements UserCenterService {
    @Override
    public User getUser(int userId) {
        UserDao userDao = new UserDao();
        return userDao.getUserById(userId);
    }

    @Override
    public String alertPhoto(HttpServletRequest request) {
        Part part = null;
        try {
            part = request.getPart("userPhoto");
        } catch (IOException | ServletException e) {
            e.printStackTrace();
        }
//        if(part == null) System.out.println("111");
        String disposition = part.getSubmittedFileName();
        String suffix = disposition.substring(disposition.lastIndexOf(".")); //获取文件后缀名
        String fileName = UUID.randomUUID()+suffix;

        String server_path =request.getServletContext().getRealPath("Userhead"); // 目录路径
        File fileDisk = new File(server_path);
        if(!fileDisk.exists()){
            fileDisk.mkdir();
        }

        String file_part =server_path+'/'+fileName;
        try {
            part.write(file_part); //写入文件
        } catch (IOException e) {
            e.printStackTrace();
        }

        String projectServerPath = request.getScheme()+"://"+request.getServerName()+":"
                +request.getServerPort()+request.getContextPath()+"/Userhead/"+fileName;  //前端访问用的url

        UserDao userDao = new UserDao();
        if(userDao.alertPhoto(projectServerPath,(int)request.getSession().getAttribute("login_status"))) return projectServerPath;
        else return "http://eihei.natapp1.cc/Userhead/m.png";
    }

    @Override
    public String alertMessage(User user) {
        UserDao userDao = new UserDao();
        int userId = user.getUserId();

        String nickName = userDao.getUserById(userId).getNickName();
        String Password = userDao.getUserById(userId).getPassword();
        String Telephone = userDao.getUserById(userId).getTelephone();

        if(user.getNickName() != null && !"".equals(user.getNickName())) nickName = user.getNickName();
        if(user.getPassword() != null && !"".equals(user.getPassword())) Password = user.getPassword();
        if(user.getTelephone() != null && !"".equals(user.getTelephone())) Telephone = user.getTelephone();

        if(userDao.alertMessage(nickName,Password,Telephone,userId)) return "修改成功";
        else return "修改失败";
    }
}
