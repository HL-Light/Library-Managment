package com.li.librarymanagement.service.impl;

import com.li.librarymanagement.entity.Role;
import com.li.librarymanagement.mapper.RoleMapper;
import com.li.librarymanagement.service.IRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName RoleService.java
 * @Description TODO
 * @createTime 2023年04月24日 02:40:00
 */

@Slf4j
@Service
public class RoleService implements IRoleService {

    @Autowired
    RoleMapper roleMapper;

    @Override
    public List<Role> list() {
        return roleMapper.list();
    }

    @Override
    public void save(Role role) {

    }

    @Override
    public Role getById(Integer id) {
        return null;
    }

    @Override
    public void updateByid(Integer id, String path) {
        roleMapper.updateByid(id, path);

    }

    @Override
    public void deleteById(Integer id) {

    }

    @Override
    public Role getPathById(Integer id) {
        return roleMapper.getPathById(id);
    }
}
