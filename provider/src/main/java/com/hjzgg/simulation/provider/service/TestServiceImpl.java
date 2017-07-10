package com.hjzgg.simulation.provider.service;

import com.alibaba.fastjson.JSONObject;
import com.hjzgg.simulation.api.ITestService;
import org.springframework.stereotype.Service;

/**
 * Created by hujunzheng on 2017/7/8.
 */
@Service("testService")
public class TestServiceImpl implements ITestService {
    @Override
    public JSONObject testService() {
        JSONObject result = new JSONObject();
        result.put("name", "hujunzheng");
        result.put("age", 25);
        return result;
    }
}
