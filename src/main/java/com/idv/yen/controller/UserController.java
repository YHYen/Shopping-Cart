package com.idv.yen.controller;

import com.idv.yen.domain.User;
import com.idv.yen.service.ImageService;
import com.idv.yen.service.UserService;
import com.idv.yen.service.Utils.Result;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/users")
public class UserController {
    public static final String SESSION_NAME = "userinfo";
    private final UserService userService;
    private final ImageService imageService;

    @Autowired
    public UserController(UserService userService, ImageService imageService) {
        this.userService = userService;
        this.imageService = imageService;
    }

    /**
     * user register
     *
     * @param user object containing user register information
     * @return Result whether the user register successful and error message
     */
    @PostMapping("/register")
    public Result register(@RequestBody User user) {
        return userService.register(user);
    }

    /**
     * check if username already exits
     *
     * @param username username to register
     * @return Result whether the username already exists and error message
     */
    @GetMapping("/findUsername/{username}")
    public Result usernameExists(@PathVariable("username") String username) {
        return userService.usernameExists(username);
    }

    /**
     * user login
     *
     * @param user               object containing user login information
     * @param httpServletRequest request object, used to process session
     * @return Result whether the user login successful and error message
     */
    @PostMapping("/login")
    public Result login(@RequestBody User user, HttpServletRequest httpServletRequest) {
        // check if the user login information matches
        Result result = userService.login(user.getUsername(), user.getPassword());
        if (result.getFlag()) {
            // if login successful, add user object to session data
            httpServletRequest.getSession().setAttribute(SESSION_NAME, result.getData());
        }
        return result;
    }

    /**
     * determine whether the user is login
     *
     * @param httpServletRequest session that records user information ("userinfo", user)
     * @return Result
     */
    @GetMapping("/isLogin")
    public Result isLogin(HttpServletRequest httpServletRequest) {
        return userService.isLogin(httpServletRequest.getSession());
    }

    /**
     * user log out
     *
     * @param httpServletRequest the request used to process request
     * @return Result successful message in Result object
     */
    @GetMapping("/logout")
    public Result logout(HttpServletRequest httpServletRequest) {
        // clear session data
        httpServletRequest.getSession().setAttribute(SESSION_NAME, null);
        httpServletRequest.getSession().invalidate();
        return new Result(true, "Logout successful");
    }


    @PostMapping("/sellerApplication")
    public Result sellerApplication(@RequestPart(value = "file") MultipartFile file, HttpSession httpSession) {
        // 1. obtain the user information to apply
        User user = (User) httpSession.getAttribute(SESSION_NAME);

        // 2. Upload photo of user ID to verify
        Result result = imageService.uploadSellerImage(file, user.getId());

        if (result.getFlag()) {
            // verification passed, change the user type to seller (type = 1)
            user.setType(1);
            return userService.updateUserInfo(user);
        }
        // verification failed, return photo verification result
        return result;

    }

    @GetMapping("/selectUserData/{id}")
    public Result selectUserData(@PathVariable Integer id) {
        return userService.selectById(id);
    }

    @DeleteMapping("/delete/{id}")
    public Result deleteUser(@PathVariable Integer id) {
        return userService.deleteUser(id);
    }

}
