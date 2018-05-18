package com.sun.service.Impl;

import com.sun.dao.LoginDao;
import com.sun.dao.RegisterDao;
import com.sun.pojo.User;
import com.sun.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * @ClassName RegisterServiceImpl
 * @Description TODO
 * @Author sunkai
 * @Date 2018/5/5 14:41
 * @Version 1.0
 **/
@Service
public class RegisterServiceImpl implements RegisterService {
    @Autowired
    private RegisterDao registerDao;

    @Autowired
    private LoginDao loginDao;

    @Override
    public Boolean insertInToUser(User user) {
        Boolean flag=registerDao.insertInToUser(user);
        return flag;
    }

    @Override
    public User getUserByUid(String uid) {
        User user=registerDao.getUserByUid(uid);
        System.out.println(user.getUsername());
        return user;
    }

    @Override
    public Boolean checkUsername(String username) {
        User user=loginDao.getUserByUserName(username);
        Boolean flag=true;
        if (StringUtils.isEmpty(user)){
            //用户名不存在
            flag=true;
        }else if (user.getUsername().equals(username)){
            //用户名已经存在
            flag=false;
        }
       return flag;
    }
}
