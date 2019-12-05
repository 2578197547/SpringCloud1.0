package cn.springcloud.book.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 自定义的鉴权filter
 */
public class AuthFilter extends ZuulFilter {
	private static final Logger logger = LoggerFactory.getLogger(AuthFilter.class);
	
	@Override
	public boolean shouldFilter() {
		// 判断是否需要进行处理
		return true;
	}

	@Override
	public Object run() {
		RequestContext rc = RequestContext.getCurrentContext();
		authUser(rc);
		return null;
	}

	@Override
	public String filterType() {
		return "pre";
	}

	@Override
	public int filterOrder() {
		return 0;
	}
	
	//将request中Http请求头的所有信息存到一个Map<String, String>中
	private static Map<String, String> httpRequestToMap(HttpServletRequest request) {
        Enumeration<String> headerNames = request.getHeaderNames();
        Map<String, String> headers = new HashMap<>();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            headers.put(headerName, request.getHeader(headerName));
        }
        return headers;
    }
	
	//自定义的鉴权处理
	public static void authUser(RequestContext ctx) {
		HttpServletRequest request = ctx.getRequest();
		Map<String, String> header = httpRequestToMap(request);
		for (Map.Entry<String, String> entry : header.entrySet()) {
			ctx.addZuulRequestHeader(entry.getKey(), entry.getValue());
		}
	}
	
}
