package com.example.system.mapper;

import com.example.system.entity.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 青哥哥
 * @since 2022-10-17
 */
@Mapper
public interface RoleMapper extends BaseMapper<Role> {

    @Select("select * from role where roleId=#{roleId} and password=#{password}")
    Role selectRole(String roleId,String password);
    @Select("select * from role where roleId=#{roleId}")
    Role selectRoles(String roleId);

    @Insert("insert into email (code,email) values (#{code},#{email})")
    void saveCode(String code,String email);

    @Update("update email set code=#{code} where email=#{email}")
    void updateCode(String code,String email);

    @Select("select email from email where email=#{email}")
    String existOrNot(String email);

    @Select("select code from email where email=#{email}")
    String findCode(String email);

    @Select("select * from role where email=#{email}")
    Role selectRoleViaEmail(String email);

    @Update("update role set password=#{password} where email=#{email}")
    void updatePassword(String password,String email);


}
