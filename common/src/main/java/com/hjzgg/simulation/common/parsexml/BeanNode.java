package com.hjzgg.simulation.common.parsexml;

/**
 * Created by hujunzheng on 2017/7/7.
 */
public class BeanNode {
    private String beanName;
    private Class<?> interfaceCls;
    private String url;

    public Class<?> getInterfaceCls() {
        return interfaceCls;
    }

    public void setInterfaceCls(Class<?> interfaceCls) {
        this.interfaceCls = interfaceCls;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }
}
