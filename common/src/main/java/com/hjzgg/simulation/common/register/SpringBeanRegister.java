package com.hjzgg.simulation.common.register;

import com.hjzgg.simulation.common.dynamic.ProxyFactoryBean;
import com.hjzgg.simulation.common.parsexml.BeanNode;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.core.type.AnnotationMetadata;

import java.util.List;

/**
 * Created by hujunzheng on 2017/7/7.
 */
public class SpringBeanRegister {
    public static void registerBean(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry, List<BeanNode> beanNodes) {
        for(BeanNode beanNode : beanNodes) {
            String beanName = beanNode.getBeanName();
            Class<?> interfaceCls = beanNode.getInterfaceCls();
            if (!registry.containsBeanDefinition(beanName) && interfaceCls.isInterface()) {
                GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
                beanDefinition.setAttribute("interfaceCls", interfaceCls);
                beanDefinition.setAttribute("beanName", beanName);
                beanDefinition.setAttribute("url", beanNode.getUrl());
                beanDefinition.setBeanClass(ProxyFactoryBean.class);
                beanDefinition.setSynthetic(true);
                registry.registerBeanDefinition(beanName, beanDefinition);
            }
        }
    }
}
