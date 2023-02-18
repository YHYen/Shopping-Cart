package com.idv.yen.service;

import com.idv.yen.domain.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * all required method related to the user
 * */
@Service
public interface UserService {
    Boolean register(User user);
    Boolean updateUserProfile(User user);
    Boolean deleteUser(Integer id);
    Boolean login(String username, String password);
    Boolean usernameExists(String username);
    User isLogin(HttpSession httpSession);
    User selectById(Integer id);
    List<User> selectAll();
}
