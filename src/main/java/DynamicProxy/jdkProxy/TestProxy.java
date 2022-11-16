package DynamicProxy.jdkProxy;

import org.junit.Test;

/**
 * @Author: xjjiang
 * @Data: 2022/4/30 17:33
 * @Description:
 */
public class TestProxy {

    @Test
    public void test() {
        DynamicProxy.jdkProxy.SmsService smsService = (DynamicProxy.jdkProxy.SmsService) DynamicProxy.jdkProxy.JdkProxyFactory.getProxy(new DynamicProxy.jdkProxy.SmsServiceImpl());
        smsService.send("java");
    }

}
