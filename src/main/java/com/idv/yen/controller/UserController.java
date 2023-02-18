package com.idv.yen.controller;

import com.idv.yen.controller.Utils.CheckCodeUtil;
import com.idv.yen.controller.Utils.Result;
import com.idv.yen.domain.User;
import com.idv.yen.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;



import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    public static final String SESSION_NAME = "userinfo";
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * user login
     * @param user
     * @param httpServletRequest
     * @return Result
     * */
    @PostMapping("/login")
    public Result login(@RequestBody User user, HttpServletRequest httpServletRequest) {
        if(userService.login(user.getUsername(), user.getPassword())) {
            httpServletRequest.getSession().setAttribute(SESSION_NAME, user);
            return new Result(true);
        }
        return new Result(false, "username or password error");
    }

    /**
     * determine whether the user is login
     * @param request
     * @return User
     * */
    @GetMapping("/isLogin")
    public User isLogin(HttpServletRequest request) {
        return userService.isLogin(request.getSession());
    }

    @PostMapping("/register")
    public Result register(@RequestBody User user) {
        String message = "";
        if (!user.getPassword().equals(user.getConfirmPassword())) {
            return new Result(false, "The two sets of passwords do not match");
        }

        if (userService.usernameExists(user.getUsername())) {
            return new Result(false, "This username already exists");
        }
        user.setType(0);
        return new Result(userService.register(user));
    }

    @GetMapping("/findUsername/{username}")
    public Result usernameExists(@PathVariable("username") String username) {
        if (userService.usernameExists(username)) {
            return new Result(true, "This username already exists");
        }
        return new Result(false, "That's a great username");
    }

    @PutMapping
    public Result updateProfile(@RequestBody User user) {
        return new Result(userService.updateUserProfile(user));
    }

    @DeleteMapping("/delete/{id}")
    public Result deleteUser(@PathVariable Integer id) {
        return new Result(userService.deleteUser(id));
    }

}
