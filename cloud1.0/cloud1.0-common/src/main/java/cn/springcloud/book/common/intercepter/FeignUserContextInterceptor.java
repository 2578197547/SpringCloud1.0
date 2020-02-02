package cn.springcloud.book.common.intercepter;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import feign.RequestInterceptor;
import feign.RequestTemplate;

/**
 * Feign传递信息
 * @author xielijie.93
 * 2019年12月5日下午3:12:31
 */
public class FeignUserContextInterceptor implements RequestInterceptor {

	@Override
	public void apply(RequestTemplate template) {
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = attributes.getRequest();
		//Feign传递头部信息
        Enumeration<String> headerNames = request.getHeaderNames();
        if (headerNames != null) {
            while (headerNames.hasMoreElements()) {
                String name = headerNames.nextElement();
                String values = request.getHeader(name);
                template.header(name, values);
            }
        }
        //Feign传递参数令牌
        //get调用body中有值时自动转为post 调用
/*        String access_token = request.getParameter("access_token");
        if (access_token!=null) {
        	StringBuffer body =new StringBuffer();
        	body.append("access_token").append("=").append(access_token);
        	template.body(body.toString());
		}*/
        //Feign实现session共享
//        String sessionId = RequestContextHolder.currentRequestAttributes().getSessionId();
//        if (null != sessionId) {
//        	template.header("Cookie", "SESSION=" + sessionId);
//        }
	}

}
