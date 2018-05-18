package com.sun.service.Impl;

import com.sun.dao.LoginDao;
import com.sun.pojo.User;
import com.sun.service.LoginService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName LoginServiceImpl
 * @Description TODO
 * @Author sunkai
 * @Date 2018/5/7 13:59
 * @Version 1.0
 **/
@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private LoginDao loginDao;

    @Override
    public Boolean getUserByUserName(String username) {
        User user=loginDao.getUserByUserName(username);
        if (StringUtils.isEmpty(user.getPassword())){
            //user.getPassword为空
            return false;
        }

        return null;
    }
}
