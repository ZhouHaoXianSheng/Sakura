package com.sakura.swagger;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.sakura.swagger.config.SwaggerProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author Mr.Zhou swagger配置
 */
@EnableSwagger2
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(SwaggerProperties.class)
@ConditionalOnProperty(name = "swagger.enabled", matchIfMissing = true)
public class SwaggerAutoConfiguration {

    /**
     * 默认的排除路径，排除Spring Boot默认的错误处理路径和端点
     */
    private static final List<String> DEFAULT_EXCLUDE_PATH = Arrays.asList("/error", "/actuator/**");

    private static final String BASE_PATH = "/**";

    /**
     * 创建API应用，apiInfo() 增加API相关信息
     * 通过select()函数返回一个ApiSelectorBuilder实例,用来控制哪些接口暴露给Swagger来展现，
     * 本例采用指定扫描的包路径来定义指定要建立API的目录。
     *
     * @return
     */
    @Bean
    public Docket api(SwaggerProperties swaggerProperties) {
        // base-path处理
        if (swaggerProperties.getBasePath().isEmpty()) {
            swaggerProperties.getBasePath().add(BASE_PATH);
        }
        // noinspection unchecked
        List<Predicate<String>> basePath = new ArrayList();
        swaggerProperties.getBasePath().forEach(path -> basePath.add(PathSelectors.ant(path)));

        // exclude-path处理
        if (swaggerProperties.getExcludePath().isEmpty()) {
            swaggerProperties.getExcludePath().addAll(DEFAULT_EXCLUDE_PATH);
        }
        List<Predicate<String>> excludePath = new ArrayList<>();
        swaggerProperties.getExcludePath().forEach(path -> excludePath.add(PathSelectors.ant(path)));

        // noinspection Guava
        return new Docket(DocumentationType.SWAGGER_2)
                .host(swaggerProperties.getHost())
                .apiInfo(apiInfo(swaggerProperties))
                .select()
                .apis(RequestHandlerSelectors.basePackage(swaggerProperties.getBasePackage()))
                //要建立API的目录
                //使用Guava Predicates进行过滤
                .paths(Predicates.and(Predicates.not(Predicates.or(excludePath)), Predicates.or(basePath))).build()
                .securitySchemes(Collections.singletonList(securitySchema(swaggerProperties)))
                .securityContexts(Collections.singletonList(securityContext(swaggerProperties))).pathMapping("/");
    }

    /**
     * 配置默认的全局鉴权策略的开关，通过正则表达式进行匹配；默认匹配所有URL
     *
     * @return
     */
    private SecurityContext securityContext(SwaggerProperties swaggerProperties) {
        return SecurityContext.builder()
                .securityReferences(defaultAuth(swaggerProperties))
                .forPaths(PathSelectors.regex(swaggerProperties.getAuthorization().getAuthRegex()))
                .build();
    }

    /**
     * 默认的全局鉴权策略
     *
     * @return
     */
    private List<SecurityReference> defaultAuth(SwaggerProperties swaggerProperties) {
        ArrayList<AuthorizationScope> authorizationScopeList = new ArrayList<>();
        swaggerProperties.getAuthorization().getAuthorizationScopeList()
                .forEach(authorizationScope -> authorizationScopeList.add(
                        new AuthorizationScope(authorizationScope.getScope(), authorizationScope.getDescription())));
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[authorizationScopeList.size()];
        return Collections
                .singletonList(SecurityReference.builder().reference(swaggerProperties.getAuthorization().getName())
                        .scopes(authorizationScopeList.toArray(authorizationScopes)).build());
    }

    private OAuth securitySchema(SwaggerProperties swaggerProperties) {
        ArrayList<AuthorizationScope> authorizationScopeList = new ArrayList<>();
        swaggerProperties.getAuthorization().getAuthorizationScopeList()
                .forEach(authorizationScope -> authorizationScopeList.add(
                        new AuthorizationScope(authorizationScope.getScope(), authorizationScope.getDescription())));
        ArrayList<GrantType> grantTypes = new ArrayList<>();
        swaggerProperties.getAuthorization().getTokenUrlList()
                .forEach(tokenUrl -> grantTypes.add(new ResourceOwnerPasswordCredentialsGrant(tokenUrl)));
        return new OAuth(swaggerProperties.getAuthorization().getName(), authorizationScopeList, grantTypes);
    }

    private ApiInfo apiInfo(SwaggerProperties swaggerProperties) {
        return new ApiInfoBuilder()
                //标题
                .title(swaggerProperties.getTitle())
                //描述
                .description(swaggerProperties.getDescription())
                //许可证协议和协议地址
                .license(swaggerProperties.getLicense()).licenseUrl(swaggerProperties.getLicenseUrl())
                //服务条款URL
                .termsOfServiceUrl(swaggerProperties.getTermsOfServiceUrl())
                //联系方式
                .contact(new Contact(swaggerProperties.getContact().getName(), swaggerProperties.getContact().getUrl(),
                        swaggerProperties.getContact().getEmail()))
                //版本
                .version(swaggerProperties.getVersion())
                .build();
    }

}
