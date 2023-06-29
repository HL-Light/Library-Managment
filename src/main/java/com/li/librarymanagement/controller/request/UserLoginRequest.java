package com.li.librarymanagement.controller.request;

import lombok.Data;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName UserLoginRequest.java
 * @Description TODO
 * @createTime 2023年04月24日 19:38:00
 */
@Data
public class UserLoginRequest {
    private String loginname;
    private String password;
}
