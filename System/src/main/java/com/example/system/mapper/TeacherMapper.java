package com.example.system.mapper;

import com.example.system.entity.Teacher;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Lee
 * @since 2022-10-18
 */
public interface TeacherMapper extends BaseMapper<Teacher> {

    @Select("select teacherId from teacher where name=#{name}")
    String findTeacherIdViaTeacherName(String name);

    @Select("select teacherId from co_te where courseId=#{courseId}")
    String findTeacherIdViaCourseId(int courseId);

    @Select("select * from teacher where degree=#{degree}")
    List<Teacher> findTeachersViaDegree(String degree);

    @Select("select id from teacher where name=#{name} and sex=#{sex} and phone=#{phone}")
    String findIdOfTeacher(String name,int sex,int phone);

    @Insert("insert into teacher (name,sex,phone,birthday,email,degree,paper) values(#{name},#{sex},#{phone},#{birthday},#{email},#{degree},#{paper})")
    void addNewTeacher(String name, int sex, int phone, String birthday, String email, String degree, String paper);

    @Update("update teacher set teacherId=#{teacherId} where name=#{name} and sex=#{sex} and phone=#{phone}")
    void updateTeacherId(int teacherId,String name,int sex,int phone);

    @Select("select courseId from co_te where teacherId=#{teacherId}")
    List<Integer> findCourseIdsViaTeacherId(int teacherId);

    @Update("update st_co set score=#{score} where studentId=#{studentId} and courseId=#{courseId}")
    void updateScore(int score,int studentId,int courseId);

    @Select("select studentId from st_co where courseId=#{courseId}")
    List<Integer> findStudentIdViaCourseId(int courseId);

    @Update("update teacher set paper=#{paper} where teacherId=#{teacherId}")
    void updatePaper(String paper,int teacherId);

    @Update("update teacher set search=#{search} where teacherId=#{teacherId}")
    void updateSearch(String search,int teacherId);

    @Update("update teacher set email=#{email} where teacherId=#{teacherId}")
    void updateEmail(String email,int teacherId);

    @Update("update teacher set degree=#{degree} where teacherId=#{teacherId}")
    void updateDegree(String degree,int teacherId);

    @Update("update teacher set phone=#{phone} where teacherId=#{teacherId}")
    void updatePhone(int phone,int teacherId);

    @Select("select timeId from co_ti where courseId=#{courseId}")
    int findTimeId(int courseId);

    @Update("update re_st set add=#{add} where studentId=#{studentId} and researchName=#[researchName}")
    void agreeApply(int studentId, String researchName,int add);
}
