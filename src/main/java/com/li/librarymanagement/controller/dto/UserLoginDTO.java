package com.li.librarymanagement.controller.dto;

import lombok.Data;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName UserLoginDTO.java
 * @Description TODO
 * @createTime 2023年04月24日 19:03:00
 */
@Data
public class UserLoginDTO {
    private Integer id;
    private String loginame;
    private String phone;
    private String email;
    private String token;
}
