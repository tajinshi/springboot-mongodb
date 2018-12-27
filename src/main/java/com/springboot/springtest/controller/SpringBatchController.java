package com.springboot.springtest.controller;


import io.swagger.annotations.Api;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;

@RestController
@Api(description = "springbatch批处理框架")
public class SpringBatchController {
    @Autowired
    private JobLauncher jobLauncher;
    @Autowired
    private Job importJob;
    public JobParameters jobParameters;

    @PostMapping("/imp")
    public String imp ()throws Exception{
        String fileName = "person";
        String path = fileName+".csv";
        SimpleDateFormat df = new SimpleDateFormat("YYYY-MM-DD HH:MM:SS");
        String date =df.format(System.currentTimeMillis());
        jobParameters = new JobParametersBuilder()
                .addString("time",date)
                .addString("input.file.name",path)
                .toJobParameters();
        jobLauncher.run(importJob, jobParameters);
        return "ok";
    }

}
