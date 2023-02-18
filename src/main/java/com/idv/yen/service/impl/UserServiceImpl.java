package com.idv.yen.service.impl;

import com.idv.yen.domain.User;
import com.idv.yen.mapper.UserMapper;
import com.idv.yen.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * use to implement the user service interface
 * */
@Service
public class UserServiceImpl implements UserService {

    final UserMapper userMapper;
    @Autowired
    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    /**
     *  add user
     * @param user User object containing user information
     * @return Boolean whether the user is added successfully
     * */
    @Override
    public Boolean save(User user) {
        return userMapper.insertUser(user) > 0;
    }

    /**
     * update user data by the user object
     * @param user User object containing user information
     * @return Boolean whether the user data is updated successfully
     * */
    @Override
    public Boolean update(User user) {
        return userMapper.updateById(user) > 0;
    }

    /**
     * delete user data by the user id
     * @param id the id of the user to delete
     * @return Boolean whether the user deleted successfully
     * */
    @Override
    public Boolean delete(Integer id) {
        return userMapper.deleteById(id) > 0;
    }

    /**
     * find user information by user id
     * @param id the id of the user to find
     * @return User object containing user information
     * */
    @Override
    public User selectById(Integer id) {
        return userMapper.selectById(id);
    }

    /**
     * find all user information
     * @return List<User> List containing all user information
     * */
    @Override
    public List<User> selectAll() {
        return userMapper.selectAll();
    }

    /**
     * check if the user login information matches
     * @return Boolean whether the user login successful
     * */
    @Override
    public Boolean loginSuccessful(String username, String password) {
        return userMapper.selectByUsernameAndPassword(username, password) != null;
    }

    /**
     * check if username already exits
     * @return Boolean whether the username already exists
     * */
    @Override
    public Boolean usernameExists(String username) {
        return userMapper.selectIdByUsername(username) == null;
    }


}
