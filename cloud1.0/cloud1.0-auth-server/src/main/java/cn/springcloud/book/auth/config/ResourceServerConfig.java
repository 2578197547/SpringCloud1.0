package cn.springcloud.book.auth.config;

import java.io.IOException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.jwt.crypto.sign.RsaVerifier;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.util.FileCopyUtils;

/**
 * @author xielijie.93
 * 2019年12月5日上午11:22:35
 */
@Order(6)//该过滤器优先级需高于WebSecurityConfigurerAdapter才能实现授权码模式
@Configuration
@EnableResourceServer //这个类表明了此应用是OAuth2 的资源服务器，此处主要指定了受资源服务器保护的资源链接
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
	
	@Bean//需要以公钥作为bean外部接口才能访问受限资源
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        Resource resource =  new ClassPathResource("public.cert");
        String publicKey;
        try {
            publicKey = new String(FileCopyUtils.copyToByteArray(resource.getInputStream()));
        }catch (IOException e) {
            throw new RuntimeException(e);
        }
        //使用converter.setVerifierKey(publicKey);会出现 Cannot convert access token to JSON
        converter.setVerifier(new RsaVerifier(publicKey));
        return converter;
    }
    
	@Bean
    public TokenStore tokenStore() {//配置token模式
        return new JwtTokenStore(accessTokenConverter());
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.tokenStore(tokenStore());
    }

	/*
	 * 配置受资源服务器保护的资源链接,仅接受签名校验
	 * */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        /**
         * always – 如果session不存在总是需要创建；
         * ifRequired – 仅当需要时，创建session(默认配置)；
         * never – 框架从不创建session，但如果已经存在，会使用该session ；
         * stateless – Spring Security不会创建session，或使用session；
         */
    	http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER);
        /*
         * "migrateSession"，即认证时，创建一个新http session，原session失效，属性从原session中拷贝过来
         * "none"，原session保持有效；
         * "newSession"，新创建session，且不从原session中拷贝任何属性。
         */
    	http.sessionManagement().sessionFixation().none();
    	
    	http.csrf().disable();
        http.authorizeRequests()
        .antMatchers("/actuator/hystrix.stream").permitAll()
        .anyRequest().authenticated();//校验所有请求
        }
 
}

