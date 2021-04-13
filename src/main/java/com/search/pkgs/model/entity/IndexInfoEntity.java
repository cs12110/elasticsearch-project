package com.search.pkgs.model.entity;

import lombok.Data;

/**
 * @author huapeng.huang
 * @version V1.0
 * @since 2021-04-12 15:49
 */
@Data
public class IndexInfoEntity {

    private String indexName;

    private Object mapping;

    private Object setting;

}
