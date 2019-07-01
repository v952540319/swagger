# swagger使用日志

### pom

```
<dependency>
  <groupId>io.springfox</groupId>
  <artifactId>springfox-swagger2</artifactId>
  <version>2.7.0</version>
</dependency>

<dependency>
  <groupId>io.springfox</groupId>
  <artifactId>springfox-swagger-ui</artifactId>
  <version>2.7.0</version>
</dependency>


```

### 配置类

```
package com.trace.configuration;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
* Created by Trace on 2018-05-16.<br/>
* Desc: swagger2配置类
*/
@SuppressWarnings({"unused"})
@Configuration @EnableSwagger2
public class Swagger2Config {
   @Value("${swagger2.enable}") private boolean enable;

   @Bean("UserApis")
   public Docket userApis() {
       return new Docket(DocumentationType.SWAGGER_2)
           .groupName("用户模块")
           .select()
           .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
           .paths(PathSelectors.regex("/user.*"))
           .build()
           .apiInfo(apiInfo())
           .enable(enable);
   }

   @Bean("CustomApis")
   public Docket customApis() {
       return new Docket(DocumentationType.SWAGGER_2)
           .groupName("客户模块")
           .select()
           .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
           .paths(PathSelectors.regex("/custom.*"))
           .build()
           .apiInfo(apiInfo())
           .enable(enable);
   }

   private ApiInfo apiInfo() {
       return new ApiInfoBuilder()
           .title("XXXXX系统平台接口文档")
           .description("提供子模块1/子模块2/子模块3的文档, 更多请关注公众号: 随行享阅")
           .termsOfServiceUrl("https://xingtian.github.io/trace.github.io/")
           .version("1.0")
           .build();
   }
}


```

