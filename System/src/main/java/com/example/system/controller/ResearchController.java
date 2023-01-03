package com.example.system.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.example.system.service.IResearchService;
import com.example.system.entity.Research;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Lee
 * @since 2022-11-26
 */
@RestController
@RequestMapping("/research")
public class ResearchController {

    @Resource
    private IResearchService researchService;

    // 新增或者更新
    @PostMapping
    public boolean save(@RequestBody Research research) {
        return researchService.saveOrUpdate(research);
    }

    @DeleteMapping("/{id}")
    public Boolean delete(@PathVariable Integer id) {
        return researchService.removeById(id);
    }

    @PostMapping("/del/batch")
    public boolean deleteBatch(@RequestBody List<Integer> ids) {
        return researchService.removeByIds(ids);
    }

    @GetMapping
    public List<Research> findAll() {
        return researchService.list();
    }

    @GetMapping("/{id}")
    public Research findOne(@PathVariable Integer id) {
        return researchService.getById(id);
    }

    @GetMapping("/page")
    public Page<Research> findPage(@RequestParam Integer pageNum,
                                   @RequestParam Integer pageSize) {
        QueryWrapper<Research> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        return researchService.page(new Page<>(pageNum, pageSize), queryWrapper);
    }


}

