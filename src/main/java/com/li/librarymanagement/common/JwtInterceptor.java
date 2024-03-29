package com.li.librarymanagement.common;

import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.li.librarymanagement.entity.Admin;
import com.li.librarymanagement.entity.User;
import com.li.librarymanagement.exception.ServiceException;
import com.li.librarymanagement.service.IAdminService;
import com.li.librarymanagement.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@Slf4j
public class JwtInterceptor implements HandlerInterceptor {

    private static final String ERROR_CODE_401 = "401";

    @Autowired
    private IAdminService adminService;

    @Autowired
    private IUserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        boolean res = HandlerInterceptor.super.preHandle(request,response,handler);
//        response.setHeader("Access-Control-Allow-Origin", "*");

        String token = request.getHeader("token");
        if (StrUtil.isBlank(token)) {
            token = request.getParameter("token");
        }

        // 执行认证
        if (StrUtil.isBlank(token)) {
            throw new ServiceException(ERROR_CODE_401, "无token，请重新登录");
        }

        String role = JWT.decode(token.toString()).getClaim("role").asString();
        String roles = JWT.decode(token.toString()).getClaim("roles").asString();
        if(!role.equals("3") && !role.equals("4")){
            // 获取 token 中的adminId
            String adminId;
            Admin admin;
            try {
                adminId = JWT.decode(token).getAudience().get(0);
                // 根据token中的userid查询数据库
                admin = adminService.getById(Integer.parseInt(adminId));
            } catch (Exception e) {
                String errMsg = "token验证失败，请重新登录";
                log.error(errMsg + ", token=" + token, e);
                throw new ServiceException(ERROR_CODE_401, errMsg);
            }
            if (admin == null) {
                throw new ServiceException(ERROR_CODE_401, "用户不存在，请重新登录");
            }

            try {
                // 用户密码加签验证 token
                JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(admin.getPassword())).build();
                jwtVerifier.verify(token); // 验证token
            } catch (JWTVerificationException e) {
                throw new ServiceException(ERROR_CODE_401, "token验证失败，请重新登录");
            }
            return true;
        }else {
            // 获取 token 中的adminId
            String userId;
            User user;
            try {
                userId = JWT.decode(token).getAudience().get(0);
                // 根据token中的userid查询数据库
                user = userService.getById(Integer.parseInt(userId));
            } catch (Exception e) {
                String errMsg = "token验证失败，请重新登录";
                log.error(errMsg + ", token=" + token, e);
                throw new ServiceException(ERROR_CODE_401, errMsg);
            }
            if (user == null) {
                throw new ServiceException(ERROR_CODE_401, "用户不存在，请重新登录");
            }

            try {
                // 用户密码加签验证 token
                JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(user.getPassword())).build();
                jwtVerifier.verify(token); // 验证token
            } catch (JWTVerificationException e) {
                throw new ServiceException(ERROR_CODE_401, "token验证失败，请重新登录");
            }
            return true;
        }

    }
}

