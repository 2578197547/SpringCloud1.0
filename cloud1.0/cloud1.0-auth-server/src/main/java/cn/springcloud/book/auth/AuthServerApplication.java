package cn.springcloud.book.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 授权服务
 * @author xielijie.93
 * 2019年12月5日下午12:00:05
 */
@SpringBootApplication
@EnableDiscoveryClient//配置本应用将使用服务注册和服务发现
@EnableCircuitBreaker//启动断路器
public class AuthServerApplication {
	public static void main(String[] args) {
		SpringApplication.run(AuthServerApplication.class, args);
	}

}
