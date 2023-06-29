package com.li.librarymanagement.mapper;


import com.li.librarymanagement.controller.request.BaseRequest;
import com.li.librarymanagement.entity.Book;
import com.li.librarymanagement.entity.Book_c;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BookMapper {

    List<Book> list();

    List<Book> listByCondition(BaseRequest baseRequest);

    void save(Book_c obj);

    Book getById(Integer id);

    void updateById(Book user);

    void updateById(Book_c user);

    void deleteById(Integer id);

    Book getByNo(String bookNo);

    void updateNumByNo(String bookNo);

    List<Book_c> listByid(BaseRequest baseRequest, Integer book_id);

    void bcsave(Integer book_id, String adress);

    List<Book_c> selectByid(Integer book_id);
}
