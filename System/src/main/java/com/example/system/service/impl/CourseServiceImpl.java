package com.example.system.service.impl;

import com.example.system.entity.Course;
import com.example.system.mapper.CourseMapper;
import com.example.system.service.ICourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Lee
 * @since 2022-10-18
 */
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements ICourseService {

    @Resource
    CourseMapper courseMapper;

    @Override
    public String findCourseIdViaCourseName(String courseName) {
        return courseMapper.findCourseIdViaCourseName(courseName);
    }
}
