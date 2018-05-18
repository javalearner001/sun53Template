package com.sun.dao;

import com.sun.pojo.User;

public interface LoginDao {
    User getUserByUserName(String username);
}
