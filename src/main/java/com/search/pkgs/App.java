package com.search.pkgs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * https://blog.csdn.net/cssnnd/article/details/112467712
 * <p>
 * https://blog.csdn.net/stilll123456/article/details/88380035
 * <p>
 * https://blog.csdn.net/weixin_42408648/article/details/108199320
 *
 *
 * <pre>
 *     swagger访问路径:
 *
 *     http://host/context-path/swagger-ui/index.html
 *     http://host/context-path/doc.html
 *
 *     http://127.0.0.1:8090/elastic-search/api/v1/swagger-ui/index.html
 *
 * </pre>
 *
 * @author cs12110
 * @version V1.0
 * @since 2021-04-12 09:16
 */
@EnableSwagger2
@SpringBootApplication
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
