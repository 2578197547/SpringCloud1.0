package cn.springcloud.book.user.service.feignService.fallback;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;	

import cn.springcloud.book.user.service.feignService.servie.DataService;

/**
 */
@Component
public class UserClientFallback implements DataService{
	
	@Override
	public String getService(@RequestParam(value = "request")String  request) {
		return "hello," +request+", this messge send failed ";
	}
	@Override
	public String getDefaultUser() {
		return new String("get getDefaultUser failed");
	}
	@Override
	public List<String> getProviderData() {
		List<String> list = new ArrayList<>();
		list.add("get getProviderData failed");
		return list;
	}
	@Override
	public String getUser() {
		return new String("get getUser failed");
	}
	
}
