package com.example.system.mapper;

import com.example.system.entity.Files;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Lee
 * @since 2022-12-05
 */
public interface FilesMapper extends BaseMapper<Files> {

    @Update("update files set studentId=#{studentId} where name=#{name}")
    void updateStudentId(int studentId,String name);

    @Select("select url from files where studentId=#{studentId}")
    String findUrl(int studentId);
}
