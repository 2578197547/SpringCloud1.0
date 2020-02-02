package cn.springcloud.book.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author xielijie.93
 * 2019年12月5日上午11:25:29
 */
@Order(2)
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
    private UserDetailsService userDetailsService;//注入自定义userdetailservice(com.service.auth.serviceauth.service.impl.UserDetailServiceImpl)
	
    @Bean
    PasswordEncoder passwordEncoder() {
    	return new BCryptPasswordEncoder();
        //return PasswordEncoderFactories.createDelegatingPasswordEncoder();//兼容多种密码的加密方式
    }

    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        /**
//         * always – 如果session不存在总是需要创建；
//         * ifRequired – 仅当需要时，创建session(默认配置)；
//         * never – 框架从不创建session，但如果已经存在，会使用该session ；
//         * stateless – Spring Security不会创建session，或使用session；
//         */
//    	http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER);
//        /*
//         * "migrateSession"，即认证时，创建一个新http session，原session失效，属性从原session中拷贝过来
//         * “none”，原session保持有效；
//         * “newSession”，新创建session，且不从原session中拷贝任何属性。
//         */
//    	http.sessionManagement().sessionFixation().none();
    	
    	//不拦截/oauth/**，/login/**，/logout/**(requestMatchers用于需要过滤多个HttpSecurity的情况)
        http.requestMatchers().antMatchers("/oauth/**", "/login/**", "/logout/**")//使HttpSecurity接收以"/login/","/oauth/","/logout/"开头请求。
                .and().authorizeRequests().antMatchers("/oauth/**").authenticated()
                .and().formLogin();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());//注入自定义的UserDetailsService，采用BCrypt加密
    }
    
    @Override//通过重载该方法，可配置Spring Security的Filter链（HTTP请求安全处理）
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
    }
}
