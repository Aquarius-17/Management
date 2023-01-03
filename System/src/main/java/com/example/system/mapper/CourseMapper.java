package com.example.system.mapper;

import com.example.system.entity.Course;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Lee
 * @since 2022-10-18
 */
@Mapper
public interface CourseMapper extends BaseMapper<Course> {

    @Select("select courseId from course where name=#{courseName}")
    String findCourseIdViaCourseName(String courseName);

    @Select("select courseName from course where name=#{courseId}")
    String findCourseNameViaCourseId(int courseId);

    @Insert("insert into st_co (studentId,courseId) values (studentId,courseId)")
    void insertCourseAndStudent(int studentId,int courseId);

    @Select("select teacherId from te_co where courseId=#{courseId}")
    String findTeacherIdViaCourseId(int courseId);

    @Select("select studentId from st_co where courseId=#{courseId}")
    List<String> findStudentIdViaCourseId(int courseId);

    @Select("select grade from st_co where courseId=#{courseId} and studentId=#{studentId}")
    String findGradeOfStudentOfCourse(int courseId,int studentId);

    @Select("select * from course where courseId=#{courseId}")
    Course findCourseViaCourseId(int courseId);

    @Select("select grade from st_co where courseId=#{courseId}")
    List<Integer> findGrades(int courseId);

}
