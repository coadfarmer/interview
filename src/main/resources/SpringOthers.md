## SpringBoot

### SpringBoot有哪些优点？

SpringBoot是Spring开源组织下的子项目，是Spring组件的一站式解决方案，它是一个脚手架，具有以下优点：

1. 容易上手，简化开发
2. 开箱即用，远离繁琐的xml配置
3. 提供了一系列大型项目通用的非业务性功能，例如：内嵌服务器、安全管理、运行数据监控、运行状况检查和外部化配置等。
5. 避免了大量的Maven导入和各版本冲突

### SpringBoot自动配置的原理

SpringBoot自动装配依赖于核心注解@SpringBootApplication，它包括三个注解

- @EnableAutoConfiguration：启动SpringBoot的自动配置机制
- @Configuration：通过spring.factories，在上下文中注册额外的bean
- @ComponentScan：扫描被@Cpmponent注解的bean，注解默认会扫描该类所在包下的所有类

### SpringBoot如何解决跨域问题

在传统的SSM项目中，我们使用CORS解决跨域问题，在SpringBoot中，可以通过实现WebMvcConfigurer接口重写addCorsMappings方法解决跨域问题。

```java
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowCredentials(true)
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .maxAge(3600);
    }

}
```

### SpringBoot启动流程

```java
public ConfigurableApplicationContext run(String...args){
        //计时器
        long startTime=System.nanoTime();
        //创建引导上下文
        DefaultBootstrapContext bootstrapContext=createBootstrapContext();
        ConfigurableApplicationContext context=null;
        //让当前应用进入headless模式
        configureHeadlessProperty();
        //从spring.factories获取监听器——EventPublishingRunListener
        SpringApplicationRunListeners listeners=getRunListeners(args);
        //遍历所有SpringApplicationRunListeners调用starting，相当于通知所有感兴趣系统正在启动的人，项目正在starting
        listeners.starting(bootstrapContext,this.mainApplicationClass);
        try{
        //保存命令参数
        ApplicationArguments applicationArguments=new DefaultApplicationArguments(args);
        //准备环境，返回或创建基础环境信息，一般是servlet
        ConfigurableEnvironment environment=prepareEnvironment(listeners,bootstrapContext,applicationArguments);

        configureIgnoreBeanInfo(environment);
        //打印Banner
        Banner printedBanner=printBanner(environment);
        //创建ApplicationContext，根据应用类型（Servlet）得到AnnotationConfigServletWebServerApplicationContext
        context=createApplicationContext();

        context.setApplicationStartup(this.applicationStartup);
        //准备ApplicationContext的基本信息，初始化和扩展ApplicationContext
        prepareContext(bootstrapContext,context,environment,listeners,applicationArguments,printedBanner);
        //刷新ApplicationContext——Spring容器refresh
        refreshContext(context);
        afterRefresh(context,applicationArguments);
        //计时结束
        Duration timeTakenToStartup=Duration.ofNanos(System.nanoTime()-startTime);
        if(this.logStartupInfo){
        new StartupInfoLogger(this.mainApplicationClass).logStarted(getApplicationLog(),timeTakenToStartup);
        }
        listeners.started(context,timeTakenToStartup);
        callRunners(context,applicationArguments);
        }
        catch(Throwable ex){
        handleRunFailure(context,ex,listeners);
        throw new IllegalStateException(ex);
        }
        try{
        Duration timeTakenToReady=Duration.ofNanos(System.nanoTime()-startTime);
        listeners.ready(context,timeTakenToReady);
        }
        catch(Throwable ex){
        handleRunFailure(context,ex,null);
        throw new IllegalStateException(ex);
        }
        return context;
        }
```

1. 创建SpringApplication
2. 运行SpringApplication
   1. 计时器
   2. 创建引导上下文
   3. 从spring.factories获取监听器——EventPublishingRunListener并紧接着就发布一个启动事件
   4. 准备环境，返回或创建基础环境信息，一般是servlet
   5. 打印Banner
   6. 创建ApplicationContext，这里实际得到AnnotationConfigServletWebServerApplicationContext
   7. 准备ApplicationContext的基本信息，初始化和扩展ApplicationContext
   8. 刷新ApplicationContext——Spring容器refresh
   9. 计时结束

## Spring安全

### RBAC 模型了解吗？

RBAC 即基于角色的权限访问控制（Role-Based Access Control）。这是一种通过角色关联权限，角色同时又关联用户的授权的方式。

![RBAC](https://guide-blog-images.oss-cn-shenzhen.aliyuncs.com/github/javaguide/booksRBAC.png)

### 什么是 Cookie ? Cookie 的作用是什么?

`Cookies` 是某些网站为了辨别用户身份而储存在用户本地终端上的数据。

### 什么是 Token?什么是 JWT?

JWT 本质上就一段签名的 JSON 格式的数据。由于它是带有签名的，因此接收者便可以验证它的真实性。

JWT由三部分组成：

1. Header：描述JWT的元数据，定义了生成签名的算法以及Token的类型
2. Payload：用来存放实际需要传递的数据
3. Signature（签名）：服务器通过Payload、Header和一个密钥（secret）使用Header里面指定的签名算法生成（默认是SHA256）。

### 如何基于Token进行身份验证？

1. 用户进行用户名密码登录
2. 服务端返回一个带签名的Token
3. 以后每次客户端发送请求都在Header中携带JWT
4. 服务端检查JWT并从中获取用户相关信息

### JWT优缺点

优点

1. 减轻服务器压力
2. 避免CSRF（跨站请求伪造）攻击
3. 适合移动端应用
4. 便于跨域单点登录

缺点

1. 有效期内无法删除token

2. token续签问题

### 什么是SSO

SSO(Single Sign On)即单点登录说的是用户登陆多个子系统的其中一个就有权访问与其相关的其他系统。

### 什么是OAuth2.0

OAuth2.0是为了解决用户授权第三方应用而产生的

![OAuth运行流程](https://www.ruanyifeng.com/blogimg/asset/2014/bg2014051203.png)

（A)用户打开客户端以后，客户端要求用户给予授权。

（B）用户同意给予客户端授权。

（C）客户端使用上一步获得的授权，向认证服务器申请令牌（Token）。

（D）认证服务器对客户端进行认证以后，确认无误，同意发放令牌（Token）。

（E）客户端使用令牌（Token），向资源服务器申请获取资源。

（F）资源服务器确认令牌（Token）无误，同意向客户端开放资源。

### SpringSecurity

是一种基于 Spring AOP 和 Servlet 过滤器的安全框架

认证过程：

1. 用户使用用户名和密码进行登录
2. Spring Security 将获取到的用户名和密码封装成一个实现了 Authentication 接口的 UsernamePasswordAuthenticationToken
3. 将上述产生的 token 对象传递给 AuthenticationManager 进行登录认证
4. AuthenticationManager 认证成功后将会返回一个封装了用户权限等信息的 Authentication 对象
5. 通过调用 SecurityContextHolder.getContext().setAuthentication(...) 将 AuthenticationManager 返回的 Authentication 对象赋予给当前的
   SecurityContext

我们也可以实现UserDetailsService接口从数据库查询认证

### 过滤器与拦截器的区别

1. 实现原理不同，过滤器是基于函数回调的，拦截器是基于动态代理的

2. 使用范围不同，过滤器是Servlet定义的，只能在Web程序中使用；而拦截器是一个Spring组件，只要引入了就可以使用

3. 触发时机不同，Filter是在进入Servlet前进行预处理，拦截器是进入Controller前进行预处理

   ![在这里插入图片描述](https://img-blog.csdnimg.cn/20200602173814901.png?#pic_center)

4. 拦截的请求范围不同，拦截器只拦截Controller中的方法

5. 注入Bean情况不同，拦截器加载时间在SpringContext之前

6. 控制执行顺序不同，Filter通过@Order注解控制执行顺序，拦截器默认的执行顺序，就是它的注册顺序，也可以通过`Order`手动设置控制，值越小越先执行。





