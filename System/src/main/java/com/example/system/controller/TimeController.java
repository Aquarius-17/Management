package com.example.system.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.example.system.service.ITimeService;
import com.example.system.entity.Time;

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
@RequestMapping("/time")
        public class TimeController {
    
@Resource
private ITimeService timeService;

// 新增或者更新
@PostMapping
public boolean save(@RequestBody Time time) {
        return timeService.saveOrUpdate(time);
        }

@DeleteMapping("/{id}")
public Boolean delete(@PathVariable Integer id) {
        return timeService.removeById(id);
        }

@PostMapping("/del/batch")
public boolean deleteBatch(@RequestBody List<Integer> ids) {
        return timeService.removeByIds(ids);
        }

@GetMapping
public List<Time> findAll() {
        return timeService.list();
        }

@GetMapping("/{id}")
public Time findOne(@PathVariable Integer id) {
        return timeService.getById(id);
        }

@GetMapping("/page")
public Page<Time> findPage(@RequestParam Integer pageNum,
@RequestParam Integer pageSize) {
        QueryWrapper<Time> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        return timeService.page(new Page<>(pageNum, pageSize), queryWrapper);
        }

        }

