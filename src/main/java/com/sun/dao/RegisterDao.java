package com.sun.dao;

import com.sun.pojo.User;

public interface RegisterDao {
    Boolean insertInToUser(User user);

    User getUserByUid(String uid);
}
