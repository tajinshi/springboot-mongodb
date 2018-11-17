package com.springboot.springtest.dubbo;

import com.springboot.springtest.common.ApiResponse;
import com.springboot.springtest.common.BusinessException;

public interface DubboDemoService {

    ApiResponse getStageById(String id) throws BusinessException;
}
