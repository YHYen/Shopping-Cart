package com.idv.yen.service.impl;

import com.idv.yen.controller.UserController;
import com.idv.yen.domain.User;
import com.idv.yen.mapper.UserMapper;
import com.idv.yen.service.UserService;
import com.idv.yen.service.Utils.Result;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * use to implement the UserService interface
 */
@Service
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }


    /**
     * user register: add user to database
     *
     * @param user User object containing user information
     * @return Result whether the user register successful and process message
     */
    @Override
    public Result register(User user) {

        if (!user.getPassword().equals(user.getConfirmPassword())) {
            return new Result(false, "The two sets of passwords do not match");
        }

        if (usernameExists(user.getUsername()).getFlag()) {
            // if user exists
            return new Result(false, "This username already exists");
        }

        user.setType(0);

        if (userMapper.insertUser(user) > 0) {
            return new Result(true, "registration successfully");
        } else {
            return new Result(false, "registration failed");
        }
    }

    /**
     * check if username already exits
     *
     * @param username username to register
     * @return Result whether the username already exists and process message
     */
    @Override
    public Result usernameExists(String username) {
        if (userMapper.selectByUsername(username) == null) {
            // username does not exists
            return new Result(false, "That's a great username");
        }
        return new Result(true, "This username already exists");
    }

    /**
     * check if the user login information matches
     *
     * @param username username to login
     * @param password user password to login
     * @return Result whether the user login successful and process message
     */
    @Override
    public Result login(String username, String password) {
        // check if the user login information matches
        User user = userMapper.selectByUsernameAndPassword(username, password);
        if (user != null) {
            // if matches, return Result object containing true, user data and successful message
            return new Result(true, user, "Login successful");
        }
        // if not matches, return Result object containing false and successful message
        return new Result(false, "username or password error");
    }

    /**
     * check if username already login
     *
     * @param httpSession session that records user information ("userinfo", user)
     * @return Result Whether the process is successful or not,  the user object and process message
     */
    @Override
    public Result isLogin(HttpSession httpSession) {
        // 1. get userinfo from session
        User user = (User) httpSession.getAttribute(UserController.SESSION_NAME);
        if (user == null) {
            // not logged in yet, return false, null data and error message
            return new Result(false, null, "You have not logged in, please log in first");
        }
        // already log in return true and user object
        return new Result(true, user);
    }

    /**
     * update user data by the user object
     *
     * @param user User object containing user information
     * @return Result whether the user data is updated successfully and process message
     */
    @Override
    public Result updateUserInfo(User user) {
        if (userMapper.updateById(user) > 0) {
            return new Result(true, "Data updated successfully");
        }

        return new Result(false, "Data repair failed");
    }

    /**
     * find user information by user id
     *
     * @param id the id of the user to find
     * @return Result  Whether the process is successful or not and object containing user information
     */
    @Override
    public Result selectById(Integer id) {
        if (userMapper.selectById(id) == null) {
            return new Result(false, null);
        }

        return new Result(true, userMapper.selectById(id));
    }

    /**
     * delete user data by the user id
     *
     * @param id the id of the user to delete
     * @return Boolean whether the user deleted successfully
     */
    @Override
    public Result deleteUser(Integer id) {
        if (userMapper.deleteById(id) > 0) {
            return new Result(true, "Successfully deleted");
        }
        return new Result(false, "Failed to delete");
    }

}
