package com.hjzgg.simulation.provider.controller;

import com.hjzgg.simulation.common.node.ServiceMessage;
import com.hjzgg.simulation.common.response.SimpleResponse;
import com.hjzgg.simulation.common.utils.ContextUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.util.ObjectUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
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

    @RequestMapping("invoke")
    public SimpleResponse invoke(@RequestBody ServiceMessage serviceMessage) {
        try {
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
                    return SimpleResponse.success(method.invoke(bean, serviceMessage.getArgs()));
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
