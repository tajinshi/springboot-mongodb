package com.springboot.springtest.service;

import com.springboot.springtest.bean.CollStage;

/**
 * <p>
 * 催收-催收阶段表 服务类
 * </p>
 *
 * @author D.Yang
 * @since 2018-10-29
 */
public interface CollStageService{
    CollStage getStageById(String id);
}
