package com.springboot.springtest.dubbo.dubboImpl;

import com.alibaba.dubbo.config.annotation.Service;
import com.springboot.springtest.bean.CollStage;
import com.springboot.springtest.common.ApiResponse;
import com.springboot.springtest.common.BusinessException;
import com.springboot.springtest.dao.CollStageMapper;
import com.springboot.springtest.dubbo.DubboDemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

@Service
public class DubboDemoServiceImpl implements DubboDemoService {
    @Autowired
    private CollStageMapper collStageMapper;
    @Override
    public ApiResponse getStageById(String id) throws BusinessException{
        if (StringUtils.isEmpty(id)) {
            return ApiResponse.error("id is null");
        }
        CollStage collStage = collStageMapper.selectById(id);
        return ApiResponse.success(collStage);
    }
}
