package com.springboot.springtest.service.impl;

import com.springboot.springtest.bean.CollStage;
import com.springboot.springtest.dao.CollStageMapper;
import com.springboot.springtest.service.CollStageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 催收-催收阶段表 服务实现类
 * </p>
 *
 * @author D.Yang
 * @since 2018-10-29
 */
@Service
public class CollStageServiceImpl implements CollStageService {

    @Autowired
    private CollStageMapper stageMapper;
    @Override
    public CollStage getStageById(String id) {
        return stageMapper.getStageById(id);
    }
}
