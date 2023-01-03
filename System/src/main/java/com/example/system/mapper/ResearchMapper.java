package com.example.system.mapper;

import com.example.system.entity.Research;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Lee
 * @since 2022-11-26
 */
public interface ResearchMapper extends BaseMapper<Research> {

    @Select("select researchName from re_te where teacherId=#{teacherId}")
    List<String> findResearchNameViaTeacherId(int teacherId);

    @Select("select * from research where researchName=#{researchName}")
    Research findResearchesViaResearchName(String researchName);

    @Select("select studentId from re_st where researchName=#{researchName} and add=#{i}")
    List<Integer> findApply(String researchName, int i);
}
