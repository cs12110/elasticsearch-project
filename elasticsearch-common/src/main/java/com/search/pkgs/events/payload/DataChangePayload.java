package com.search.pkgs.events.payload;

import com.alibaba.fastjson.JSON;

import java.util.Map;

import lombok.Data;

/**
 * @author cs12110
 * @version V1.0
 * @since 2021-04-20 10:20
 */
@Data
public class DataChangePayload {

    private String databaseName;
    private String tableName;

    private Map<String, Object> valueMap;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
