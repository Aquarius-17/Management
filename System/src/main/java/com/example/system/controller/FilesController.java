package com.example.system.controller;


import cn.hutool.core.io.FileUtil;
import cn.hutool.json.JSONException;
import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.system.common.Result;
import com.example.system.mapper.FilesMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.example.system.service.IFilesService;
import com.example.system.entity.Files;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Lee
 * @since 2022-12-05
 */
@RestController
@RequestMapping("/files")
public class FilesController {


    @Resource
    private IFilesService filesService;

    @Value("${files.upload.path}")
    private String uploadFilePath;

    @Resource
    private FilesMapper filesMapper;

    // 新增或者更新
    @PostMapping
    public boolean save(@RequestBody Files files) {
        return filesService.saveOrUpdate(files);
    }

    @DeleteMapping("/{id}")
    public Boolean delete(@PathVariable Integer id) {
        return filesService.removeById(id);
    }

    @PostMapping("/del/batch")
    public boolean deleteBatch(@RequestBody List<Integer> ids) {
        return filesService.removeByIds(ids);
    }

    @GetMapping
    public List<Files> findAll() {
        return filesService.list();
    }

    @GetMapping("/{id}")
    public Files findOne(@PathVariable Integer id) {
        return filesService.getById(id);
    }

    @GetMapping("/page")
    public Page<Files> findPage(@RequestParam Integer pageNum,
                                @RequestParam Integer pageSize) {
        QueryWrapper<Files> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        return filesService.page(new Page<>(pageNum, pageSize), queryWrapper);
    }

    @ResponseBody
    @PostMapping("/fileUpload")
    public Result fileUpload(@RequestParam("file") MultipartFile file, int studentId) throws JSONException {
        JSONObject result = new JSONObject();
        if (file.isEmpty()) {
            return Result.error("error", "空文件");
        }
        //获取文件名
        String name = file.getOriginalFilename();
        //获取文件类型
        String type = name.substring(name.lastIndexOf(".") + 1);
        //获取文件大小
        long size = file.getSize();

        File fileTempObj = new File(uploadFilePath + name);
        //检测目录是否存在
        if (!fileTempObj.getParentFile().exists()) {
            fileTempObj.getParentFile().mkdirs();
        }
        //文件名检测文件是否存在
        if (fileTempObj.exists()) {
            return Result.error("error", "文件已存在");
        }
        try {
            file.transferTo(fileTempObj);
        } catch (Exception e) {
            return Result.error("error", e.getMessage());
        }

        String url = uploadFilePath + name;
        String inName = name.substring(0, name.lastIndexOf("."));
        Files files = new Files();
        files.setUrl(url);
        files.setSize(size);
        files.setName(inName);
        files.setType(type);
        filesMapper.insert(files);
        filesMapper.updateStudentId(studentId, inName);
        return Result.success("成功上传");
    }

    //下载文件
    @ResponseBody
    @GetMapping("/download")
    public Result download(HttpServletResponse response,@RequestParam int studentId) throws IOException {
        String url = filesMapper.findUrl(studentId);
        File file=new File(url);
        if(!file.exists()){
            return Result.error("error","文件不存在");
        }
        response.reset();
        response.setContentType("application/octet-stream");
        response.setCharacterEncoding("utf-8");
        response.setContentLength((int)file.length());
        response.setHeader("Content-Disposition","attachment;studentId="+studentId);
        byte[] readBytes= FileUtil.readBytes(file);
        OutputStream os=response.getOutputStream();
        os.write(readBytes);
        return Result.success();
    }
    //删除文件
    @ResponseBody
    @PostMapping("/deleteFile")
    public Result deleteFile(@RequestParam int studentId){
        String url = filesMapper.findUrl(studentId);
        File file=new File(url);
        if(!file.exists()){
            return Result.error("error","文件不存在");
        }
        try{
            if(file.isFile()){
                file.delete();
            }
            else{
                for(File temp:file.listFiles()){
                    temp.delete();
                }
                file.delete();
            }
        }
        catch (Exception e){
            return Result.error("error",e.getMessage());
        }
        return Result.success();
    }
}

