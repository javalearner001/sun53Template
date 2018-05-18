package com.sun.service;

import com.sun.pojo.User;

public interface RegisterService {
    Boolean insertInToUser(User user);

    User getUserByUid(String uid);

    Boolean checkUsername(String username);
}
