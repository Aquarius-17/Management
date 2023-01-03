package com.example.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.RandomUtil;
import com.example.system.controller.dto.RoleDto;
import com.example.system.entity.Role;
import com.example.system.mapper.RoleMapper;
import com.example.system.service.IRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.system.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 青哥哥
 * @since 2022-10-17
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {
    @Autowired
    RoleMapper roleMapper;

    @Value("${spring.mail.username}")
    private String from;
    @Autowired
    JavaMailSender javaMailSender;
    @Override
    public RoleDto login(RoleDto roleDto) {
        String roleId=roleDto.getRoleId();
        String password=roleDto.getPassword();
        try{
            Role one = roleMapper.selectRole(roleId,password);
            if(one!=null){
                BeanUtil.copyProperties(one,roleDto,true);
                String token = TokenUtils.genToken(one);
                roleDto.setToken(token);
                return roleDto;
            }else{
                return null;
            }
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public RoleDto loginEmail(RoleDto roleDto) throws Exception {
        String email=roleDto.getEmail();
        String code = roleDto.getCode();
        String s=roleMapper.findCode(email);
        if(s!=code){
            throw new Exception("验证码错误");
        }
        else{
            try{
                Role role = roleMapper.selectRoleViaEmail(email);
                BeanUtil.copyProperties(role,roleDto,true);
                String token = TokenUtils.genToken(role);
                roleDto.setToken(token);
                return roleDto;
            }catch (Exception e){
                return null;
            }
        }
    }

    @Override
    public Role register(RoleDto roleDto) throws Exception {
        String RoleId=roleDto.getRoleId();
        Role one=roleMapper.selectRoles(RoleId);
        if(one==null){
            one = new Role();
            one.setRoleId(RoleId);
            one.setPassword(roleDto.getPassword());
            one.setRole(roleDto.getRole());
            one.setEmail(roleDto.getEmail());
            save(one);
        }
        else{
            throw new Exception("用户已存在");
        }
        return one;
    }

    @Override
    public void sendEmailCode(String email){
        SimpleMailMessage simpleMailMessage=new SimpleMailMessage();

        String code = RandomUtil.randomNumbers(6);
        simpleMailMessage.setText("本次登录验证码是："+code);
        simpleMailMessage.setSubject("主题");
        simpleMailMessage.setTo(email);
        simpleMailMessage.setFrom(from);
        javaMailSender.send(simpleMailMessage);
        String now=roleMapper.existOrNot(email);
        if(now!=null){
            roleMapper.updateCode(code,email);
        }
        else{
            roleMapper.saveCode(code,email);
        }
    }

    @Override
    public void changePassword(RoleDto roleDto) throws Exception {
        String email = roleDto.getEmail();
        String code = roleDto.getCode();
        String password = roleDto.getPassword();
        String str=roleMapper.findCode(email);
        if(str!=code){
            throw new Exception("验证码错误");
        }
        else{
            Role role = roleMapper.selectRoleViaEmail(email);
            if(role==null){
                throw new Exception("用户不存在，请注册");
            }
            else{
                roleMapper.updatePassword(password,email);
            }
        }

    }
}
