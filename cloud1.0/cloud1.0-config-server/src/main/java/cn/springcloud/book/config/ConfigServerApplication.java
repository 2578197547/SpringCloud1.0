package cn.springcloud.book.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * 配置中心
 * @author xielijie.93
 * 2020年2月1日下午11:09:51
 */
@SpringBootApplication
@EnableConfigServer
@EnableDiscoveryClient//配置本应用将使用服务注册和服务发现
public class ConfigServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConfigServerApplication.class, args);

    }
}
