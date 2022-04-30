package DynamicProxy.cglibProxy;

/**
 * @Author: xjjiang
 * @Data: 2022/4/30 19:17
 * @Description:
 */
public class TestCglib {


    public static void main(String[] args) {
        AliSmsService aliSmsService = (AliSmsService) CglibProxyFactory.getProxy(AliSmsService.class);
        aliSmsService.send("java");
    }

}
