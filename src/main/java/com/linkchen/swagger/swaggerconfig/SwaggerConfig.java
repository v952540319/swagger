package com.linkchen.swagger.swaggerconfig;

import com.google.common.base.Predicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import springfox.documentation.spi.DocumentationType;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket apiConfig() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("api")//定义分组
                .apiInfo(apiInfo())// 调用apiInfo方法,创建一个ApiInfo实例,里面是展示在文档页面信息内容
                .useDefaultResponseMessages(false)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.linkchen.swagger.web"))
                .paths(Predicates.or(PathSelectors.regex("/api/.*")))//过滤的接口
                .build()
                ;//不使用默认的返回值
    }
    @Bean
    public Docket apiConfig1() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("api2")//定义分组
                .apiInfo(apiInfo())// 调用apiInfo方法,创建一个ApiInfo实例,里面是展示在文档页面信息内容
                .useDefaultResponseMessages(false)
                ;//不使用默认的返回值
    }


    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("BookStore Platform API")//大标题
                .description("BookStore Platform's REST API, all the applications could access the Object model data via JSON.")//详细描述
                .version("2.0")//版本
                .contact(new Contact("Helen", "地址", "v952540319@163.com"))//作者
                .license("The Apache License, Version 2.0")//许可证信息
                .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")//许可证地址
                .build();
    }


}




