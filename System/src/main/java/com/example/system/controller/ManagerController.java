package com.example.system.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.system.common.Result;
import com.example.system.entity.Research;
import com.example.system.entity.Student;
import com.example.system.entity.Teacher;
import com.example.system.service.impl.TeacherServiceImpl;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.example.system.service.IManagerService;
import com.example.system.entity.Manager;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Lee
 * @since 2022-11-25
 */
@RestController
@RequestMapping("/manager")
public class ManagerController {

    @Resource
    private IManagerService managerService;

    @Resource
    private TeacherServiceImpl teacherService;

    // 新增或者更新
    @PostMapping
    public boolean save(@RequestBody Manager manager) {
        return managerService.saveOrUpdate(manager);
    }

    @DeleteMapping("/{id}")
    public Boolean delete(@PathVariable Integer id) {
        return managerService.removeById(id);
    }

    @PostMapping("/del/batch")
    public boolean deleteBatch(@RequestBody List<Integer> ids) {
        return managerService.removeByIds(ids);
    }

    @GetMapping
    public List<Manager> findAll() {
        return managerService.list();
    }

    @GetMapping("/{id}")
    public Manager findOne(@PathVariable Integer id) {
        return managerService.getById(id);
    }

    @GetMapping("/page")
    public Page<Manager> findPage(@RequestParam Integer pageNum,
                                  @RequestParam Integer pageSize) {
        QueryWrapper<Manager> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        return managerService.page(new Page<>(pageNum, pageSize), queryWrapper);
    }

    //.选课学生信息（所有选课学生）
    //      传入值：课程名和老师名
    //      返回值：学生列表
    @GetMapping("/findStudentsOfTeacher")
    public Result findStudentsOfTeacher(@RequestParam String teacherName, String courseName) {
        List<Student> students = managerService.findStudentsOfTeacher(teacherName, courseName);
        if (students == null) {
            return Result.error("000", "该老师未开始这门课");
        } else {
            return Result.success(students);

        }
    }

    //按教师职称返回四个列表的老师 教授0 副教授1 讲师2 助教3
    @GetMapping("/findTeachersViaDegree")
    public Result findTeachersViaDegree(@RequestParam String degreeName){
        String degree;
        if (degreeName == "教授") {
            degree="0";
        }
        else if(degreeName=="副教授"){
            degree="1";
        } else if (degreeName=="讲师") {
            degree="2";
        }
        else{
            degree="3";
        }

        List<Teacher> teachers=managerService.findTeachersViaDegree(degree);
        return Result.success(teachers);
    }
    //新增老师,返回学工号
    @GetMapping("/addNewTeacher")
    public Result addNewTeacher(@RequestParam String name, int sex, int phone, String birthday, String email, String degree, String paper){
        int teacherId=teacherService.addNewTeacher(name, sex, phone, birthday, email, degree, paper);
        return Result.success(teacherId);
    }
    //返回各个职称的人数以及对应名称
    @GetMapping("/selectDegreeOfTeachers")
    public Result selectDegreeOfTeachers(){
        Map map=teacherService.selectDegreeOfTeachers();
        return Result.success(map);
    }
    //按大一大二大三大四返回最大的班级号
    @GetMapping("/selectYearsOfStudents")
    public Result selectYearsOfStudents(@RequestParam String year){
        int max_size=managerService.selectYearsOfStudents(year);
        return Result.success(max_size);
    }
    //返回班级学生信息
    @GetMapping("/selectStudentsViaClassNumAndYear")
    public Result selectStudentsViaClassNumAndYear(@RequestParam String classNum,String year){
        List<Student> studentList=managerService.selectStudentsViaClassNumAndYear(classNum, year);
        return Result.success(studentList);
    }
    //教师科研团队查询
    @GetMapping("/findAllResearchOfTeacher")
    public Result findAllResearchOfTeacher(@RequestParam int teacherId){
        List<Research> researchList=teacherService.findAllResearchOfTeacher(teacherId);
        return Result.success(researchList);
    }
}

