package cn.springcloud.book.user.service.feignService.servie;


import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import cn.springcloud.book.user.service.feignService.fallback.UserClientFallback;


/**
 * feign调用数据服务
 * @author zhudeming
 *
 */
@FeignClient(name = "sc-data-service", fallback=UserClientFallback.class)
public interface DataService {
	
	@RequestMapping("/getService")
	public String getService(@RequestParam(value = "request")String  request);
	
    //返回配置文件内配置的默认用户
    @GetMapping("/getDefaultUser")
    public String getDefaultUser();
    
    @RequestMapping(value = "/getProviderData")
    public List<String> getProviderData();

    @RequestMapping(value = "/getUser")
    public String getUser();
}
