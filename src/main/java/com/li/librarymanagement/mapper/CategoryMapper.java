package com.li.librarymanagement.mapper;


import com.li.librarymanagement.controller.request.BaseRequest;
import com.li.librarymanagement.entity.Category;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CategoryMapper {

    List<Category> list();

    List<Category> listByCondition(BaseRequest baseRequest);

    void save(Category obj);

    Category getById(Integer id);

    void updateById(Category user);

    void deleteById(Integer id);

    Category selectByPid(Integer id);

}
