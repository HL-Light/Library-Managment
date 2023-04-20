package com.li.librarymanagement.service;


import com.github.pagehelper.PageInfo;
import com.li.librarymanagement.controller.dto.LoginDTO;
import com.li.librarymanagement.controller.request.BaseRequest;
import com.li.librarymanagement.controller.request.LoginRequest;
import com.li.librarymanagement.controller.request.PasswordRequest;
import com.li.librarymanagement.entity.Admin;

import java.util.List;

public interface IAdminService {

    List<Admin> list();

    PageInfo<Admin> page(BaseRequest baseRequest);

    void save(Admin obj);

    Admin getById(Integer id);

    void update(Admin obj);

    void deleteById(Integer id);

    LoginDTO login(LoginRequest request);

    void changePass(PasswordRequest request);

}
