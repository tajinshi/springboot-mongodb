package com.springboot.springtest.controller;

import com.springboot.springtest.common.ApiResponse;
import com.springboot.springtest.common.lock.RedisLockUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;

import java.util.UUID;

@RestController
@Api(description = "redis分布式锁的工具类")
@RequestMapping(value = "api")
public class RedisLockController {

    @PostMapping("lock")
    @ApiOperation(value = "加锁", response = ApiResponse.class)
    public String lock() {
        String requestId = UUID.randomUUID().toString();
        Jedis jedis = new Jedis("47.104.177.229",6389);
        jedis.auth("aishangADMIN.123");
        boolean job = RedisLockUtil.tryGetDistributedLock(jedis, "job", requestId, 10000);
        System.out.println("boolean:"+job);
        return requestId+" ------ " +job;
    }

    @PostMapping("unlock")
    @ApiOperation(value = "解锁", response = ApiResponse.class)
    public String unlock(String requestId) {
        Jedis jedis = new Jedis("47.104.177.229",6389);
        jedis.auth("aishangADMIN.123");
        boolean job = RedisLockUtil.releaseDistributedLock(jedis, "job", requestId);
        System.out.println("boolean:"+job);
        return requestId;
    }
}
