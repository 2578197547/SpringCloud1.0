package cn.springcloud.book.common.intercepter;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import cn.springcloud.book.common.context.SwapContextHolder;
import cn.springcloud.book.common.util.JWTUtil;

public class UserContextInterceptor implements HandlerInterceptor {
	private static final Logger log = LoggerFactory.getLogger(UserContextInterceptor.class);
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse respone, Object arg2) {
//		Map<String, Object> claimsMap = new HashMap<String, Object>();
//		try {
//			//如果通信中包含令牌，将令牌解析成用户信息保存到上下文
//			String access_token = request.getParameter("access_token");
//			String Authorization = request.getHeader("Authorization");
//			if(access_token!=null&&access_token.length()>1){
//				claimsMap = JWTUtil.jwtVerify(access_token);
//			}else if (Authorization!=null&&Authorization.length()>1&&Authorization.toUpperCase().startsWith("BEARER")) {
//				Authorization = Authorization.replaceFirst("(?i)bearer(\\s*)", "");//bearer(\\s*)忽视大小写
//				claimsMap = JWTUtil.jwtVerify(Authorization);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			return false;
//		}
//		SwapContextHolder.set(claimsMap);
//		log.info(">>>>>>>>userId:"+claimsMap.get("userId")+"<<<<<<<<");
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse respone, Object arg2, ModelAndView arg3)
			throws Exception {
		// DOING NOTHING
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse respone, Object arg2, Exception arg3)
			throws Exception {
		//SwapContextHolder.shutdown();
	}
	
	
	
}
