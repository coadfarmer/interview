### ![Spring主要模块](https://guide-blog-images.oss-cn-shenzhen.aliyuncs.com/github/javaguide/jvme0c60b4606711fc4a0b6faf03230247a.png)

### 什么是Spring？

Spring是一个轻量级Java开发框架，是为了解决企业级业务开发的业务逻辑层和其他层的耦合关系。Spring最根本是使命是简化Java开发，让Java开发者专注于程序的开发。

### Spring的优缺点

优点

- 方便解耦，简化开发

  可以将所有对象的创建和依赖关系的维护交给Spring管理。

- AOP编程支持

  Spring提供面向切面编程，可以方便地实现对程序进行拦截，监控等功能。

- 声明式事务支持

  只需要配置就能完成对事务的管理，无需手动编程

- 方便集成各种优秀框架

缺点

- Spring明明是一个很轻量级的框架，却给人感觉大而全
- Spring依赖反射，反射影响性能

### Spring由哪些模块组成

- Spring Core：提供了框架最基本的组成部分，包括控制反转（IOC）和依赖注入(DI)功能。
- Spring Beans：提供了BeanFactory，是工厂模式的一个经典实现，Spring将管理对象称为Bean。
- SpringContext：负责实例化、配置和组装Bean
- Spring AOP：提供了面向切面的编程实现，可以自定义拦截器
- Spring Web：网络相关模块
- Spring Data Access：数据库相关模块
- SpringTest：测试相关模块

## IOC & AOP

### IOC（Inverse of  Control）

IoC是一种设计思想，而不是一个具体的技术实现。IoC的思想就是将原本在程序中手动创建对象的控制权，交给Spring来管理。

比如要实例化一个Service，这个Service可能依赖了很多类，那我们要搞清楚每一个类的构造函数，这很麻烦。利用IoC，只需要配置好，然后在需要的地方引用就行了。

IoC容器是Spring用来实现IoC的载体，IoC容器实际上是个Map，实现原理是工厂模式加反射机制。

### AOP（Aspect-Oriented Programming）

AOP即面向切面编程，将那些与业务无关，





