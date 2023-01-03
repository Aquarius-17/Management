package com.example.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
  @TableName("role")
@ApiModel(value = "Role对象", description = "")
public class Role implements Serializable {

    private static final long serialVersionUID = 1L;

      @ApiModelProperty("主键")
        private Integer id;

      @ApiModelProperty("身份")
      private String role;//0,代表管理员，1代表老师，2代表学生

      @ApiModelProperty("用户名")
      private String name;

      @ApiModelProperty("密码")
      private String password;

      @ApiModelProperty("个人邮箱")
      private String email;

      @ApiModelProperty("账号")
      private String roleId;


}
