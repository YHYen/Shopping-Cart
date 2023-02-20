package com.idv.yen.service;

import com.idv.yen.domain.User;
import com.idv.yen.service.Utils.Result;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * all required method related to the user
 */
@Service
public interface UserService {
    Result register(User user);

    Result updateUserInfo(User user);

    Result deleteUser(Integer id);

    Result login(String username, String password);

    Result usernameExists(String username);

    Result isLogin(HttpSession httpSession);

    Result selectById(Integer id);

}
