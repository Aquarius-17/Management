package com.example.system.service;

import com.example.system.controller.dto.RoleDto;
import com.example.system.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 青哥哥
 * @since 2022-10-17
 */
public interface IRoleService extends IService<Role> {

    RoleDto login(RoleDto roleDto);
    public RoleDto loginEmail(RoleDto roleDto) throws Exception;
    public Role register(RoleDto roleDto) throws Exception;
    public void sendEmailCode(String email);
    void changePassword(RoleDto roleDto) throws Exception;
}
