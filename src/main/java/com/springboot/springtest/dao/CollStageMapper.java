package com.springboot.springtest.dao;

import com.springboot.springtest.bean.CollStage;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 催收-催收阶段表 Mapper 接口
 * </p>
 *
 * @author D.Yang
 * @since 2018-10-29
 */
public interface CollStageMapper extends BaseMapper<CollStage> {
    CollStage getStageById(@Param("id") String id);
}
