package com.li.librarymanagement.service;

import com.li.librarymanagement.entity.Role;
import com.li.librarymanagement.entity.User;

import java.util.List;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName IRoleService.java
 * @Description TODO
 * @createTime 2023年04月24日 02:38:00
 */
public interface IRoleService {
    List<Role> list();

    void save(Role role);

    Role getById(Integer id);

    void updateByid(Integer id, String path);

    void deleteById(Integer id);

    Role getPathById(Integer id);
}
