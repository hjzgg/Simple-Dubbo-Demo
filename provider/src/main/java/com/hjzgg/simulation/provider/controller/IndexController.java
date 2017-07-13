package com.hjzgg.simulation.provider.controller;

import com.hjzgg.simulation.common.node.ServiceMessage;
import com.hjzgg.simulation.common.response.SimpleResponse;
import com.hjzgg.simulation.common.utils.ContextUtils;
import com.hjzgg.simulation.common.utils.SerializeUtil;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hujunzheng on 2017/7/8.
 */
@RestController
@RequestMapping("index")
public class IndexController {

    @RequestMapping(value = "invoke", method = RequestMethod.POST)
    public Object invoke(@RequestParam String serviceMessageBody) {
        try {
            ServiceMessage serviceMessage = (ServiceMessage) SerializeUtil.unserialize(Hex.decodeHex(serviceMessageBody.toCharArray()));
            Object bean = null;
            if((bean = ContextUtils.getBean(serviceMessage.getBeanName(), serviceMessage.getRequireType())) != null) {
                List<Class<?>> classList = new ArrayList<>();
                if(serviceMessage.getArgs() != null) {
                    for (Object obj : serviceMessage.getArgs()) {
                        classList.add(obj.getClass());
                    }
                }
                Method method = ReflectionUtils.findMethod(bean.getClass(), serviceMessage.getMethodName(), classList.toArray(new Class<?>[0]));
                if(method != null) {
                    return method.invoke(bean, serviceMessage.getArgs());
                } else {
                    return SimpleResponse.error("服务" + serviceMessage.getRequireType().getTypeName() + "中没有对应参数"
                            + ToStringBuilder.reflectionToString(classList) + "的" + serviceMessage.getMethodName() + "方法");
                }
            } else {
                return SimpleResponse.error("没有名称为" + serviceMessage.getBeanName() + "且类型为"
                        + serviceMessage.getRequireType().getTypeName() + "对应的bean");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return SimpleResponse.error(e.getMessage());
        }
    }

}
