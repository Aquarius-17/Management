package com.example.system.service;

import com.example.system.entity.Manager;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.system.entity.Student;
import com.example.system.entity.Teacher;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Lee
 * @since 2022-11-25
 */
public interface IManagerService extends IService<Manager> {
    public List<Student> findStudentsOfTeacher(@RequestParam String teacherName, String courseName);
    public List<Teacher> findTeachersViaDegree(@RequestParam String degree);
    public int selectYearsOfStudents(String year);
    public List<Student> selectStudentsViaClassNumAndYear(String classNum,String year);
}
