package com.sun.service;


import com.sun.pojo.User;

import java.sql.SQLException;

public interface LoginService {
    Boolean getUserByUserName(String username);

    public User login(String username, String password) throws SQLException;
}
