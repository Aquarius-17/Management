package com.example.system.service.impl;

import com.example.system.common.Result;
import com.example.system.entity.Course;
import com.example.system.entity.Research;
import com.example.system.entity.Student;
import com.example.system.entity.Teacher;
import com.example.system.mapper.CourseMapper;
import com.example.system.mapper.ResearchMapper;
import com.example.system.mapper.StudentMapper;
import com.example.system.mapper.TeacherMapper;
import com.example.system.service.ITeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Lee
 * @since 2022-10-18
 */
@Service
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher> implements ITeacherService {

    @Resource
    TeacherMapper teacherMapper;
    @Resource
    CourseMapper courseMapper;
    @Resource
    StudentMapper studentMapper;

    @Resource
    ResearchMapper researchMapper;

    @Override
    public List<Student> sortGradeOfStudentOfCourse(String courseName, String teacherName) {
        List<Student> students = new ArrayList<>();
        String teacherId = teacherMapper.findTeacherIdViaTeacherName(teacherName);
        String courseId = courseMapper.findCourseIdViaCourseName(courseName);
        String teacherId2 = courseMapper.findTeacherIdViaCourseId(Integer.parseInt(courseId));
        //保证传入的课程是该老师开设的
        if (teacherId != teacherId2) {
            return null;
        } else {
            List<String> studentIds = courseMapper.findStudentIdViaCourseId(Integer.parseInt(courseId));
            int len = studentIds.size();
            for (int i = 0; i < len; i++) {
                Student tmp_stu = studentMapper.findStudentViaStudentId(Integer.parseInt(studentIds.get(i)));
                String grade = courseMapper.findGradeOfStudentOfCourse(Integer.parseInt(courseId), Integer.parseInt(studentIds.get(i)));
                tmp_stu.setGrade(grade);
                students.add(tmp_stu);
            }
            for (int n = 0; n < students.size(); n++) {
                for (int m = 0; m < students.size() - 1; m++) {
                    if (Integer.parseInt(students.get(m).getGrade()) < Integer.parseInt(students.get(m + 1).getGrade())) {
                        Student stu1 = students.get(m);
                        Student stu2 = students.get(m + 1);
                        students.set(m, stu2);
                        students.set(m + 1, stu1);
                    } else {
                    }
                }
            }
        }
        return students;
    }

    @Override
    public List<Student> sortGradeOfClassOfCourse(String courseName, String teacherName, String classNum) {
        List<Student> students = new ArrayList<>();
        String teacherId = teacherMapper.findTeacherIdViaTeacherName(teacherName);
        String courseId = courseMapper.findCourseIdViaCourseName(courseName);
        String teacherId2 = courseMapper.findTeacherIdViaCourseId(Integer.parseInt(courseId));
        //保证传入的课程是该老师开设的
        if (teacherId != teacherId2) {
            return null;
        } else {
            List<String> studentIdList = courseMapper.findStudentIdViaCourseId(Integer.parseInt(courseId));
            int len = studentIdList.size();
            for (int i = 0; i < len; i++) {
                Student tmp_stu = studentMapper.findStudentViaStudentId(Integer.parseInt(studentIdList.get(i)));
                if (tmp_stu.getClassNum() == classNum) {
                    String grade = courseMapper.findGradeOfStudentOfCourse(Integer.parseInt(courseId), Integer.parseInt(studentIdList.get(i)));
                    tmp_stu.setGrade(grade);
                    students.add(tmp_stu);
                } else {
                }

            }
            for (int k = 0; k < students.size(); k++) {
                for (int m = 0; m < students.size() - 1; m++) {
                    if (Integer.parseInt(students.get(m).getGrade()) < Integer.parseInt(students.get(m + 1).getGrade())) {
                        Student stu1 = students.get(m);
                        students.set(m, students.get(m + 1));
                        students.set(m + 1, stu1);
                    } else {
                    }
                }
            }
        }
        return students;
    }


    //返回各区间人数
    @Override
    public List<List<Student>> findTheNumberOfDifferentSection(String courseName, String teacherName) {
        List<Student> students0 = new ArrayList<>();
        List<Student> students1 = new ArrayList<>();
        List<Student> students2 = new ArrayList<>();
        List<Student> students3 = new ArrayList<>();
        List<Student> students4 = new ArrayList<>();
        List<List<Student>> studentResults = new ArrayList<>();
        List<Student> students = new ArrayList<>();
        String teacherId = teacherMapper.findTeacherIdViaTeacherName(teacherName);
        String courseId = courseMapper.findCourseIdViaCourseName(courseName);
        String teacherId2 = courseMapper.findTeacherIdViaCourseId(Integer.parseInt(courseId));
        //保证传入的课程是该老师开设的
        if (teacherId != teacherId2) {
            return null;
        } else {
            List<String> studentIds = courseMapper.findStudentIdViaCourseId(Integer.parseInt(courseId));
            int len = studentIds.size();
            for (int i = 0; i < len; i++) {
                Student tmp_stu = studentMapper.findStudentViaStudentId(Integer.parseInt(studentIds.get(i)));
                int num = is_whichSection(tmp_stu);
                if (num == 0) {
                    students0.add(tmp_stu);
                } else if (num == 1) {
                    students1.add(tmp_stu);
                } else if (num == 2) {
                    students2.add(tmp_stu);
                } else if (num == 3) {
                    students3.add(tmp_stu);
                } else {
                    students4.add(tmp_stu);
                }
            }
            studentResults.set(0, students0);
            studentResults.set(1, students1);
            studentResults.set(2, students2);
            studentResults.set(3, students3);
            studentResults.set(4, students4);

        }
        return studentResults;
    }

    private int is_whichSection(Student student) {
        if (Integer.parseInt(student.getGrade()) < 60) {
            return 0;
        } else if (Integer.parseInt(student.getGrade()) <= 70 && Integer.parseInt(student.getGrade()) > 60) {
            return 1;
        } else if (Integer.parseInt(student.getGrade()) <= 80 && Integer.parseInt(student.getGrade()) > 70) {
            return 2;
        } else if (Integer.parseInt(student.getGrade()) <= 90 && Integer.parseInt(student.getGrade()) > 80) {
            return 3;
        } else {
            return 4;
        }

    }

    public int addNewTeacher(String name, int sex, int phone, String birthday, String email, String degree, String paper) {
        Teacher teacher = new Teacher();
        teacher.setName(name);
        teacher.setSex(sex);
        teacher.setPhone(phone);
        teacher.setBirthday(birthday);
        teacher.setEmail(email);
        teacher.setDegree(degree);
        teacher.setPaper(paper);
        teacherMapper.insert(teacher);
        String ID = teacherMapper.findIdOfTeacher(name, sex, phone);
        int teacherId = Integer.parseInt(birthday + ID);
        teacherMapper.updateTeacherId(teacherId, name, sex, phone);
        return teacherId;

    }

    public Map selectDegreeOfTeachers() {
        List<Teacher> teacher0 = teacherMapper.findTeachersViaDegree("0");
        List<Teacher> teacher1 = teacherMapper.findTeachersViaDegree("1");
        List<Teacher> teacher2 = teacherMapper.findTeachersViaDegree("2");
        List<Teacher> teacher3 = teacherMapper.findTeachersViaDegree("3");
        Map<String, String> map = new HashMap<String, String>();
        map.put("教授", Integer.toString(teacher0.size()));
        map.put("副教授", Integer.toString(teacher1.size()));
        map.put("讲师", Integer.toString(teacher2.size()));
        map.put("助教", Integer.toString(teacher3.size()));
        return map;
    }

    public List<Course> findCoursesOfTeacher(int teacherId) {
        List<Course> courses = new ArrayList<>();
        List<Integer> courseIds = teacherMapper.findCourseIdsViaTeacherId(teacherId);
        for (int i = 0; i < courseIds.size(); i++) {
            Course course = courseMapper.findCourseViaCourseId(courseIds.get(i));
            courses.set(i, course);
        }
        return courses;
    }

    public void updateScore(int studentId, int courseId, int score) {
        teacherMapper.updateScore(score, studentId, courseId);
    }

    public List<Student> findStudentsOfCourse(int courseId) {
        List<Student> students = new ArrayList<>();
        List<Integer> studentIds = teacherMapper.findStudentIdViaCourseId(courseId);
        for (int i = 0; i < studentIds.size(); i++) {
            Student student = studentMapper.findStudentViaStudentId(studentIds.get(i));
            students.set(i, student);
        }
        return students;
    }

    public List<Research> findAllResearchOfTeacher(int teacherId) {
        List<Research> researchList = new ArrayList<>();
        List<String> researchNames = researchMapper.findResearchNameViaTeacherId(teacherId);
        for (int i = 0; i < researchNames.size(); i++) {
            Research research = researchMapper.findResearchesViaResearchName(researchNames.get(i));
            researchList.set(i, research);
        }
        return researchList;

    }

    public List<Student> findAllStudentOfResearch(String teacherName, String researchName) {
        List<Student> students = new ArrayList<>();
        List<Integer> studentIds = studentMapper.findStudentIdOfResearch(researchName,1);
        for (int i = 0; i < studentIds.size(); i++) {
            Student student = studentMapper.findStudentViaStudentId(studentIds.get(i));
            students.set(i, student);
        }
        return students;

    }

    public String updateInformationOfTeacher(@RequestParam int teacherId, Map map) {
        int phone = (int) map.get("phone");
        String email = (String) map.get("email");
        String search = (String) map.get("search");
        String paper = (String) map.get("paper");
        String degree = (String) map.get("degree");
        try{
            if (!email.isEmpty()) {
                teacherMapper.updateEmail(email, teacherId);
            }
            if (!Integer.toString(phone).isEmpty()) {
                teacherMapper.updatePhone(phone, teacherId);
            }
            if (!search.isEmpty()) {
                teacherMapper.updateSearch(search, teacherId);
            }
            if (!paper.isEmpty()) {
                teacherMapper.updatePaper(paper, teacherId);
            }
            if (!degree.isEmpty()) {
                teacherMapper.updateDegree(degree, teacherId);
            }
        }catch (Exception e){
            return e.getMessage();
        }

        return "成功修改";

    }


    public List<Map> getTimeTable(@RequestParam int teacherId){
        List<Integer> courseIds = teacherMapper.findCourseIdsViaTeacherId(teacherId);
        List<Map> maps=new ArrayList<>();
        for(int i=0;i<courseIds.size();i++){
            int timeId=teacherMapper.findTimeId(courseIds.get(i));
            String courseName=courseMapper.findCourseNameViaCourseId(courseIds.get(i));
            Map map=new HashMap<>();
            map.put(courseName,timeId);
            maps.set(i,map);
        }
        return maps;
    }

    @Override
    public List<List<Student>>  apply(int teacherId) {
        List<String> researches=researchMapper.findResearchNameViaTeacherId(teacherId);
        List<List<Student>> listList=new ArrayList<>();
        for(int i=0;i<researches.size();i++){
            String researchName=researches.get(i);
            List<Student> students=new ArrayList<>();
            List<Integer> studentIds=researchMapper.findApply(researchName,0);
            for(int n=0;n<studentIds.size();n++){
                Student news=studentMapper.findStudentViaStudentId(studentIds.get(n));
                students.set(n,news);
            }
            listList.set(i,students);
        }
        return listList;
    }

    @Override
    public void agreeApply(int studentId, String researchName) {
        teacherMapper.agreeApply(studentId,researchName,1);
    }
}
