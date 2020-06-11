package com.tenfar.yiyi.config;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyName;
import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.google.common.base.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.async.DeferredResult;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.schema.configuration.ObjectMapperConfigured;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.annotation.PostConstruct;
import java.lang.annotation.Annotation;
import java.util.Optional;
import java.util.function.Function;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    private static final String splitor = ";";
    @Autowired
    private ApplicationContext applicationContext;

    public static Predicate<RequestHandler> basePackage(final String basePackage) {
        return input -> declaringClass(input).map(handlerPackage(basePackage)).orElse(true);
    }

    private static Function<Class<?>, Boolean> handlerPackage(final String basePackage) {
        return input -> {
            // 循环判断匹配
            for (String strPackage : basePackage.split(splitor)) {
                boolean isMatch = input.getPackage().getName().startsWith(strPackage);
                if (isMatch) {
                    return true;
                }
            }
            return false;
        };
    }

    private static Optional<? extends Class<?>> declaringClass(RequestHandler input) {
        return Optional.ofNullable(input.declaringClass());
    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .genericModelSubstitutes(DeferredResult.class)
                .useDefaultResponseMessages(false)
                .forCodeGeneration(false)
                .pathMapping("/")
                .select()
                .build()
                .apiInfo(apiInfo())
                .enable(true)
                .select()
                .apis(basePackage("com.tenfar.yiyi.controller" + splitor + "com.tenfar.yiyi.controllers"))
                .paths(PathSelectors.any())
                .build();

    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("YIYI 文档接口")
                .description("接口文档")
                .version("v2")
                .build();
    }

    @PostConstruct
    public void setObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        objectMapper.registerModule(module);

        objectMapper.setAnnotationIntrospector(new JacksonAnnotationIntrospector() {

            @Override
            public boolean isAnnotationBundle(Annotation ann) {
                if (ann.annotationType() == JSONField.class) {
                    return true;
                }
                return super.isAnnotationBundle(ann);
            }

            @Override
            public PropertyName findNameForSerialization(Annotated a) {
                PropertyName nameForSerialization = super.findNameForSerialization(a);
                if (nameForSerialization == null || nameForSerialization == PropertyName.USE_DEFAULT) {
                    JSONField jsonField = _findAnnotation(a, JSONField.class);
                    if (jsonField != null) {
                        return PropertyName.construct(jsonField.name());
                    }
                }
                return nameForSerialization;
            }

            @Override
            public PropertyName findNameForDeserialization(Annotated a) {
                PropertyName nameForDeserialization = super.findNameForDeserialization(a);
                if (nameForDeserialization == null || nameForDeserialization == PropertyName.USE_DEFAULT) {
                    JSONField jsonField = _findAnnotation(a, JSONField.class);
                    if (jsonField != null) {
                        return PropertyName.construct(jsonField.name());
                    }
                }
                return nameForDeserialization;
            }
        });

        ObjectMapperConfigured objectMapperConfigured = new ObjectMapperConfigured(applicationContext, objectMapper);
        applicationContext.publishEvent(objectMapperConfigured);
    }

}
