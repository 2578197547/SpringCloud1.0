package cn.springcloud.book.user.controller;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.*;
import cn.springcloud.book.user.entity.SysUser;
import cn.springcloud.book.user.service.UserService;
import cn.springcloud.book.user.utils.BPwdEncoderUtil;
import java.security.Principal;

@RestController
public class TestEndPointController {

    Logger logger = LoggerFactory.getLogger(TestEndPointController.class);

    @Autowired
    private UserService userService;


    //@GetMapping("/product/{id}")
    @RequestMapping("/product/{id}")
    public String getProduct(@PathVariable String id) {

        String dbpasswor = "$2a$10$HBX6q6TndkgMxhSEdoFqWOUtctaJEMoXe49NWh8Owc.4MTunv.wXa";

        logger.info("判断两个密码是否相等 " + (BPwdEncoderUtil.matches("123456", dbpasswor)));

        return "product id : " + id;
    }

    @GetMapping("/order/{id}")
    public String getOrder(@PathVariable String id) {
        return "order id : " + id;
    }

    @GetMapping("/getPrinciple")
    public OAuth2Authentication getPrinciple(OAuth2Authentication oAuth2Authentication, Principal principal, Authentication authentication) {
        logger.info(oAuth2Authentication.getUserAuthentication().getAuthorities().toString());
        logger.info(oAuth2Authentication.toString());
        logger.info("principal.toString() " + principal.toString());
        logger.info("principal.getName() " + principal.getName());
        logger.info("authentication: " + authentication.getAuthorities().toString());

        return oAuth2Authentication;
    }

    @RequestMapping(value = "/registry", method = RequestMethod.GET)
    public SysUser createUser(@RequestParam("username") String username, @RequestParam("password") String password) {
        if (StringUtils.isNotEmpty(username) && StringUtils.isNotEmpty(password)) {
            return userService.create(username, password);
        }
        return null;
    }

    //@PreAuthorize("hasRole('ADMIN') AND hasRole('DBA')")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @RequestMapping("/hello")
    public String hello() {
        return "hello you.by ROLE_ADMIN";
    }
    
    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping("/helloRole")
    public String helloRole() {
        return "hello you.by ADMIN";
    }

}
