package com.li.librarymanagement.mapper;

import com.li.librarymanagement.entity.Role;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName RoleMapper.java
 * @Description TODO
 * @createTime 2023年04月24日 02:42:00
 */
@Mapper
public interface RoleMapper {

    List<Role> list();

    void save(Role role);

    Role getById(Integer id);

    void updateByid(Integer id, String rolepath);

    void deleteById(Integer id);

    Role getPathById(Integer id);
}
