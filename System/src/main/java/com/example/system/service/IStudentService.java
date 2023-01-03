package com.example.system.service;

import com.example.system.entity.Course;
import com.example.system.entity.Student;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 青哥哥
 * @since 2022-10-17
 */
public interface IStudentService extends IService<Student> {

    Student findOne(String studentId);

    List<Map> studentPicture(String studentId);

    List<List<Course>> courseTable(int studentId);

    List<String> findResearch();

    List<String> showResearches(int studentId);
}
