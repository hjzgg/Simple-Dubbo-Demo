package com.hjzgg.simulation.register.controller;

import com.hjzgg.simulation.common.cache.RedisCacheTemplate;
import com.hjzgg.simulation.common.node.InterfaceMessage;
import com.hjzgg.simulation.common.node.RegisterMessage;
import com.hjzgg.simulation.common.response.SimpleResponse;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hujunzheng on 2017/7/9.
 */
@RestController
@RequestMapping("index")
public class IndexController {

    @Autowired
    private RedisCacheTemplate redisCacheTemplate;

    @RequestMapping(value = "register", method = RequestMethod.POST)
    public SimpleResponse register(@RequestBody RegisterMessage registerMessage) {
        try {
            Map<String, Object> map = new HashMap<>();
            for (InterfaceMessage interfaceMessage : registerMessage.getInterfaceMessageList()) {
                interfaceMessage.setProviderUrl(registerMessage.getProviderUrl());
                map.put(ToStringBuilder.reflectionToString(interfaceMessage, ToStringStyle.SHORT_PREFIX_STYLE), true);
            }
            redisCacheTemplate.batchPut(map);
            return SimpleResponse.success(map.size());
        } catch (Exception e) {
            e.printStackTrace();
            return SimpleResponse.error(e.getMessage());
        }
    }


    @RequestMapping(value = "contains", method = RequestMethod.POST)
    public SimpleResponse contains(@RequestBody InterfaceMessage interfaceMessage) {
        try {
            if(redisCacheTemplate.exist(ToStringBuilder.reflectionToString(interfaceMessage, ToStringStyle.SHORT_PREFIX_STYLE))) {
                return SimpleResponse.success(true);
            } else {
                return SimpleResponse.error(null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return SimpleResponse.error(e.getMessage());
        }
    }

    @RequestMapping(value = "test", method = {RequestMethod.GET, RequestMethod.POST})
    public SimpleResponse test(@RequestParam String providerUrl){
        return SimpleResponse.success(providerUrl);
    }
}
