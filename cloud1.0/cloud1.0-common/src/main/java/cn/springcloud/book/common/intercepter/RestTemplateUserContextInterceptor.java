package cn.springcloud.book.common.intercepter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * RestTemplate请求拦截器
 * @author xielijie.93
 * 2019年12月5日下午3:13:42
 */
public class RestTemplateUserContextInterceptor implements ClientHttpRequestInterceptor {

	@Override
	public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
			throws IOException {
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest req = attributes.getRequest();
		//RestTemplate传递头部信息
		HttpHeaders headers = request.getHeaders();
        Enumeration<String> headerNames = req.getHeaderNames();
        if (headerNames != null) {
            while (headerNames.hasMoreElements()) {
                String name = headerNames.nextElement();
                String values = req.getHeader(name);
                headers.add(name, values);
            }
        }
        //RestTemplate传递参数令牌
        //get调用body中有值时自动转为post 调用
/*        String access_token = req.getParameter("access_token");
        if (access_token!=null) {
        	StringBuffer bodyBuffer =new StringBuffer();
        	bodyBuffer.append("access_token").append("=").append(access_token);
        	body = body.toString().getBytes();
		}*/
        //RestTemplate实现session共享
//        String sessionId = RequestContextHolder.currentRequestAttributes().getSessionId();
//        if (null != sessionId) {
//        	request.getHeaders().add("Cookie", "SESSION=" + sessionId);
//        }
		return execution.execute(request, body);
	}
}
