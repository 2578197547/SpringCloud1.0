#数据服务，提供基础的数据
port:8099
/getContextUserId：返回当前环境下的用户
/getDefaultUser：//返回配置文件内配置的默认用户（需要启动配置中心（git））        
/getProviderData：//返回生产者数据（List<String>）

http://localhost:8099/actuator/refresh(post):重新向配置中心获取config数据
    



