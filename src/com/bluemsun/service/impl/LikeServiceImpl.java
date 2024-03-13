package com.bluemsun.service.impl;

import com.bluemsun.dao.BookDao;
import com.bluemsun.dao.UserDao;
import com.bluemsun.service.LikeService;

public class LikeServiceImpl implements LikeService {
    @Override
    public int checkLike(int userId, int bookId) {
        UserDao userDao = new UserDao();
        return userDao.checkLike(userId,bookId);
    }

    @Override
    public int alertLike(int userId, int bookId) {
        UserDao userDao = new UserDao();
        BookDao bookDao = new BookDao();
        if(userDao.checkLike(userId,bookId) == 1){
            userDao.delLike(userId,bookId);
            bookDao.delLikeNumber(bookId);
            return 0;
        }else{
            userDao.addLike(userId,bookId);
            bookDao.addLikeNumber(bookId);
            return 1;
        }
    }
}
