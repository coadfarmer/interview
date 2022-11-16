package DynamicProxy.cglibProxy;

/**
 * @Author: xjjiang
 * @Data: 2022/4/30 19:17
 * @Description:
 */
public class TestCglib {


    public static void main(String[] args) {
        DynamicProxy.cglibProxy.AliSmsService aliSmsService = (DynamicProxy.cglibProxy.AliSmsService) DynamicProxy.cglibProxy.CglibProxyFactory.getProxy(DynamicProxy.cglibProxy.AliSmsService.class);
        aliSmsService.send("java");
    }

}
