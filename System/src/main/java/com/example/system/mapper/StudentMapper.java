package com.example.system.mapper;

import com.example.system.entity.Student;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 青哥哥
 * @since 2022-10-17
 */
@Mapper
public interface StudentMapper extends BaseMapper<Student> {

    @Select("select studentId from student where name=#{name}")
    String findStudentIdViaStudentName(String name);

    @Select("select * from student where studentId=#{studentId}")
    Student findStudentViaStudentId(int studentId);

    @Select("select * from student where classNum=#{classNum} and year=#{year}")
    List<Student> findStudentViaClassNumAndYear(String classNum, String year);

    @Select("select classNum from student where year=#{year}")
    List<String> findClassNumViaYear(String year);

    @Select("select studentId from re_st where researchName=#{researchName} and add=#{add}")
    List<Integer> findStudentIdOfResearch(String researchName,int add);

    @Select("select courseId from st_co where studentId=#{studentId}")
    List<Integer> findCourseIds(int studentId);

    @Select("select grade from st_co where courseId=#{courseId} and studentId=#{studentId}")
    int findGrade(int courseId,int studentId);

    @Update("update student set gpa=#{gpa} where studentId=#{studentId}")
    void setGpa(int gpa,int studentId);

    @Select("select gpa from student")
    List<Integer> findGPAs();

    @Select("select researchName from re_st where studentId=#{studentId}")
    List<String> findResearch(int studentId);

    @Select("select researchName from research where full=#{full}")
    List<String> findAllResearch(int full);

    @Select("select researchName from re_st where studentId=#{studentId}")
    List<String> showResearches(int studentId);

}
