package cn.springcloud.book.auth.service.impl;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import cn.springcloud.book.auth.entity.SysUser;
import cn.springcloud.book.auth.service.SysUserService;

/**
 * 自定义信息到TokenEnhancer中，这里在令牌中添加了用户名称，角色，权限等信息
 * @author xielijie.93
 * 2019年12月5日上午11:31:44
 */
public class CustomTokenEnhancer implements TokenEnhancer {
	
	@Autowired
	SysUserService sysUserService;
	
	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken,
			OAuth2Authentication authentication) {
		Map<String, Object> additionalInformation = new HashMap<>();

		String userName = authentication.getUserAuthentication().getName();
		SysUser sysUser = sysUserService.getUserByName(userName);
		additionalInformation.put("userId", sysUser.getId());
		additionalInformation.put("userName", userName);
		additionalInformation.put("roles", sysUser.getRoles());
		additionalInformation.put("organizations", authentication.getName());
		((DefaultOAuth2AccessToken) accessToken)
				.setAdditionalInformation(additionalInformation);
		return accessToken;
	}
}
