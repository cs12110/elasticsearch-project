package com.search.pkgs.common.conig;

import javax.annotation.Resource;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author cs12110
 * @version V1.0
 * @since 2021-04-12 09:40
 */
@Configuration
public class ElasticSearchRestClientConfig {
    @Resource
    private ElasticSearchConfig elasticSearchConfig;

    @Bean
    public RestHighLevelClient elasticsearchClient() {
        HttpHost httpHost = new HttpHost(elasticSearchConfig.getHost(), elasticSearchConfig.getPort());
        RestClientBuilder builder = RestClient.builder(httpHost);

        return new RestHighLevelClient(builder);
    }

}
