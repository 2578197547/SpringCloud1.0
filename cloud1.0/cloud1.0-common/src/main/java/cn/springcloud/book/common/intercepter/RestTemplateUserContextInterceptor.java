package cn.springcloud.book.common.intercepter;

import java.io.IOException;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

/**
 * RestTemplate请求拦截器
 * @author xielijie.93
 * 2019年12月5日下午3:13:42
 */
public class RestTemplateUserContextInterceptor implements ClientHttpRequestInterceptor {

	@Override
	public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
			throws IOException {
		//可以在这里自定义请求处理
		return execution.execute(request, body);
	}
}
