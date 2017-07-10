package com.hjzgg.simulation.common.parsexml;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by hujunzheng on 2017/7/7.
 */
public class ParseServiceXML {

    private static Logger logger = LoggerFactory.getLogger(ParseServiceXML.class);

    public static List<BeanNode> getProviderServices(String xmlPath) {
        return parseAndGetBeanNodes(xmlPath, Service.PROVIDER);
    }

    public static List<BeanNode> getConsumerServices(String xmlPath) {
        return parseAndGetBeanNodes(xmlPath, Service.CONSUMER);
    }

    private static List<BeanNode> parseAndGetBeanNodes(String xmlPath, Service service) {
        List<BeanNode> list = new ArrayList<>();
        try {
            File cfgFile = ResourceUtils.getFile(xmlPath);
            SAXReader reader = new SAXReader();
            Document document = reader.read(cfgFile);
            Element root = document.getRootElement();

            for(Iterator<Element> it = root.elementIterator(); it.hasNext();) {
                Element element = it.next();
                if("service".equals(element.getName())) {
                    String beanName = null, cls = null, url = null;
                    switch (service) {
                        case PROVIDER:
                            beanName = element.attributeValue("id");
                            break;
                        case CONSUMER:
                            beanName = element.attributeValue("ref");
                            url = element.attributeValue("url");
                            if (StringUtils.isEmpty(url)) {
                                throw new RuntimeException("消费者 url属性为空");
                            }
                            break;
                        default:
                            break;
                    }
                    cls = element.attributeValue("interface");

                    if(StringUtils.isNotEmpty(beanName) && StringUtils.isNotEmpty(cls)) {
                        try {
                            Class<?> interfaceCls = Class.forName(cls);
                            BeanNode beanNode = new BeanNode();
                            beanNode.setBeanName(beanName);
                            beanNode.setUrl(url);
                            beanNode.setInterfaceCls(interfaceCls);
                            list.add(beanNode);
                        } catch (ClassNotFoundException e) {
                            logger.error(service.getValue() + ",不存在服务类->" + cls);
                            e.printStackTrace();
                        }
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return list;
    }

}

enum Service {
    PROVIDER("register"),
    CONSUMER("register");

    private String value;

    Service(String value) {
        this.value = value;
    }
    public String getValue() {
        return this.value;
    }
}