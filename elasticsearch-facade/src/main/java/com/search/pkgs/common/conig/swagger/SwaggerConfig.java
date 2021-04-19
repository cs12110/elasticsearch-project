package com.search.pkgs.common.conig.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * @author cs12110
 * @version V1.0
 * @since 2021-04-14 10:31
 */
@Configuration
public class SwaggerConfig {

    /**
     * 注入Docket
     *
     * @return docket
     */
    @Bean
    public Docket docket() {
        String basePackages = "com.search.pkgs.controller";

        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select()
            .apis(RequestHandlerSelectors.basePackage(basePackages)).paths(PathSelectors.any()).build();
    }

    /**
     * 配置在线文档的基本信息
     *
     * @return ApiInfo
     */
    private static ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("ElasticSearch").contact(new Contact("cs12110", "", null))
            .termsOfServiceUrl("").version("v1.0").build();
    }
}
