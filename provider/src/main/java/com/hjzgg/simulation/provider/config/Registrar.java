package com.hjzgg.simulation.provider.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hjzgg.simulation.common.node.InterfaceMessage;
import com.hjzgg.simulation.common.node.RegisterMessage;
import com.hjzgg.simulation.common.parsexml.BeanNode;
import com.hjzgg.simulation.common.parsexml.ParseServiceXML;
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
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

public class Registrar implements ImportBeanDefinitionRegistrar, EnvironmentAware {
    private static Logger logger = LoggerFactory.getLogger(Registrar.class);

    private String servicesXmlPath;
    private String serviceProviderPath;
    private String serviceRegisterPath;

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        List<BeanNode> beanNodes = ParseServiceXML.getProviderServices(servicesXmlPath);
        List<InterfaceMessage> list = new ArrayList<>();
        for(BeanNode beanNode : beanNodes) {
            if(!registry.containsBeanDefinition(beanNode.getBeanName())) {
                logger.error("接口" + beanNode.getBeanName() + " " + beanNode.getInterfaceCls().getTypeName() + " 没有对应的实现类");
            } else {
                InterfaceMessage interfaceMessage = new InterfaceMessage();
                interfaceMessage.setBeanName(beanNode.getBeanName());
                interfaceMessage.setInterfacType(beanNode.getInterfaceCls().getTypeName());
                list.add(interfaceMessage);
            }
        }
        if(!CollectionUtils.isEmpty(list)) {
            RegisterMessage registerMessage = new RegisterMessage();
            registerMessage.setProviderUrl(this.serviceProviderPath);
            registerMessage.setInterfaceMessageList(list);
            try {
                String result = RestTemplateUtils.post(this.serviceRegisterPath, (JSONObject) JSON.toJSON(registerMessage), MediaType.APPLICATION_JSON_UTF8);
                JSONObject retJson = JSONObject.parseObject(result);
                if(retJson.getInteger("code") == ReturnCode.SUCCESS.getValue()) {
                    logger.debug("服务注册成功...");
                } else {
                    logger.error("服务注册失败..." + retJson.getString("msg"));
                }
            } catch (Exception e) {
                e.printStackTrace();
                logger.error("服务注册失败..." + e.getMessage());
            }
        }
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.servicesXmlPath = environment.getProperty("service.xml.path");
        this.serviceProviderPath = environment.getProperty("service.provider.path");
        this.serviceRegisterPath = environment.getProperty("service.register.path");

        assert(StringUtils.isNotEmpty(this.serviceProviderPath) && StringUtils.isNotEmpty(serviceRegisterPath) &&
        StringUtils.isNotEmpty(this.servicesXmlPath));
    }
}