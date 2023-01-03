package com.example.system.service;

import com.example.system.entity.Course;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Lee
 * @since 2022-10-18
 */
public interface ICourseService extends IService<Course> {

    String findCourseIdViaCourseName(String courseName);

}
