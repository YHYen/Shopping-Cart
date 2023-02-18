package com.idv.yen.controller;

import com.idv.yen.controller.Utils.Result;
import com.idv.yen.domain.User;
import com.idv.yen.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Users")
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("{username}/{password}")
    public Result loginSuccessful(@PathVariable String username, @PathVariable String password) {
        return new Result(userService.loginSuccessful(username, password));
    }

    @GetMapping("{username}")
    public Result usernameExists(@PathVariable String username) {
        return new Result(userService.usernameExists(username));
    }

    @PostMapping
    public Result save(@RequestBody User user) {
        return new Result(userService.save(user));
    }

    @GetMapping("{id}")
    public Result selectById(@PathVariable Integer id) {
        return new Result(true, userService.selectById(id));
    }

}
