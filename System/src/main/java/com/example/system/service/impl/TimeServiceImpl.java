package com.example.system.service.impl;

import com.example.system.entity.Time;
import com.example.system.mapper.TimeMapper;
import com.example.system.service.ITimeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Lee
 * @since 2022-10-18
 */
@Service
public class TimeServiceImpl extends ServiceImpl<TimeMapper, Time> implements ITimeService {

}
