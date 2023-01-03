package com.example.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@TableName("sys_student")
@ApiModel(value = "Student对象", description = "")
public class Student implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    private Integer id;

    @ApiModelProperty("姓名")
    private String name;

    @ApiModelProperty("学号")
    private Integer studentId;

    @ApiModelProperty("性别")
    private Integer sex;

    @ApiModelProperty("家庭住址")
    private String address;

    @ApiModelProperty("出生日期")
    private String birthday;

    @ApiModelProperty("电话")
    private Integer phone;

    @ApiModelProperty("个人邮箱")
    private String email;

    @ApiModelProperty("当前科目成绩")
    private String grade;

    @ApiModelProperty("班级号")
    private String classNum;

    @ApiModelProperty("年级")
    private String year;

    @ApiModelProperty
    private int gpa;


}
