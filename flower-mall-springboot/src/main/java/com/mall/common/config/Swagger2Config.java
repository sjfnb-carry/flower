package com.mall.common.config;

import com.google.common.base.Predicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/*Swagger2配置*/
@Configuration
@EnableSwagger2
public class Swagger2Config {
  @Bean
  public Docket createRestApi() {
    ParameterBuilder aParameterBuilder1 = new ParameterBuilder();
    aParameterBuilder1.name("token").description("用户登录token").modelRef(new ModelRef("String")).parameterType("header").required(true).build();
    List<Parameter> aParameters = new ArrayList<Parameter>();
    aParameters.add(aParameterBuilder1.build());


    return new Docket(DocumentationType.SWAGGER_2)
            .groupName("com.floewer.mall.sys")
            .apiInfo(apiInfo())
            .select()
            .paths(Predicates.not(PathSelectors.regex("/admin/.*")))
            .paths(Predicates.not(PathSelectors.regex("/error.*")))
            .paths(PathSelectors.any())
            .build()
            .useDefaultResponseMessages(false)
            .globalOperationParameters(aParameters);
  }

  private ApiInfo apiInfo() {

    return new ApiInfoBuilder()
            .title("JSDX_WHZ")
            .description(" 代码生成")
            .termsOfServiceUrl(" https://www.hujieit.top")
            .version("1.1.1")
            .build();
  }
}
