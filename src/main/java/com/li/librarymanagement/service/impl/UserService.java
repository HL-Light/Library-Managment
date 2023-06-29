package com.li.librarymanagement.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.li.librarymanagement.controller.dto.LoginDTO;
import com.li.librarymanagement.controller.dto.UserLoginDTO;
import com.li.librarymanagement.controller.request.BaseRequest;
import com.li.librarymanagement.controller.request.LoginRequest;
import com.li.librarymanagement.controller.request.UserLoginRequest;
import com.li.librarymanagement.entity.Admin;
import com.li.librarymanagement.entity.User;
import com.li.librarymanagement.exception.ServiceException;
import com.li.librarymanagement.mapper.UserMapper;
import com.li.librarymanagement.service.IUserService;
import com.li.librarymanagement.utils.MD5Utils;
import com.li.librarymanagement.utils.TokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.nio.cs.US_ASCII;

import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class UserService implements IUserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public List<User> list() {
        return userMapper.list();
    }

    @Override
    public PageInfo<User> page(BaseRequest baseRequest) {
        PageHelper.startPage(baseRequest.getPageNum(), baseRequest.getPageSize());
        List<User> users = userMapper.listByCondition(baseRequest);
        return new PageInfo<>(users);
    }

    @Override
    public void save(User user) {
        if (user.getPassword()!=null){
            try {
                user.setPassword(MD5Utils.getMD5Str(user.getPassword()));
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }
        Date date = new Date();
        // 当做卡号来处理
        user.setUsername(DateUtil.format(date, "yyyyMMdd") + Math.abs(IdUtil.fastSimpleUUID().hashCode()));
        userMapper.save(user);
    }

    @Override
    public User getById(Integer id) {
        return userMapper.getById(id);
    }

    @Override
    public void update(User user) {
        if (user.getPassword()!=null){
            try {
                user.setPassword(MD5Utils.getMD5Str(user.getPassword()));
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }
        user.setUpdatetime(new Date());
        userMapper.updateById(user);
    }

    @Override
    public void deleteById(Integer id) {
        userMapper.deleteById(id);
    }

    @Override
    public void handleAccount(User user) {
        Integer score = user.getScore();
        if (score == null) {
            return;
        }
        Integer id = user.getId();
        User dbUser = userMapper.getById(id);
        dbUser.setAccount(dbUser.getAccount() + score);
        userMapper.updateById(dbUser);
    }

    @Override
    public UserLoginDTO login(UserLoginRequest request) {
        User user = null;
        try {
            user = userMapper.getByLoginname(request.getLoginname());
        } catch (Exception e) {
            log.error("根据用户名{} 查询出错", request.getLoginname());
            throw new ServiceException("用户名错误");
        }
        if (user == null) {
            throw new ServiceException("用户名或密码错误");
        }
        // 判断密码是否合法
        String securePass = null;
        try {
            securePass = MD5Utils.getMD5Str(request.getPassword());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        if (!securePass.equals(user.getPassword())) {
            throw new ServiceException("用户名或密码错误");
        }
        if (!user.isStatus()) {
            throw new ServiceException("当前用户处于禁用状态，请联系管理员");
        }
        UserLoginDTO loginDTO = new UserLoginDTO();
        BeanUtils.copyProperties(user, loginDTO);

        // 生成token
        String token = TokenUtils.genToken(String.valueOf(user.getId()), user.getPassword(), user);
        loginDTO.setToken(token);
        return loginDTO;
    }

}
