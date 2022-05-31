## SpringBoot

### SpringBoot有哪些优点？

SpringBoot是Spring开源组织下的子项目，是Spring组件的一站式解决方案，它是一个脚手架，具有以下优点：

1. 容易上手，提升开发效率
2. 开箱即用，远离繁琐的配置
3. 提供了一系列大型项目通用的非业务性功能，例如：内嵌服务器、安全管理、运行数据监控、运行状况检查和外部化配置等。
4. 没有代码生成，不需要xml配置
5. 避免了大量的Maven导入和各版本冲突

### SpringBoot自动配置的原理

SpringBoot自动装配依赖于核心注解@SpringBootApplication，它包括三个注解

- @EnableAutoConfiguration：启动SpringBoot的自动配置机制
- @Configuration：通过spring.factories，在上下文中注册额外的bean或导入其他配置类
- @ComponentScan：扫描被@Cpmponent注解的bean，注解默认会扫描该类所在包下的所有类

### SpringBoot如何解决跨域问题

在传统的SSM项目中，我们使用CORS解决跨域问题，在SpringBoot中，可以通过实现WebMvcConfigurer接口然后重写addCorsMappings方法解决跨域问题。

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
// SpringApplication.java

public ConfigurableApplicationContext run(String... args) {
    // new 一个StopWatch用于统计run启动过程花了多少时间
    StopWatch stopWatch = new StopWatch();
    // 开始计时
    stopWatch.start();
    ConfigurableApplicationContext context = null;
    // exceptionReporters集合用来存储异常报告器，用来报告SpringBoot启动过程的异常
    Collection<SpringBootExceptionReporter> exceptionReporters = new ArrayList<>();
    // 配置headless属性，即“java.awt.headless”属性，默认为ture
    // 其实是想设置该应用程序,即使没有检测到显示器,也允许其启动.对于服务器来说,是不需要显示器的,所以要这样设置.
    configureHeadlessProperty();
    // 【1】从spring.factories配置文件中加载到EventPublishingRunListener对象并赋值给SpringApplicationRunListeners
    // EventPublishingRunListener对象主要用来发射SpringBoot启动过程中内置的一些生命周期事件，标志每个不同启动阶段
    SpringApplicationRunListeners listeners = getRunListeners(args);
    // 启动SpringApplicationRunListener的监听，表示SpringApplication开始启动。
    // 》》》》》发射【ApplicationStartingEvent】事件
    listeners.starting();
    try {
        // 创建ApplicationArguments对象，封装了args参数
        ApplicationArguments applicationArguments = new DefaultApplicationArguments(
                args);
        // 【2】准备环境变量，包括系统变量，环境变量，命令行参数，默认变量，servlet相关配置变量，随机值，
        // JNDI属性值，以及配置文件（比如application.properties）等，注意这些环境变量是有优先级的
        // 》》》》》发射【ApplicationEnvironmentPreparedEvent】事件
        ConfigurableEnvironment environment = prepareEnvironment(listeners,
                applicationArguments);
        // 配置spring.beaninfo.ignore属性，默认为true，即跳过搜索BeanInfo classes.
        configureIgnoreBeanInfo(environment);
        // 【3】控制台打印SpringBoot的bannner标志
        Banner printedBanner = printBanner(environment);
        // 【4】根据不同类型创建不同类型的spring applicationcontext容器
        // 因为这里是servlet环境，所以创建的是AnnotationConfigServletWebServerApplicationContext容器对象
        context = createApplicationContext();
        // 【5】从spring.factories配置文件中加载异常报告期实例，这里加载的是FailureAnalyzers
        // 注意FailureAnalyzers的构造器要传入ConfigurableApplicationContext，因为要从context中获取beanFactory和environment
        exceptionReporters = getSpringFactoriesInstances(
                SpringBootExceptionReporter.class,
                new Class[] { ConfigurableApplicationContext.class }, context); // ConfigurableApplicationContext是AnnotationConfigServletWebServerApplicationContext的父接口
        // 【6】为刚创建的AnnotationConfigServletWebServerApplicationContext容器对象做一些初始化工作，准备一些容器属性值等
        // 1）为AnnotationConfigServletWebServerApplicationContext的属性AnnotatedBeanDefinitionReader和ClassPathBeanDefinitionScanner设置environgment属性
        // 2）根据情况对ApplicationContext应用一些相关的后置处理，比如设置resourceLoader属性等
        // 3）在容器刷新前调用各个ApplicationContextInitializer的初始化方法，ApplicationContextInitializer是在构建SpringApplication对象时从spring.factories中加载的
        // 4）》》》》》发射【ApplicationContextInitializedEvent】事件，标志context容器被创建且已准备好
        // 5）从context容器中获取beanFactory，并向beanFactory中注册一些单例bean，比如applicationArguments，printedBanner
        // 6）TODO 加载bean到application context，注意这里只是加载了部分bean比如mainApplication这个bean，大部分bean应该是在AbstractApplicationContext.refresh方法中被加载？这里留个疑问先
        // 7）》》》》》发射【ApplicationPreparedEvent】事件，标志Context容器已经准备完成
        prepareContext(context, environment, listeners, applicationArguments,
                printedBanner);
        // 【7】刷新容器，这一步至关重要，以后会在分析Spring源码时详细分析，主要做了以下工作：
        // 1）在context刷新前做一些准备工作，比如初始化一些属性设置，属性合法性校验和保存容器中的一些早期事件等；
        // 2）让子类刷新其内部bean factory,注意SpringBoot和Spring启动的情况执行逻辑不一样
        // 3）对bean factory进行配置，比如配置bean factory的类加载器，后置处理器等
        // 4）完成bean factory的准备工作后，此时执行一些后置处理逻辑，子类通过重写这个方法来在BeanFactory创建并预准备完成以后做进一步的设置
        // 在这一步，所有的bean definitions将会被加载，但此时bean还不会被实例化
        // 5）执行BeanFactoryPostProcessor的方法即调用bean factory的后置处理器：
        // BeanDefinitionRegistryPostProcessor（触发时机：bean定义注册之前）和BeanFactoryPostProcessor（触发时机：bean定义注册之后bean实例化之前）
        // 6）注册bean的后置处理器BeanPostProcessor，注意不同接口类型的BeanPostProcessor；在Bean创建前后的执行时机是不一样的
        // 7）初始化国际化MessageSource相关的组件，比如消息绑定，消息解析等
        // 8）初始化事件广播器，如果bean factory没有包含事件广播器，那么new一个SimpleApplicationEventMulticaster广播器对象并注册到bean factory中
        // 9）AbstractApplicationContext定义了一个模板方法onRefresh，留给子类覆写，比如ServletWebServerApplicationContext覆写了该方法来创建内嵌的tomcat容器
        // 10）注册实现了ApplicationListener接口的监听器，之前已经有了事件广播器，此时就可以派发一些early application events
        // 11）完成容器bean factory的初始化，并初始化所有剩余的单例bean。这一步非常重要，一些bean postprocessor会在这里调用。
        // 12）完成容器的刷新工作，并且调用生命周期处理器的onRefresh()方法，并且发布ContextRefreshedEvent事件
        refreshContext(context);
        // 【8】执行刷新容器后的后置处理逻辑，注意这里为空方法
        afterRefresh(context, applicationArguments);
        // 停止stopWatch计时
        stopWatch.stop();
        // 打印日志
        if (this.logStartupInfo) {
            new StartupInfoLogger(this.mainApplicationClass)
                    .logStarted(getApplicationLog(), stopWatch);
        }
        // 》》》》》发射【ApplicationStartedEvent】事件，标志spring容器已经刷新，此时所有的bean实例都已经加载完毕
        listeners.started(context);
        // 【9】调用ApplicationRunner和CommandLineRunner的run方法，实现spring容器启动后需要做的一些东西比如加载一些业务数据等
        callRunners(context, applicationArguments);
    }
    // 【10】若启动过程中抛出异常，此时用FailureAnalyzers来报告异常
    // 并》》》》》发射【ApplicationFailedEvent】事件，标志SpringBoot启动失败
    catch (Throwable ex) {
        handleRunFailure(context, ex, exceptionReporters, listeners);
        throw new IllegalStateException(ex);
    }

    try {
        // 》》》》》发射【ApplicationReadyEvent】事件，标志SpringApplication已经正在运行即已经成功启动，可以接收服务请求了。
        listeners.running(context);
    }
    // 若出现异常，此时仅仅报告异常，而不会发射任何事件
    catch (Throwable ex) {
        handleRunFailure(context, ex, exceptionReporters, null);
        throw new IllegalStateException(ex);
    }
    // 【11】最终返回容器
    return context;
}
```

1.从spring.factories配置文件中加载EventPublishingListen对象，该对象拥有SimpleApplicationMulticaster属性，即在SpringBoot启动过程的不同阶段用来发射内置的生命周期事件；

2. 准备环境变量，包括系统变量，环境变量，命令行参数，默认变量，servlet相关配置变量，配置文件等等；
3. 控制台答应SpringBoot标志；
4. 根据不同类型环境**创建**不同类型的**applicationcontext容器**，例如servlet、reactive；
5. 从spring.factories配置文件中加载异常报告实例，这里加载的是FailureAnalyzers； //这一步在springboot2.6中放到了catch方法里
6. 为刚创建的容器对象做一些初始化工作，扫描注解类就是在这一步完成的；
7. 刷新容器，执行Spring中的refresh（）方法
8. 执行刷新容器后的后置处理逻辑，注意这里为空方法；//到这里启动基本就完成了，计时结束
9. 调用ApplicationRunner和CommandLineRunner的run方法，我们实现这两个接口可以在spring容器其启动后需要的一些东西，比如加载一些业务数据等；
10. 返回容器对象

## Spring安全

## RBAC 模型了解吗？

RBAC 即基于角色的权限访问控制（Role-Based Access Control）。这是一种通过角色关联权限，角色同时又关联用户的授权的方式。

![RBAC](https://guide-blog-images.oss-cn-shenzhen.aliyuncs.com/github/javaguide/booksRBAC.png)

## 什么是 Cookie ? Cookie 的作用是什么?

`Cookies` 是某些网站为了辨别用户身份而储存在用户本地终端上的数据。

## 什么是 Token?什么是 JWT?

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







