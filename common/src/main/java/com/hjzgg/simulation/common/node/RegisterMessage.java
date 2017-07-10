package com.hjzgg.simulation.common.node;

import java.util.List;

/**
 * Created by hujunzheng on 2017/7/9.
 */
public class RegisterMessage {
    private String providerUrl;
    private List<InterfaceMessage> interfaceMessageList;

    public String getProviderUrl() {
        return providerUrl;
    }

    public void setProviderUrl(String providerUrl) {
        this.providerUrl = providerUrl;
    }

    public List<InterfaceMessage> getInterfaceMessageList() {
        return interfaceMessageList;
    }

    public void setInterfaceMessageList(List<InterfaceMessage> interfaceMessageList) {
        this.interfaceMessageList = interfaceMessageList;
    }
}
