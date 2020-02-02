package cn.springcloud.book.common.context;

import java.util.Map;

/**
 * 传递上下文信息
 * @author xielijie.93
 * 2019年12月13日下午4:36:29
 */
public class SwapContextHolder {
	
	public static ThreadLocal<Map<String, Object>> context = new ThreadLocal<Map<String, Object>>();
	
	public static Map<String, Object> currentSwap() {
		return context.get();
	}

	public static void set(Map<String, Object> swap) {
		context.set(swap);
	}

	public static void shutdown() {
		context.remove();
	}
	
}
