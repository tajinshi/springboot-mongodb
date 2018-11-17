package com.springboot.springtest.dubbo;

import com.springboot.springtest.common.ApiResponse;

public interface DubboDemoService {
    ApiResponse getStageById(String id) ;
}
