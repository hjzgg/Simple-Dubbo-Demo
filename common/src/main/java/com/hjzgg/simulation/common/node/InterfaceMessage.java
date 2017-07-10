package com.hjzgg.simulation.common.node;

import java.io.Serializable;

/**
 * Created by hujunzheng on 2017/7/9.
 */
public class InterfaceMessage implements Serializable {
    private String beanName;
    private String interfacType;
    private String providerUrl;

    public String getProviderUrl() {
        return providerUrl;
    }

    public void setProviderUrl(String providerUrl) {
        this.providerUrl = providerUrl;
    }

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public String getInterfacType() {
        return interfacType;
    }

    public void setInterfacType(String interfacType) {
        this.interfacType = interfacType;
    }
}
