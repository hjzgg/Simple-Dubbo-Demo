package com.hjzgg.simulation.common.dynamic;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * Created by hujunzheng on 2017/7/7.
 */
public class ProxyFactoryBean implements FactoryBean {

    private String url;
    private Class<?> interfaceCls;
    private String beanName;

    private Object proxy;

    private Object newInstance() {
        if(this.isSingleton() && proxy != null) {
            return proxy;
        }
        synchronized (this) {
            Object target = JdkDynamicProxy.createJDKProxy(this);
            if(proxy == null) {
                proxy = target;
            }
            return target;
        }
    }

    @Override
    public Object getObject() throws Exception {
        return this.newInstance();
    }

    @Override
    public Class<?> getObjectType() {
        return interfaceCls;
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
}
