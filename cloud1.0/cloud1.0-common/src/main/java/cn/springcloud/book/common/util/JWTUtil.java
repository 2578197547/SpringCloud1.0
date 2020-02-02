package cn.springcloud.book.common.util;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.FileCopyUtils;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.crypto.sign.RsaVerifier;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

public class JWTUtil {

	public static Map<String, Object> jwtVerify(String token) throws Exception{
		Map<String, Object> claimsMap = new HashMap<String, Object>();
		Resource resource =  new ClassPathResource("public.cert");
		if(resource.exists()){
			String publicKey = new String(FileCopyUtils.copyToByteArray(resource.getInputStream()));
			//校验jwt
			Jwt jwt = JwtHelper.decodeAndVerify(token, new RsaVerifier(publicKey));
			//获取jwt原始内容
			String claims = jwt.getClaims();
			claimsMap = JSON.parseObject(claims, new TypeReference<Map<String, Object>>(){});
		}else{
			return claimsMap;
		}
		return claimsMap;
		
	}
	
	public static Map<String, Object> analysisReq(HttpServletRequest request) throws Exception{
		Map<String, Object> claimsMap = new HashMap<String, Object>();
		//如果通信中包含令牌，将令牌解析成用户信息返回
		String access_token = request.getParameter("access_token");
		String Authorization = request.getHeader("Authorization");
		if(access_token!=null&&access_token.length()>1){
			claimsMap = jwtVerify(access_token);
		}else if (Authorization!=null&&Authorization.length()>1&&Authorization.toUpperCase().startsWith("BEARER")) {
			Authorization = Authorization.replaceFirst("(?i)bearer(\\s*)", "");//bearer(\\s*)忽视大小写
			claimsMap = JWTUtil.jwtVerify(Authorization);
		}
		return claimsMap;
	}
}
