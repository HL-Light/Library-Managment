package com.li.librarymanagement.service;


import com.github.pagehelper.PageInfo;
import com.li.librarymanagement.controller.request.BaseRequest;
import com.li.librarymanagement.entity.Book;

import java.util.List;

public interface IBookService {
    
    List<Book> list();

    PageInfo<Book> page(BaseRequest baseRequest);

    void save(Book obj);

    Book getById(Integer id);

    void update(Book obj);

    void deleteById(Integer id);

}
