package com.hjzgg.simulation.common.dynamic;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by hujunzheng on 2017/7/7.
 */
public class JdkDynamicProxy implements InvocationHandler {
    public JdkDynamicProxy(ProxyFactoryBean factoryBean) {
        this.factoryBean = factoryBean;
    }

    private ProxyFactoryBean factoryBean;

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        return null;
    }


    public static Object createJDKProxy(ProxyFactoryBean factoryBean){
        Object proxyArithmetic = Proxy.newProxyInstance(JdkDynamicProxy.class.getClassLoader(),
                new Class[]{factoryBean.getInterfaceCls()}, new JdkDynamicProxy(factoryBean));
        return proxyArithmetic;
    }
}
