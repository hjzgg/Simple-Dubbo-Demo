package com.hjzgg.simulation.consumer.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hjzgg.simulation.common.dynamic.JdkDynamicProxy;
import com.hjzgg.simulation.common.node.InterfaceMessage;
import com.hjzgg.simulation.common.parsexml.BeanNode;
import com.hjzgg.simulation.common.parsexml.ParseServiceXML;
import com.hjzgg.simulation.common.register.SpringBeanRegister;
import com.hjzgg.simulation.common.response.ReturnCode;
import com.hjzgg.simulation.common.utils.RestTemplateUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.http.MediaType;

import java.util.Iterator;
import java.util.List;

public class Registrar implements ImportBeanDefinitionRegistrar, EnvironmentAware{

    private Logger logger = LoggerFactory.getLogger(Registrar.class);

    private String servicesXmlPath;
    private String serviceContainsPath;

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        List<BeanNode> beanNodes = ParseServiceXML.getConsumerServices(servicesXmlPath);
//      判断注册中心 是否注册了这个服务了
        for(Iterator<BeanNode> it = beanNodes.iterator(); it.hasNext(); ) {
            BeanNode beanNode = it.next();
            InterfaceMessage interfaceMessage = new InterfaceMessage();
            interfaceMessage.setProviderUrl(beanNode.getUrl());
            interfaceMessage.setInterfacType(beanNode.getInterfaceCls().getTypeName());
            interfaceMessage.setBeanName(beanNode.getBeanName());

            try {
                String result = RestTemplateUtils.post(this.serviceContainsPath, (JSONObject) JSON.toJSON(interfaceMessage), MediaType.APPLICATION_JSON_UTF8);
                JSONObject retJson = JSON.parseObject(result);
                if (retJson.getInteger("code") == ReturnCode.FAILURE.getValue()) {
                    it.remove();

                    logger.error(interfaceMessage.getBeanName() + "对应类型" + interfaceMessage.getInterfacType() + "的服务在" +
                    interfaceMessage.getProviderUrl() + "上没有注册");
                }
            } catch (Exception e) {
                e.printStackTrace();
                logger.error("服务" + interfaceMessage.getBeanName() + "对应类型" + interfaceMessage.getInterfacType() + "查找失败..." + e.getMessage());
            }
        }
        SpringBeanRegister.registerBean(importingClassMetadata, registry, beanNodes);
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.servicesXmlPath = environment.getProperty("service.xml.path");
        this.serviceContainsPath = environment.getProperty("service.contains.url");
        String serviceInvokePath = environment.getProperty("service.invoke.url");

        assert(StringUtils.isNotEmpty(serviceContainsPath) && StringUtils.isNotEmpty(this.servicesXmlPath)
            && StringUtils.isNotEmpty(serviceInvokePath));

        JdkDynamicProxy.setServerProviderInvokeUrl(serviceInvokePath);
    }
}