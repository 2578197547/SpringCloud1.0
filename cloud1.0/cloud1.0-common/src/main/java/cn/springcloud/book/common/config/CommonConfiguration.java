package cn.springcloud.book.common.config;

import feign.Feign;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import cn.springcloud.book.common.intercepter.FeignUserContextInterceptor;
import cn.springcloud.book.common.intercepter.RestTemplateUserContextInterceptor;

@SuppressWarnings("deprecation")
@Configuration
@EnableWebMvc
public class CommonConfiguration extends WebMvcConfigurerAdapter {

	/**
	 * 创建Feign请求拦截器
	 */
	@Bean
	@ConditionalOnClass(Feign.class)
	public FeignUserContextInterceptor feignTokenInterceptor() {
		return new FeignUserContextInterceptor();
	}

	/**
	 * RestTemplate拦截器
	 * @LoadBalanced:启用了负载均衡后url不再写端口而是直接写serviceId，ps：http://sc-auth-server/oauth/token
	 * @return
	 */
	@LoadBalanced
	// 开启客户端负载均衡
	@Bean
	public RestTemplate restTemplate() {
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getInterceptors().add(
				new RestTemplateUserContextInterceptor());
		return restTemplate;
	}

}
