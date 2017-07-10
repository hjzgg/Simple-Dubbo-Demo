package com.hjzgg.simulation.consumer.controller;

import com.hjzgg.simulation.api.ITestService;
import com.hjzgg.simulation.common.response.SimpleResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by hujunzheng on 2017/7/9.
 */
@RestController
@RequestMapping("consumer")
public class TestController {

    @Autowired
    private ITestService testService;


    @RequestMapping("test")
    public SimpleResponse test() {
        try {
            return SimpleResponse.success(testService.testService());
        } catch (Exception e) {
            e.printStackTrace();
            return SimpleResponse.error(e.getMessage());
        }
    }
}
