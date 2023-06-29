package com.li.librarymanagement.service;


import com.github.pagehelper.PageInfo;
import com.li.librarymanagement.controller.dto.LoginDTO;
import com.li.librarymanagement.controller.dto.UserLoginDTO;
import com.li.librarymanagement.controller.request.BaseRequest;
import com.li.librarymanagement.controller.request.LoginRequest;
import com.li.librarymanagement.controller.request.UserLoginRequest;
import com.li.librarymanagement.entity.User;

import java.util.List;

public interface IUserService {

    List<User> list();

    PageInfo<User> page(BaseRequest baseRequest);

    void save(User user);

    User getById(Integer id);

    void update(User user);

    void deleteById(Integer id);

    void handleAccount(User user);

    UserLoginDTO login(UserLoginRequest request);

}
