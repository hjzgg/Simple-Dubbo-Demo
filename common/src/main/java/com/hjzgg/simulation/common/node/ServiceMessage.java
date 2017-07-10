package com.hjzgg.simulation.common.node;

import java.io.Serializable;

/**
 * Created by hujunzheng on 2017/7/9.
 */
public class ServiceMessage implements Serializable {
    private String beanName;
    private Class<?> requireType;
    private String methodName;
    private Object[] args;

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public Class<?> getRequireType() {
        return requireType;
    }

    public void setRequireType(Class<?> requireType) {
        this.requireType = requireType;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }
}
