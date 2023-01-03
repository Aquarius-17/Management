package com.example.system.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.system.common.Result;
import com.example.system.entity.Course;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.example.system.service.IStudentService;
import com.example.system.entity.Student;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 青哥哥
 * @since 2022-10-17
 */
@RestController
@RequestMapping("/student")
public class StudentController {

    @Resource
    private IStudentService studentService;

    // 新增或者更新
    @PostMapping
    public boolean save(@RequestBody Student student) {
        return studentService.saveOrUpdate(student);
    }

    @DeleteMapping("/{id}")
    public Boolean delete(@PathVariable Integer id) {
        return studentService.removeById(id);
    }

    @PostMapping("/del/batch")
    public boolean deleteBatch(@RequestBody List<Integer> ids) {
        return studentService.removeByIds(ids);
    }

    @GetMapping
    public List<Student> findAll() {
        return studentService.list();
    }

    @GetMapping("/{id}")
    public Student findOne(@PathVariable Integer id) {
        return studentService.getById(id);
    }

    @GetMapping("/page")
    public Page<Student> findPage(@RequestParam Integer pageNum,
                                  @RequestParam Integer pageSize) {
        QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        return studentService.page(new Page<>(pageNum, pageSize), queryWrapper);
    }

    //返回学生所有信息
    @GetMapping("/findOne")
    public Result findOne(@RequestParam String studentId){
        Student student=studentService.findOne(studentId);
        return Result.success(student);
    }
    //头像修改直接调用文件下载方法即可

    //用户画像
    @GetMapping("/studentPicture")
    public Result studentPicture(String studentId){
        List<Map> maps = studentService.studentPicture(studentId);
        return Result.success(maps);
    }

    //个人简介文件上传下载展示即可

    //学生可以加入的老师科研主题
    //0代表没满
    @GetMapping("/findResearch")
    public Result findResearch(){
        List<String> research = studentService.findResearch();
        return Result.success(research);
    }

    //返回该同学加入的科研团队
    @GetMapping("/showResearches")
    public Result showResearches(@RequestParam int studentId){
        List<String> researches = studentService.showResearches(studentId);
        return Result.success(researches);
    }
}

