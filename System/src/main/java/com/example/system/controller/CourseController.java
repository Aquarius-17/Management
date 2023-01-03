package com.example.system.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.example.system.service.ICourseService;
import com.example.system.entity.Course;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Lee
 * @since 2022-10-18
 */
@RestController
@RequestMapping("/course")
        public class CourseController {

@Resource
private ICourseService courseService;

// 新增或者更新
@PostMapping
public boolean save(@RequestBody Course course) {
        return courseService.saveOrUpdate(course);
        }

@DeleteMapping("/{id}")
public Boolean delete(@PathVariable Integer id) {
        return courseService.removeById(id);
        }

@PostMapping("/del/batch")
public boolean deleteBatch(@RequestBody List<Integer> ids) {
        return courseService.removeByIds(ids);
        }

@GetMapping
public List<Course> findAll() {
        return courseService.list();
        }

@GetMapping("/{id}")
public Course findOne(@PathVariable Integer id) {
        return courseService.getById(id);
        }

@GetMapping("/page")
public Page<Course> findPage(@RequestParam Integer pageNum,
@RequestParam Integer pageSize) {
        QueryWrapper<Course> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        return courseService.page(new Page<>(pageNum, pageSize), queryWrapper);
        }



        }

