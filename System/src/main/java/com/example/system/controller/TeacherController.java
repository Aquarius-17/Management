package com.example.system.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.system.common.Result;
import com.example.system.entity.Course;
import com.example.system.entity.Research;
import com.example.system.entity.Student;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.example.system.service.ITeacherService;
import com.example.system.entity.Teacher;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Lee
 * @since 2022-10-18
 */
@RestController
@RequestMapping("/teacher")
public class TeacherController {

    @Resource
    private ITeacherService teacherService;

    // 新增或者更新
    @PostMapping
    public boolean save(@RequestBody Teacher teacher) {
        return teacherService.saveOrUpdate(teacher);
    }

    @DeleteMapping("/{id}")
    public Boolean delete(@PathVariable Integer id) {
        return teacherService.removeById(id);
    }

    @PostMapping("/del/batch")
    public boolean deleteBatch(@RequestBody List<Integer> ids) {
        return teacherService.removeByIds(ids);
    }

    @GetMapping
    public List<Teacher> findAll() {
        return teacherService.list();
    }

    @GetMapping("/{id}")
    public Teacher findOne(@PathVariable Integer id) {
        return teacherService.getById(id);
    }

    @GetMapping("/page")
    public Page<Teacher> findPage(@RequestParam Integer pageNum,
                                  @RequestParam Integer pageSize) {
        QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        return teacherService.page(new Page<>(pageNum, pageSize), queryWrapper);
    }

    //老师查看自己课程的学生成绩，成绩已经排名了，由高到低
    @GetMapping("/sortGradeOfStudentOfCourse")
    public Result sortGradeOfStudentOfCourse(@RequestParam String courseName, String teacherName) {
        List<Student> students=new ArrayList<>();
        students=teacherService.sortGradeOfStudentOfCourse(courseName,teacherName);
        if(students==null){
            return Result.success("课程老师不匹配");
        }
        return Result.success(students);
    }
    //老师查看特定班级的该课程学生成绩，并由高到低
    @GetMapping("/sortGradeOfClassOfCourse")
    public Result sortGradeOfClassOfCourse(@RequestParam String courseName,String teacherName,String classNum){
        List<Student> students=teacherService.sortGradeOfClassOfCourse(courseName, teacherName, classNum);
        if(students==null){
            return Result.success("课程老师不匹配");
        }
        return Result.success(students);
    }
    //返回一个列表，第一个是不及格人，第二个是60-70，依次类推
    @GetMapping("/findTheNumberOfDifferentSection")
    public Result findTheNumberOfDifferentSection(@RequestParam String courseName,String teacherName) {
        List<List<Student>> studentResults = teacherService.findTheNumberOfDifferentSection(courseName, teacherName);
        return Result.success(studentResults);
    }
    //返回该老师教授的所有课程的信息
    @GetMapping("/findCoursesOfTeacher")
    public Result findCoursesOfTeacher(@RequestParam int teacherId){
        List<Course> courses=teacherService.findCoursesOfTeacher(teacherId);
        return Result.success(courses);
    }

    //打分
    @GetMapping("/updateScore")
    public Result updateScore(@RequestParam int studentId,int courseId,int score){
        teacherService.updateScore(studentId,courseId,score);
        return Result.success();
    }

    //返回课程学生
    @GetMapping("/findStudentsOfCourse")
    public Result findStudentsOfCourse(@RequestParam int courseId){
        List<Student> students=teacherService.findStudentsOfCourse(courseId);
        return Result.success(students);
    }
    //返回该老师的所有科研主题
    @GetMapping("/findAllResearchOfTeacher")
    public Result findAllResearchOfTeacher(@RequestParam int teacherId){
        List<Research> researchList=teacherService.findAllResearchOfTeacher(teacherId);
        return Result.success(researchList);
    }
    //返回老师某课题包含的学生
    @GetMapping("/findAllStudentOfResearch")
    public Result findAllStudentOfResearch(@RequestParam String teacherName,String researchName){
        List<Student> students=teacherService.findAllStudentOfResearch(teacherName,researchName);
        return Result.success(students);
    }

    //修改个人简介
    @PostMapping("/updateInformationOfTeacher")
    public Result updateInformationOfTeacher(@RequestParam int teacherId, Map map){
        String code = teacherService.updateInformationOfTeacher(teacherId, map);
        return Result.success(code);
    }
    //生成老师的课表
    @GetMapping("/getTimeTable")
    public Result getTimeTable(@RequestParam int teacherId){
        List<Map> timeTable = teacherService.getTimeTable(teacherId);
        return Result.success(timeTable);
    }
    //查看科研团队申请者
    @GetMapping("/apply")
    public Result apply(int teacherId){
        List<List<Student>>  students=teacherService.apply(teacherId);
        return Result.success(students);
    }

    //同意申请
    @GetMapping("/agreeApply")
    public Result agreeApply(@RequestParam int studentId,String researchName){
        teacherService.agreeApply(studentId,researchName);
        return Result.success("成功");
    }



}

