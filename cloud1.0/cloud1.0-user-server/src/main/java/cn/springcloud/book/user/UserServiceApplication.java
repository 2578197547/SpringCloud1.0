package cn.springcloud.book.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 用户服务
 * @author xielijie.93
 * 2019年12月5日下午4:26:08
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableCircuitBreaker
public class UserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }
   
}
