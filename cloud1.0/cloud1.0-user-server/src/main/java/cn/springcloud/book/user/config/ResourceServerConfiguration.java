package cn.springcloud.book.user.config;

import java.io.IOException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.util.FileCopyUtils;

@Configuration
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true)//控制方法的访问权限
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

	public static final String public_cert = "public.cert";
	
	@Bean
	public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        Resource resource =  new ClassPathResource(public_cert);
        String publicKey;
        try {
            publicKey = new String(FileCopyUtils.copyToByteArray(resource.getInputStream()));
        }catch (IOException e) {
            throw new RuntimeException(e);
        }
        //使用converter.setVerifierKey(publicKey);会出现 Cannot convert access token to JSON
        converter.setVerifierKey(publicKey);
        return converter;
    }
	
	@Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
    	//解决sessionId在添加security权鉴后每次访问改变问题
    	http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER);
    	http.sessionManagement().sessionFixation().none();
    	
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/product/**","/registry/**", "/user/login/**","/actuator/hystrix.stream","/getProviderData").permitAll()
                .antMatchers("/**").authenticated();
                //.antMatchers("/**").permitAll();
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.tokenStore(tokenStore());
    }
}
