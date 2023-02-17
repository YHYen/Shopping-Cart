package com.idv.yen.service;

import com.idv.yen.domain.User;

import java.util.List;

public interface UserService {
    Boolean save(User user);
    Boolean update(User user);
    Boolean delete(Integer id);
    User selectById(Integer id);
    List<User> selectAll();
}
