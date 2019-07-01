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
