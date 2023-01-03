package com.example.system.controller;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.system.common.Constants;
import com.example.system.common.Result;
import com.example.system.controller.dto.RoleDto;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.example.system.service.IRoleService;
import com.example.system.entity.Role;

import org.springframework.web.bind.annotation.RestController;

import static com.example.system.common.Constants.CODE_400;
import static com.example.system.common.Constants.CODE_600;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 青哥哥
 * @since 2022-10-17
 */
@RestController
@RequestMapping("/role")
public class RoleController {

    @Resource
    private IRoleService roleService;

    @PostMapping("/login")
    public Result login(@RequestBody RoleDto roleDto) {
        String roleId = roleDto.getRoleId();
        String password = roleDto.getPassword();
        if (StrUtil.isBlank(roleId) || StrUtil.isBlank(password)) {
            return Result.error(CODE_400, "参数错误");
        }
        RoleDto login = roleService.login(roleDto);
        if (login == null) {
            return Result.error("error", "错误");
        } else {
            return Result.success(login);
        }
    }

    @PostMapping("/loginEmail")
    public Result loginEmail(@RequestBody RoleDto roleDto) throws Exception {
        String email = roleDto.getEmail();
        String code = roleDto.getCode();
        if (StrUtil.isBlank(email) || StrUtil.isBlank(code)) {
            return Result.error(CODE_400, "参数错误");
        }
        try{
            RoleDto dto = roleService.loginEmail(roleDto);
            return Result.success(dto);
        }catch (Exception e){
            return Result.error(CODE_400,"验证码错误");
        }


    }

    @PostMapping("/register")
    public Result register(@RequestBody RoleDto roleDto) throws Exception {
        String RoleId = roleDto.getRoleId();
        String password = roleDto.getPassword();
        if (StrUtil.isBlank(RoleId) || StrUtil.isBlank(password)) {
            return Result.error(CODE_400, "参数错误");
        }
        try {
            Role role = roleService.register(roleDto);
            return Result.success(role);
        }
        catch(Exception e){
            return Result.error(CODE_600,"用户已存在");
        }


    }

    // 新增或者更新
    @PostMapping
    public boolean save(@RequestBody Role role) {
        return roleService.saveOrUpdate(role);
    }

    //修改密码
    @PostMapping("/changePassword")
    public Result changePassword(@RequestBody RoleDto roleDto) {
        String email = roleDto.getEmail();
        String code = roleDto.getCode();
        if (StrUtil.isBlank(email) || StrUtil.isBlank(code)) {
            return Result.error(CODE_400, "参数错误");
        }
        try{
            roleService.changePassword(roleDto);
            return Result.success();
        }
        catch (Exception e) {
            return Result.error(CODE_400,"验证码错误");
        }
    }

    @GetMapping("/email")
    public Result sendEmailCode(@RequestParam String email){
        if(StrUtil.isBlank(email)){
            return Result.error(CODE_400,"参数错误");
        }
        roleService.sendEmailCode(email);
        return Result.success("发送成功");
    }

    @DeleteMapping("/{id}")
    public Boolean delete(@PathVariable Integer id) {
        return roleService.removeById(id);
    }

    @PostMapping("/del/batch")
    public boolean deleteBatch(@RequestBody List<Integer> ids) {
        return roleService.removeByIds(ids);
    }

    @GetMapping
    public List<Role> findAll() {
        return roleService.list();
    }

    @GetMapping("/{id}")
    public Role findOne(@PathVariable Integer id) {
        return roleService.getById(id);
    }

    @GetMapping("/page")
    public Page<Role> findPage(@RequestParam Integer pageNum,
                               @RequestParam Integer pageSize) {
        QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        return roleService.page(new Page<>(pageNum, pageSize), queryWrapper);
    }

}

