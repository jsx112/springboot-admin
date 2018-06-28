spring-boot-admin的实际例子，基于以下版本
springboot：2.0.3.RELEASE
spring-boot-admin：2.0.1
spring-cloud：Finchley.RELEASE

其中monitor-center是监控中心，eureka-server是服务注册中心并提供服务，client是调用方。

由于配置了邮件提醒，需要在monitor-center中的application.yml配置邮件信息。
