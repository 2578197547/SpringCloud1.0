package cn.springcloud.book.common.config;

import feign.Feign;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import cn.springcloud.book.common.context.SpringCloudHystrixConcurrencyStrategy;
import cn.springcloud.book.common.intercepter.FeignUserContextInterceptor;
import cn.springcloud.book.common.intercepter.RestTemplateUserContextInterceptor;
import cn.springcloud.book.common.intercepter.UserContextInterceptor;

@SuppressWarnings("deprecation")
@Configuration
@EnableWebMvc
public class CommonConfiguration extends WebMvcConfigurerAdapter {

	/**
	 * 自定义springmvc的拦截器
	 * 请求拦截器
	 */
	@Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new UserContextInterceptor());
    }
	
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
	
    /**
     * 在cn.springcloud.book.common.context.UserContextHolder中使用了ThreadLocal来保存用户信息，
     * 由于在线程隔离的模式下，会导致前后线程传递对象丢失，在这里使用自定义并发策略HystrixConcurrencyStrategy可解决该问题
     */
    @Bean
	public SpringCloudHystrixConcurrencyStrategy springCloudHystrixConcurrencyStrategy() {
		return new SpringCloudHystrixConcurrencyStrategy();
	}
}
