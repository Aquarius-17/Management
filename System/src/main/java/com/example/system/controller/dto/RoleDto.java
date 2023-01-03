package com.example.system.controller.dto;

import lombok.Data;

//接受前端登录请求参数
@Data
public class RoleDto {
    String roleId;
    String password;
    String token;
    String email;
    String code;
    String role;
    String oldPassword;
}
