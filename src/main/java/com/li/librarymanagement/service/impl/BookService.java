package com.li.librarymanagement.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.li.librarymanagement.controller.request.BaseRequest;
import com.li.librarymanagement.entity.Book;
import com.li.librarymanagement.entity.Book_c;
import com.li.librarymanagement.exception.ServiceException;
import com.li.librarymanagement.mapper.BookMapper;
import com.li.librarymanagement.service.IBookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
public class BookService implements IBookService {

    @Resource
    BookMapper bookMapper;


    @Override
    public List<Book> list() {
        return bookMapper.list();
    }

    @Override
    public PageInfo<Book> page(BaseRequest baseRequest) {
        PageHelper.startPage(baseRequest.getPageNum(), baseRequest.getPageSize());
        return new PageInfo<>(bookMapper.listByCondition(baseRequest));
    }

    @Override
    public PageInfo<Book_c> page_c(BaseRequest baseRequest,Integer book_id) {
        PageHelper.startPage(baseRequest.getPageNum(),baseRequest.getPageSize());
        return new PageInfo<>(bookMapper.listByid(baseRequest,book_id));
    }

    @Override
    public void save(Book_c obj) {
        Book book = null;
        try {
            book = bookMapper.getByNo(obj.getBookNo());
        } catch (Exception e) {
            throw new ServiceException("数据插入错误", e);
        }
        if (book == null){
            //            obj.setCategory(category(obj.getCategories()));
            try {
                bookMapper.save(obj);
                book = bookMapper.getByNo(obj.getBookNo());
                bookMapper.bcsave(book.getId(),obj.getAdress());
            } catch (Exception e) {
                throw new ServiceException("数据插入错误", e);
            }
        }else {
            bookMapper.updateNumByNo(obj.getBookNo());
            bookMapper.bcsave(book.getId(),obj.getAdress());
        }


    }

    @Override
    public Book getById(Integer id) {
        return bookMapper.getById(id);
    }

    @Override
    public void update(Book obj) {
        try {
//            obj.setCategory(category(obj.getCategories()));
            obj.setUpdatetime(LocalDate.now());
            bookMapper.updateById(obj);
        } catch (Exception e) {
            throw new ServiceException("数据更新错误", e);
        }

    }

    @Override
    public void deleteById(Integer id) {
        bookMapper.deleteById(id);
    }

    @Override
    public List<Book_c> selectByid(Integer book_id) {
        return bookMapper.selectByid(book_id);
    }

    private String category(List<String> categories) {
        StringBuilder sb = new StringBuilder();
        if (CollUtil.isNotEmpty(categories)) {
            categories.forEach(v -> sb.append(v).append(" > "));
            return sb.substring(0, sb.lastIndexOf(" > "));
        }
        return sb.toString();
    }

}
