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

	//两种分组方法
	//1.过滤的接口
	//2.apis分包（例：com.linkchen.swagger.web.user and com.linkchen.swagger.web.book）
   
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

```

### Book实体

```
@ApiModel
public class Book {
    @ApiModelProperty(hidden=true)
    private Integer bookId;

    @ApiModelProperty(name="bookName",value="图书标题",required=true,example="深入理解Java虚拟机")
    private String bookName;
}
```

### Controller

```
package com.linkchen.swagger.web;

import com.linkchen.swagger.entity.Book;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.val;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@Api(description="书籍管理的CRUD")//对api资源的描述（热部署不好使）
public class BookController {
    static Map<Integer,Book> bookList = new HashMap<>();
    static {
        for (int i=0;i<=20;i++){
            bookList.put(i,new Book(i,"book"+i));
        }
    }
    @GetMapping("/books")
    @ApiOperation(value = "图书列表", notes = "获取全部图书列表")
    public Map<Integer, Book> getBookList(){
        return  bookList;
    }

    @GetMapping("books/{id}")
    @ApiOperation(value = "获取图书", notes = "根据id获取图书信息")
    @ApiResponses({
            @ApiResponse(code = -1, message = "数据不存在")
    })
    public Book getBookById(@PathVariable("id") Integer id){
        return bookList.get(id);
    }
    //原子变量，方便生成自增序列
    static AtomicLong seq = new AtomicLong(10);
    @PostMapping("books")
    public Book saveBook(@RequestBody Book book) {

        //模拟数据库生成自增长主键
        Integer id = Math.toIntExact(seq.incrementAndGet());
        book.setBookId(id);
        bookList.put(id, book);
        //将携带主键的book对象作为响应返回
        return book;
}
    @PutMapping("books/{id}")
    public Book updateBookById(@PathVariable("id") Integer id, @RequestBody Book book) {

        //从持久层中获取数据
        Book b = bookList.get(id);
        //根据远程传递过来的参数修改数据
        b.setBookName(book.getBookName());
        //更新持久层的数据
        bookList.put(id, b);
        //响应
        return b;
    }
    @DeleteMapping("books/{id}")
    public String deleteBookById(@PathVariable("id") Long id) {
        bookList.remove(id);
        return "success";
    }







}
```





# RESTful URL**

| **动作** | **说明** | **url****示例** | **功能**                                         |
| -------- | -------- | --------------- | ------------------------------------------------ |
| GET      | 获取资源 | /books          | 得到 book 列表                                   |
| GET      | 获取资源 | /books/1        | 得到 id = 1 的 book                              |
| POST     | 新建资源 | /books          | 新增 book，在请求体中传递完整book信息            |
| PUT      | 更新资源 | /books/1        | 更新 id = 1的 book，请求体中船体改变后的book资源 |
| DELETE   | 删除资源 | /books/1        | 删除 id = 1的 book                               |