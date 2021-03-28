package com.xxh.cms.common.config.spring;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author xxh
 */
@Configuration
public class JwtWebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(new JwtInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/user/login");

    }

}
