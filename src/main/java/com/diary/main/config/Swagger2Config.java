package com.diary.main.config;

import com.diary.main.interfaces.PassToken;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import org.springframework.beans.factory.annotation.Value;

@Configuration
@EnableSwagger2
public class Swagger2Config {

    @Value("${swagger2.enable}") //标识 是否启动 swagger2 API
    private boolean swagger2Enable;

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .enable(swagger2Enable)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.diary.main"))
                .paths(PathSelectors.any())
                .build();

    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("diary 博客 API文档")
                .description("更多相关文章请关注：https://www.yeblog.club/#/Home")
                .termsOfServiceUrl("https://www.yeblog.club/#/Home")
                .contact("YeHao")
                .version("1.0")
                .build();
    }



}
