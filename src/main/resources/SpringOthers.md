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
@SpringBootApplication
public class MyApplication {
    public static void main(String[] args) {
        // 加载 Spring Boot 启动类
        SpringApplication app = new SpringApplication(MyApplication.class);
        // 加载应用配置文件
        app.setDefaultProperties(Collections.singletonMap("server.port", "8080"));
        // 启动 Spring 容器
        ConfigurableApplicationContext context = app.run(args);
        // 打印 Banner
        printBanner();
        // 执行命令行运行器
        runCommandLineRunner(context);
        // 应用程序启动成功，等待客户端请求
    }

    private static void printBanner() {
        Banner banner = new SpringApplicationBanner();
        banner.printBanner(new StandardEnvironment());
    }

    private static void runCommandLineRunner(ConfigurableApplicationContext context) {
        String[] runnerNames = context.getBeanNamesForType(CommandLineRunner.class);
        Arrays.sort(runnerNames);
        for (String name : runnerNames) {
            CommandLineRunner runner = context.getBean(name, CommandLineRunner.class);
            runner.run();
        }
    }
}
//注意，以上只是简化的代码，并非springboot源码
```

1. 加载 Spring Boot 启动类：Spring Boot 应用的启动类必须包含 `@SpringBootApplication` 注解，该注解会启用 Spring 的组件扫描和自动配置功能，并开启 Spring Boot 应用的自动配置。
2. 加载应用配置文件：Spring Boot 应用会自动加载 `application.properties` 或 `application.yml` 配置文件中的配置信息，该配置信息包括数据库连接信息、服务端口号等。（`spring.factories` 文件的加载）
3. 启动 Spring 容器：Spring Boot 应用会创建 Spring 容器，加载所有的配置文件，执行所有的 `@Configuration` 标注的配置类和 `@Bean` 标注的方法，完成所有的依赖注入和 Bean 的创建。（打印banner）
4. 启动自动配置：Spring Boot 应用会根据自动配置机制，加载所有的自动配置类，并按照优先级顺序执行这些自动配置类，完成对应用程序的自动配置。
5. 启动 Web 服务器：Spring Boot 应用会根据配置文件中的端口号等信息，启动嵌入式的 Web 服务器，如 Tomcat、Jetty 或 Undertow。
6. 注册 WebServlet、Filter 和 Listener：Spring Boot 应用会根据配置文件中的信息，注册 WebServlet、Filter 和 Listener，处理客户端请求。
7. 执行命令行运行器：如果 Spring Boot 应用包含 `CommandLineRunner` 或 `ApplicationRunner` 接口的实现类，Spring Boot 应用会在 Web 服务器启动之后，自动执行这些实现类中的 `run()` 方法。
8. 完成启动：Spring Boot 应用启动成功，等待客户端请求。

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

1. 用户请求访问需要认证和授权的资源，此时系统会检查用户是否已经登录。
2. 如果用户未登录，系统会要求用户登录，这通常通过用户名和密码进行。
3. 如果用户登录成功，系统会验证用户的身份并生成一个授权码。
4. 然后，系统会将授权码发送给客户端。
5. 客户端使用授权码向认证服务器请求令牌。
6. 认证服务器对授权码进行验证，并生成一个访问令牌。
7. 认证服务器将访问令牌发送给客户端。
8. 客户端使用访问令牌请求受保护的资源。
9. 资源服务器验证访问令牌，并返回请求的资源。

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





