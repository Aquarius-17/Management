package com.example.system.service.impl;

import com.example.system.entity.Files;
import com.example.system.mapper.FilesMapper;
import com.example.system.service.IFilesService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Lee
 * @since 2022-12-05
 */
@Service
public class FilesServiceImpl extends ServiceImpl<FilesMapper, Files> implements IFilesService {

}
