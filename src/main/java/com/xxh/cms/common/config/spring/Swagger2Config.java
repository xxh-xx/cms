package com.xxh.cms.common.config.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * @author xxh
 */
@Configuration
@ComponentScan(basePackages = "com.xxh.cms.article.controller")
public class Swagger2Config {

    @Bean
    public Docket docket(){
        Contact contact = new Contact("xxh", "https://github.com/xxh-xx", "xxh668899@vip.qq.com");
        ApiInfo apiInfo=new ApiInfoBuilder()
                //api标题
                .title("内容管理")
                //api描述
                .description("内容管理相关接口描述")
                //版本号
                .version("1.0.0")
                //本API负责人的联系信息
                .contact(contact)
                .build();
        //文档类型（swagger2）
        return new Docket(DocumentationType.SWAGGER_2)
                //设置包含在json ResourceListing响应中的api元信息
                .apiInfo(apiInfo)
                //启动用于api选择的构建器
                .select()
                //扫描接口的包
                .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
                //路径过滤器（扫描所有路径）
                .paths(PathSelectors.any())
                .build();
    }

}
