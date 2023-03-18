### Spring如何管理过滤器Filter？

有以下两种方法：

1. 使用 @Bean 注解方式注册过滤器
2. 使用@WebFilter注解方式注册过滤器

### 如何使用Spring Security实现安全机制?

1. 添加 Spring Security 依赖
2. 配置 Spring Security
3. 添加安全注解

### Spring security 过滤器链执行顺序

1. `ChannelProcessingFilter`：该过滤器负责检查请求的安全通道是否正确。例如，如果请求是通过 HTTP 发送的，但需要使用 HTTPS，那么该过滤器将将请求重定向到 HTTPS 通道。
2. `SecurityContextPersistenceFilter`：该过滤器负责在请求之间持久化 `SecurityContext`。例如，在基于 Session 的认证中，该过滤器将从 Session 中加载 `SecurityContext`，并将其设置为当前请求的上下文。如果用户已经在之前的请求中进行了身份验证，则可以直接使用相应的 `SecurityContext`，而不必再次进行身份验证。
3. `ConcurrentSessionFilter`：该过滤器负责处理并发登录的情况。例如，如果用户在其他地方登录了相同的账号，那么该过滤器将拦截新的登录请求，并根据配置的策略来处理。
4. `LogoutFilter`：该过滤器负责处理注销请求。例如，当用户点击注销按钮时，该过滤器将使当前用户的 `SecurityContext` 失效，并在需要时将其重定向到注销成功页面。
5. `UsernamePasswordAuthenticationFilter`：该过滤器负责处理基于用户名和密码的身份验证。例如，在登录页面提交表单时，该过滤器将验证提交的用户名和密码，并创建一个 `Authentication` 对象表示当前用户的身份信息。
6. `DefaultLoginPageGeneratingFilter`：该过滤器负责生成默认的登录页面。例如，在没有自定义登录页面时，该过滤器将生成一个简单的 HTML 表单，用于提交用户名和密码。
7. `CasAuthenticationFilter`：该过滤器负责处理 CAS 单点登录请求。例如，在使用 CAS 单点登录时，该过滤器将与 CAS 服务器进行通信，并获取当前用户的身份信息。
8. `BasicAuthenticationFilter`：该过滤器负责处理基本身份验证请求。例如，在使用 HTTP 基本身份验证时，该过滤器将从请求头中获取用户名和密码，并创建一个 `Authentication` 对象表示当前用户的身份信息。
9. `RequestCacheAwareFilter`：该过滤器负责处理缓存的请求。例如，在身份验证之前，该过滤器将缓存当前请求，以便在身份验证成功后将用户重定向回原始请求。
10. `SecurityContextHolderAwareRequestFilter`：该过滤器负责创建包装了 `HttpServletRequest` 的 `HttpServletRequestWrapper`，以便支持 Spring Security 的安全功能，例如 `@PreAuthorize` 和 `@PostAuthorize`。
11. `AnonymousAuthenticationFilter`：该过滤器负责处理匿名用户的请求。例如，在启用匿名用户时，该过滤器将为匿名用户创建一个 `Authentication` 对象表示其身份信息。
12. `SessionManagementFilter`：该过滤器负责处理会话管理。例如，在基于 Session 的认证中，该过滤器将检查当前用户的会话是否已过期，以及当前用户是否超过了最大会话数限制等。
13. `ExceptionTranslationFilter`：该过滤器负责处理 Spring Security 异常。例如，在身份验证失败时，该过滤器将根据配置的策略来处理该异常，例如将用户重定向到登录页面或者返回一个错误页面。
14. `FilterSecurityInterceptor`：该过滤器负责进行访问控制决策。例如，在用户请求受保护的资源时，该过滤器将使用 `AccessDecisionManager` 对象来决定是否允许该请求。

在上述过滤器链中，`FilterSecurityInterceptor` 是整个过滤器链的核心。它是 Spring Security 实现访问控制的关键部分，负责将当前请求与配置的访问控制规则进行匹配，并决定是否允许该请求访问受保护的资源。

需要注意的是，Spring Security 过滤器链的顺序是固定的，但是可以通过配置来添加、替换或者移除特定的过滤器。这样可以根据具体的需求来定制过滤器链，以满足不同的应用场景。

### 什么是安全上下文security context?

​    安全上下文（Security Context）是指用于存储用户的认证和授权信息的数据结构。它是一个线程本地变量，可以在应用程序的任何位置访问和修改。

### 允许在哪些安全注释中使用SpEL?

​    Spring Security 允许在以下安全注释中使用 Spring 表达式语言（SpEL）：

1. @PreAuthorize: 在方法执行之前进行访问控制检查。
2. @PostAuthorize: 在方法执行之后进行访问控制检查。
3. @PreFilter: 在方法执行之前对输入参数进行过滤，以确保只有合法的参数可以被方法接受。
4. @PostFilter: 在方法执行之后对返回值进行过滤，以确保只有合法的返回值可以被返回。

​    

​    

​    

​     