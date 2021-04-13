package com.search.pkgs.common.conig;

import com.alibaba.fastjson.JSON;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

/**
 * @author cs12110
 * @version V1.0
 * @since 2021-04-12 09:46
 */
@Data
@Component
@ConfigurationProperties(prefix = "elasticsearch")
public class ElasticSearchConfig {

    private String host;
    private Integer port;
    private String bookIndices;
    private String clusterName;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
