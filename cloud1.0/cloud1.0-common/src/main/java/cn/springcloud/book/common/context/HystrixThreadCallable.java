package cn.springcloud.book.common.context;
import java.util.Map;
import java.util.concurrent.Callable;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

/**
 * 线程隔离自定义处理
 * @author xielijie.93
 * 2020年1月12日上午11:35:48
 * @param <S>
 */
public class HystrixThreadCallable<S> implements Callable<S>{
	 private final RequestAttributes requestAttributes;  
	 private final Callable<S> delegate;
	 private Map<String, Object> params;
	 
     public HystrixThreadCallable(Callable<S> callable, RequestAttributes requestAttributes,Map<String, Object> params) {  
         this.delegate = callable; 
         this.requestAttributes = requestAttributes;  
         this.params = params;
     }

     @Override  
     public S call() throws Exception {
         try {
             RequestContextHolder.setRequestAttributes(requestAttributes);
             SwapContextHolder.context.set(params);
             //System.out.println(">>>>>>>>>>>>>>>>>>."+params+".<<<<<<<<<<<<<<<<<<<<");
             return delegate.call();  
         } finally {
             RequestContextHolder.resetRequestAttributes();
             SwapContextHolder.context.remove();
         }  
     }  
       
}
