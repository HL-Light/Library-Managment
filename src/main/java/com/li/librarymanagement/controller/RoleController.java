package com.li.librarymanagement.controller;

import com.li.librarymanagement.common.Result;
import com.li.librarymanagement.entity.Admin;
import com.li.librarymanagement.entity.Role;
import com.li.librarymanagement.entity.User;
import com.li.librarymanagement.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName RoleController.java
 * @Description TODO
 * @createTime 2023年04月24日 02:35:00
 */

@CrossOrigin
@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    IRoleService roleService;

    @PostMapping("/save")
    public Result save(@RequestBody Role role) {
        roleService.save(role);
        return Result.success();
    }

    @GetMapping("/list")
    public Result list() {
        List<Role> roles = roleService.list();
        return Result.success(roles);
    }

    @GetMapping("/getPath/{id}")
    public Result getPathById(@PathVariable Integer id){
        Role rolepath = roleService.getPathById(id);
        return Result.success(rolepath);
    }

    @PutMapping("/update")
    public Result updateById(@RequestBody Role obj){
        Integer id = obj.getId();
        String path = obj.getRolepath();
        roleService.updateByid(id,path);
        return Result.success();
    }



}
