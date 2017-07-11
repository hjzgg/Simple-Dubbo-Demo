package com.hjzgg.simulation.common.register;

import com.hjzgg.simulation.common.dynamic.ProxyFactoryBean;
import com.hjzgg.simulation.common.parsexml.BeanNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.core.type.AnnotationMetadata;

import java.util.List;

/**
 * Created by hujunzheng on 2017/7/7.
 */
public class SpringBeanRegister {
    private static final Logger logger = LoggerFactory.getLogger(SpringBeanRegister.class);
    public static void registerBean(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry, List<BeanNode> beanNodes) {
        for(BeanNode beanNode : beanNodes) {
            String beanName = beanNode.getBeanName();
            Class<?> interfaceCls = beanNode.getInterfaceCls();
            if (!registry.containsBeanDefinition(beanName) && interfaceCls.isInterface()) {
                GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
                beanDefinition.getPropertyValues().addPropertyValue("interfaceCls", interfaceCls);
                beanDefinition.getPropertyValues().addPropertyValue("beanName", beanName);
                beanDefinition.getPropertyValues().addPropertyValue("url", beanNode.getUrl());
                beanDefinition.setBeanClass(ProxyFactoryBean.class);
                beanDefinition.setSynthetic(true);
                registry.registerBeanDefinition(beanName, beanDefinition);
                logger.debug("服务" + beanName + "对应的类型" + interfaceCls.getTypeName() + "注册成功...");
            }
        }
    }
}
