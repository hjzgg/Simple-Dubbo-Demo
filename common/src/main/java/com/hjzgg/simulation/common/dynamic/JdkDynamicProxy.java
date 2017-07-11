package com.hjzgg.simulation.common.dynamic;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hjzgg.simulation.common.node.ServiceMessage;
import com.hjzgg.simulation.common.utils.RestTemplateUtils;
import com.hjzgg.simulation.common.utils.SerializeUtil;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.URLEncoder;
import java.nio.charset.Charset;

/**
 * Created by hujunzheng on 2017/7/7.
 */
public class JdkDynamicProxy implements InvocationHandler {

    private static final Logger logger = LoggerFactory.getLogger(JdkDynamicProxy.class);

    private static String SERVER_PROVIDER_INVOKE_URL = "http://localhost:8090/register-server/index/invoke";

    public JdkDynamicProxy(ProxyFactoryBean factoryBean) {
        this.bean = factoryBean;
    }

    private ProxyFactoryBean bean;

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        try {
            ServiceMessage serviceMessage = new ServiceMessage();
            serviceMessage.setArgs(args);
            serviceMessage.setBeanName(bean.getBeanName());
            serviceMessage.setMethodName(method.getName());
            serviceMessage.setRequireType(bean.getInterfaceCls());

            JSONObject params = new JSONObject();
            params.put("serviceMessageBody", Hex.encodeHexString(SerializeUtil.serialize(serviceMessage)));

            Class<?> returnType = method.getReturnType();
            return RestTemplateUtils.post(SERVER_PROVIDER_INVOKE_URL, params, MediaType.APPLICATION_FORM_URLENCODED, returnType);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("服务调用出错，" + e.getMessage());
            return null;
        }
    }


    public static Object createJDKProxy(ProxyFactoryBean factoryBean){
        Object proxy = Proxy.newProxyInstance(JdkDynamicProxy.class.getClassLoader(),
                new Class[]{factoryBean.getInterfaceCls()}, new JdkDynamicProxy(factoryBean));
        return proxy;
    }

    public static void setServerProviderInvokeUrl(String url) {
        SERVER_PROVIDER_INVOKE_URL = url;
    }
}
