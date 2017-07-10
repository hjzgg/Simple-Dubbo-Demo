package com.hjzgg.simulation.register.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@Import({com.hjzgg.simulation.common.cache.CacheConfig.class})
@EnableSwagger2
public class Config {

    @Bean
    public Docket restApiForSensors() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .groupName("sensors data")
                .forCodeGeneration(true)
                .select()
//                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
//                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.ant("/index/**"))
                .build()
                .useDefaultResponseMessages(false);
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("注册中心SpringBoot Swagger2 RESTful API")
                .contact(new Contact("胡峻峥", "", "2570230521@qq.com"))
                .description("服务注册中心")
                .version("1.0")
                .build();
    }
}
