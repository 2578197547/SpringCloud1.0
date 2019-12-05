#配置中心
port:9090

1、以接口方式访问
http://localhost:9090/config-info/dev(返回的信息包含了配置文件的位置、版本、配置文件的名称以及配置文件中的具体内容)
http://localhost:9090/config-info-dev.yml(返回具体配置信息)
仓库中的配置文件会被转换成web接口，访问可以参照以下的规则：
/{application}/{profile}[/{label}]
/{application}-{profile}.yml
/{label}/{application}-{profile}.yml
/{application}-{profile}.properties
/{label}/{application}-{profile}.properties
以neo-config-dev.properties为例子，它的application是neo-config，profile是dev。client会根据填写的参数来选择读取对应的配置。
2、client端访问
(1)添加配置
<dependency>
	<groupId>org.springframework.cloud</groupId>
	<artifactId>spring-cloud-starter-config</artifactId>
</dependency>
(2)指向配置中心
spring.cloud.config.name=sc-configserver
spring.cloud.config.profile=dev
spring.cloud.config.uri=http://localhost:9090/
spring.cloud.config.label=master
解释：
spring.application.name：对应{application}部分
spring.cloud.config.profile：对应{profile}部分
spring.cloud.config.label：对应git的分支。如果配置中心使用的是本地存储，则该参数无用
spring.cloud.config.uri：配置中心的具体地址
spring.cloud.config.discovery.service-id：指定配置中心的service-id，便于扩展为高可用配置集群。
(特别注意：上面这些与spring-cloud相关的属性必须配置在bootstrap.properties中，config部分内容才能被正确加载。因为config的相关配置会先于application.properties，而bootstrap.properties的加载也是先于application.properties。) 
(3)整合eureka
spring.application.name=spring-cloud-config-client
server.port=8002
spring.cloud.config.name=neo-config
spring.cloud.config.profile=dev
spring.cloud.config.label=master
spring.cloud.config.discovery.enabled=true
spring.cloud.config.discovery.serviceId=spring-cloud-config-server
eureka.client.serviceUrl.defaultZone=http://localhost:8000/eureka/
解釋：
主要是去掉了spring.cloud.config.uri直接指向server端地址的配置，增加了最后的三个配置：
spring.cloud.config.discovery.enabled ：开启Config服务发现支持
spring.cloud.config.discovery.serviceId ：指定server端的name,也就是server端spring.application.name的值
eureka.client.serviceUrl.defaultZone ：指向注册中心的地址
这三个配置文件都需要放到bootstrap.properties的配置中
(4)調用
使用@Value注解来获取server端参数的值
@RestController
class HelloController {
    @Value("${neo.hello}")
    private String hello;

    @RequestMapping("/hello")
    public String from() {
        return this.hello;
    }
}