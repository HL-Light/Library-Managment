package com.li.librarymanagement.service;


import com.github.pagehelper.PageInfo;
import com.li.librarymanagement.controller.request.BaseRequest;
import com.li.librarymanagement.entity.Book;
import com.li.librarymanagement.entity.Book_c;

import java.util.List;

public interface IBookService {
    
    List<Book> list();

    PageInfo<Book> page(BaseRequest baseRequest);

    PageInfo<Book_c> page_c(BaseRequest baseRequest, Integer book_id);

    void save(Book_c obj);

    Book getById(Integer id);

    void update(Book obj);

    void deleteById(Integer id);

    List<Book_c> selectByid(Integer book_id);

}
