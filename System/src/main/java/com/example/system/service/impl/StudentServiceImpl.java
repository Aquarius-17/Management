package com.example.system.service.impl;

import com.example.system.entity.Course;
import com.example.system.entity.Student;
import com.example.system.mapper.CourseMapper;
import com.example.system.mapper.StudentMapper;
import com.example.system.service.IStudentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 青哥哥
 * @since 2022-10-17
 */
@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements IStudentService {

    @Autowired
    StudentMapper studentMapper;

    @Autowired
    CourseMapper courseMapper;

    @Override
    public Student findOne(String studentId) {
        Student student = studentMapper.findStudentViaStudentId(Integer.parseInt(studentId));
        return student;
    }

    @Override
    public List<Map> studentPicture(String studentId) {
        Student student = studentMapper.findStudentViaStudentId(Integer.parseInt(studentId));
        String gpa = gpa(studentId);
        int gpaRank = getGpaRank(studentId);
        int classNum = Integer.parseInt(student.getClassNum());
        List<String> orgName = studentMapper.findResearch(Integer.parseInt(studentId));
        List<Map> maps = new ArrayList<>();
        Map map1 = null;
        map1.put("gap", gpa);
        Map map2 = null;
        map2.put("gpaRank", gpaRank);
        Map map3 = null;
        map3.put("classNum", classNum);
        Map map4 = null;
        map4.put("orgName", orgName);
        maps.set(1,map1);
        maps.set(2,map2);
        maps.set(3,map3);
        maps.set(4,map4);
        return maps;

    }


    //计算总绩点
    public String gpa(String studentId) {
        List<Integer> courseIds = studentMapper.findCourseIds(Integer.parseInt(studentId));
        //计算所以课程总绩点
        int allCredit = 0;
        for (int i = 0; i < courseIds.size(); i++) {
            Course now = courseMapper.findCourseViaCourseId(courseIds.get(i));
            allCredit = allCredit + Integer.parseInt(now.getCredit());
        }
        int gotCredit = 0;
        for (int n = 0; n < courseIds.size(); n++) {
            Course cor = courseMapper.findCourseViaCourseId(courseIds.get(n));
            int grade = studentMapper.findGrade(courseIds.get(n), Integer.parseInt(studentId));
            int credit = Integer.parseInt(cor.getCredit());
            int nowCredit = (grade / 100) * credit;
            gotCredit = gotCredit + nowCredit;
        }
        double finCredit = (gotCredit / allCredit) * 5;
        String fin = String.format("%.2f", finCredit);
        studentMapper.setGpa(Integer.parseInt(fin), Integer.parseInt(studentId));
        return fin;
    }

    //单课程年级排名
    public int getRank(int courseId, String studentId1) {
        int studentId = Integer.parseInt(studentId1);
        int rank = 1;
        List<Integer> grades = courseMapper.findGrades(courseId);
        int grade = studentMapper.findGrade(courseId, studentId);
        if (grades != null) {
            for (int i = 0; i < grades.size(); i++) {
                if (grades.get(i) > grade) {
                    rank++;
                }
            }
        }
        return rank;
    }

    //绩点年级排名
    public int getGpaRank(String studentId1) {
        int studentId = Integer.parseInt(studentId1);
        String gp = gpa(studentId1);
        int gpa = Integer.parseInt(gp);
        List<Integer> GPAs = studentMapper.findGPAs();
        int gpaRank = 1;
        for (int i = 0; i < GPAs.size(); i++) {
            if (GPAs.get(i) > gpa) {
                gpaRank++;
            }
        }
        return gpaRank;
    }

    //
    @Override
    public List<List<Course>> courseTable(int studentId) {
        //必修课程查询（0）
        List<Course> BI=new ArrayList<>();
        //限选课程查询（1）
        List<Course> XIAN=new ArrayList<>();
        //任选课程查询（2）
        List<Course> REN=new ArrayList<>();
        List<Integer> listBI=studentMapper.findCourseIds(studentId);
        for(int i=0;i<listBI.size();i++){
            Course now=courseMapper.findCourseViaCourseId(listBI.get(i));
            if(Integer.parseInt(now.getCourseType())== 0){
                BI.set(i,now);
            }
            else if (Integer.parseInt(now.getCourseType())== 1){
                XIAN.set(i,now);
            }else {
                REN.set(i,now);
            }
        }
        List<List<Course>> listList=new ArrayList<>();
        listList.set(0,BI);
        listList.set(1,XIAN);
        listList.set(2,REN);
        return listList;
    }

    @Override
    public List<String> findResearch() {
       List<String> names= studentMapper.findAllResearch(0);
       return names;
    }

    @Override
    public List<String> showResearches(int studentId) {
        List<String> names=studentMapper.showResearches(studentId);
        return names;
    }

}
