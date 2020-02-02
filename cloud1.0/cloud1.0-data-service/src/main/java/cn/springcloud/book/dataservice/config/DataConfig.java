package cn.springcloud.book.dataservice.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

/**
 * 
 * @author xielijie.93
 * 2020年1月25日下午9:18:48
 * 配置信息
 */
@Component
@RefreshScope // 使用该注解的类，会在接到SpringCloud配置中心配置刷新的时候，自动将新的配置更新到该类对应的字段中。
@ConfigurationProperties(prefix = "cn.springcloud.book")
public class DataConfig {

	private String defaultUser;

	public String getDefaultUser() {
		return defaultUser;
	}

	public void setDefaultUser(String defaultUser) {
		this.defaultUser = defaultUser;
	}
    
}
