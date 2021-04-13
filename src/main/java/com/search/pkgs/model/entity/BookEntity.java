package com.search.pkgs.model.entity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.List;

import lombok.Data;

/**
 * @author cs12110
 * @version V1.0
 * @since 2021-04-12 09:23
 */
@Data
public class BookEntity {

    /**
     * id
     */
    private String id;

    /**
     * isbn
     */
    private String isbn;

    /**
     * 作者
     */
    private String author;

    /**
     * 书籍名称
     */
    private String bookName;

    /**
     * 描述,ik分词
     */
    private String description;

    /**
     * 评分
     */
    private Double score;

    /**
     * 发行时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date publishTime;

    /**
     * 标签
     */
    private List<String> tags;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
