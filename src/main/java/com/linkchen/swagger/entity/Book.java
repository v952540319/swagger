package com.linkchen.swagger.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@ApiModel
public class Book {
    @ApiModelProperty(hidden=true)
    private Integer bookId;

    @ApiModelProperty(name="bookName",value="图书标题",required=true,example="深入理解Java虚拟机")
    private String bookName;
}
