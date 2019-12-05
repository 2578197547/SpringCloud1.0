package cn.springcloud.book.auth.service.impl;

import java.util.HashMap;
import java.util.Map;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

/**
 * 自定义信息到TokenEnhancer中，这里在令牌中添加了用户名称，角色，权限等信息
 * @author xielijie.93
 * 2019年12月5日上午11:31:44
 */
public class CustomTokenEnhancer implements TokenEnhancer {
	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken,
			OAuth2Authentication authentication) {
		Map<String, Object> additionalInformation = new HashMap<>();

		String userName = authentication.getUserAuthentication().getName();
		User user = (User) authentication.getUserAuthentication()
				.getPrincipal();
		additionalInformation.put("userName", userName);
		additionalInformation.put("roles", user.getAuthorities());
		additionalInformation.put("organization", authentication.getName());
		((DefaultOAuth2AccessToken) accessToken)
				.setAdditionalInformation(additionalInformation);
		return accessToken;
	}
}
