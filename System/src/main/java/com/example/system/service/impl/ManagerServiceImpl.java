package com.example.system.service.impl;

import com.example.system.common.Result;
import com.example.system.entity.Manager;
import com.example.system.entity.Student;
import com.example.system.entity.Teacher;
import com.example.system.mapper.CourseMapper;
import com.example.system.mapper.ManagerMapper;
import com.example.system.mapper.StudentMapper;
import com.example.system.mapper.TeacherMapper;
import com.example.system.service.IManagerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Lee
 * @since 2022-11-25
 */
@Service
public class ManagerServiceImpl extends ServiceImpl<ManagerMapper, Manager> implements IManagerService {
    @Resource
    CourseMapper courseMapper;
    @Resource
    StudentMapper studentMapper;

    @Resource
    TeacherMapper teacherMapper;

    //传入老师和课程名称，传回学生表
    public List<Student> findStudentsOfTeacher(@RequestParam String teacherName, String courseName) {
        String courseId = courseMapper.findCourseIdViaCourseName(courseName);
        String teacherId = teacherMapper.findTeacherIdViaTeacherName(teacherName);
        String teacherIdNow = courseMapper.findTeacherIdViaCourseId(Integer.parseInt(courseId));
        if (teacherId == teacherIdNow) {
            List<String> studentsId = courseMapper.findStudentIdViaCourseId(Integer.parseInt(courseId));
            List<Student> students = new ArrayList<>();
            for (int i = 0; i < studentsId.size(); i++) {
                Student student = studentMapper.findStudentViaStudentId(Integer.parseInt(studentsId.get(i)));
                students.set(i, student);
            }
            return students;
        }
        else{
            return null;
        }
    }
    //传入职称级别，找到对应的老师
    public List<Teacher> findTeachersViaDegree(@RequestParam String degree){
        List<Teacher> teachers=teacherMapper.findTeachersViaDegree(degree);
        return teachers;
    }

    //挑出最大的班级号返回
    public int selectYearsOfStudents(String year){

        List<String> classNum=studentMapper.findClassNumViaYear(year);
        int max=0;
        for(int i=0;i<classNum.size();i++){
            if(Integer.parseInt(classNum.get(i))>max){
                max=Integer.parseInt(classNum.get(i));
            }
        }
        return max;
    }
    //返回班级中的学生
    public List<Student> selectStudentsViaClassNumAndYear(String classNum,String year){
        List<Student> students=studentMapper.findStudentViaClassNumAndYear(classNum,year);
        return students;
    }
}
