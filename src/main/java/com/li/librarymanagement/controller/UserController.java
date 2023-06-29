package com.li.librarymanagement.controller;


import com.li.librarymanagement.common.Result;
import com.li.librarymanagement.controller.dto.LoginDTO;
import com.li.librarymanagement.controller.dto.UserLoginDTO;
import com.li.librarymanagement.controller.request.LoginRequest;
import com.li.librarymanagement.controller.request.UserLoginRequest;
import com.li.librarymanagement.controller.request.UserPageRequest;
import com.li.librarymanagement.entity.User;
import com.li.librarymanagement.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    IUserService userService;

    @PostMapping("/login")
    public Result login(@RequestBody LoginRequest request) {
        UserLoginRequest userLoginRequest = new UserLoginRequest();
        userLoginRequest.setLoginname(request.getUsername());
        userLoginRequest.setPassword(request.getPassword());
        UserLoginDTO login = userService.login(userLoginRequest);
        return Result.success(login);
    }

    @PostMapping("/save")
    public Result save(@RequestBody User user) {
        userService.save(user);
        return Result.success();
    }

    @PostMapping("/account")
    public Result account(@RequestBody User user) {
        userService.handleAccount(user);
        return Result.success();
    }

    @PutMapping("/update")
    public Result update(@RequestBody User user) {
        userService.update(user);
        return Result.success();
    }

    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id) {
        userService.deleteById(id);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id) {
        User user = userService.getById(id);
        return Result.success(user);
    }

    @GetMapping("/list")
    public Result list() {
        List<User> users = userService.list();
        return Result.success(users);
    }

    @GetMapping("/page")
    public Result page(UserPageRequest userPageRequest) {
        return Result.success(userService.page(userPageRequest));
    }

}
