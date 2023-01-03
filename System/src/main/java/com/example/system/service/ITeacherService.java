package com.example.system.service;

import com.example.system.entity.Course;
import com.example.system.entity.Research;
import com.example.system.entity.Student;
import com.example.system.entity.Teacher;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Lee
 * @since 2022-10-18
 */
public interface ITeacherService extends IService<Teacher> {

    public List<Student> sortGradeOfStudentOfCourse(@RequestParam String courseName, String teacherName);

    List<Student> sortGradeOfClassOfCourse(String courseName, String teacherName, String classNum);


    //返回各区间人数
    List<List<Student>> findTheNumberOfDifferentSection(String courseName, String teacherName);

    public int addNewTeacher(String name, int sex, int phone, String birthday, String email, String degree, String paper);

    public Map selectDegreeOfTeachers();
    public List<Course> findCoursesOfTeacher(int teacherId);
    public void updateScore(int teacherId,int courseId,int score);
    public List<Student> findStudentsOfCourse(int courseId);
    public List<Research> findAllResearchOfTeacher(int teacherId);
    public List<Student> findAllStudentOfResearch( String teacherName, String researchName);
    public String updateInformationOfTeacher(@RequestParam int teacherId, Map map);
    public List<Map> getTimeTable(@RequestParam int teacherId);

    List<List<Student>>  apply(int teacherId);

    void agreeApply(int studentId, String researchName);
}
