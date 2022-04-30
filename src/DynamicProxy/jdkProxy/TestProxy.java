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
        SmsService smsService = (SmsService) JdkProxyFactory.getProxy(new SmsServiceImpl());
        smsService.send("java");
    }

}
