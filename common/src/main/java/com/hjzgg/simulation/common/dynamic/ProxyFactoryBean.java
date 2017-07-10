package com.hjzgg.simulation.common.dynamic;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * Created by hujunzheng on 2017/7/7.
 */
public class ProxyFactoryBean implements FactoryBean, InitializingBean {

    private String url;
    private Class<?> interfaceCls;
    private String beanName;

    private Object proxy;

    @Override
    public Object getObject() throws Exception {
        return this.proxy;
    }

    @Override
    public Class<?> getObjectType() {
        return proxy.getClass();
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    public Class<?> getInterfaceCls() {
        return interfaceCls;
    }

    public void setInterfaceCls(Class<?> interfaceCls) {
        this.interfaceCls = interfaceCls;
    }

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.proxy = JdkDynamicProxy.createJDKProxy(this);
    }
}
