package com.api.quiz.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import springfox.documentation.builders.AuthorizationCodeGrantBuilder;
import springfox.documentation.builders.OAuthBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger.web.SecurityConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

	private final Logger log = LoggerFactory.getLogger(SwaggerConfiguration.class);

	@Value("${client.id}")
	private String CLIENT_ID;

	@Value("${auth.secret}")
	private String CLIENT_SECRET;

	@Value("${auth.server}")
	private String  AUTH_SERVER;

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.api.quiz.controller"))
                .paths(PathSelectors.any())
                .build()
				.apiInfo(apiInfo())
				.securitySchemes(Arrays.asList(securityScheme()))
				.securityContexts(Arrays.asList(securityContext()));
    }

	private ApiInfo apiInfo() {
		return new ApiInfo(
				"QUIZ-SERVICE REST API",
				"Is getting is used quiz-app API.",
				"API Version 0.0.2",
				"Terms of service",
				new Contact("Dhammdip Or Vishwajit", "www.quiz-app.com", "dsvs@NoNameCompany.com"),
				"License of API", "API license URL", Collections.emptyList());
	}

	@Bean
	public SecurityConfiguration security() {
		return SecurityConfigurationBuilder.builder()
				.clientId(CLIENT_ID)
				.clientSecret(CLIENT_SECRET)
				.scopeSeparator(" ")
				.useBasicAuthenticationWithAccessCodeGrant(true)
				.build();
	}

	private SecurityScheme securityScheme() {
		GrantType grantType = new AuthorizationCodeGrantBuilder()
				.tokenEndpoint(new TokenEndpoint(AUTH_SERVER + "/token", "oauthtoken"))
				.tokenRequestEndpoint(
						new TokenRequestEndpoint(AUTH_SERVER + "/authorize", CLIENT_ID, CLIENT_SECRET))
				.build();

		SecurityScheme oauth = new OAuthBuilder().name("spring_oauth")
				.grantTypes(Arrays.asList(grantType))
				.scopes(Arrays.asList(scopes()))
				.build();
		return oauth;
	}

	private AuthorizationScope[] scopes() {
		AuthorizationScope[] scopes = {
				new AuthorizationScope("read", "for read operations"),
				new AuthorizationScope("write", "for write operations"),
				new AuthorizationScope("*", "Access All API") };
		return scopes;
	}

	private SecurityContext securityContext() {
		return SecurityContext.builder()
				.securityReferences(
						Arrays.asList(new SecurityReference("spring_oauth", scopes())))
				.forPaths(PathSelectors.regex("/*.*"))
				.build();
	}

}