package com.api.quiz.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

@Configuration
@EnableResourceServer
public class OAuthResourceServerConfiguration extends ResourceServerConfigurerAdapter {

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.resourceId("my-resource");
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.anonymous().and()
                .authorizeRequests()
                .antMatchers("/", "/lib/*", "/images/*", "/css/*", "/swagger-ui.js","/swagger-ui.min.js", "/api-docs", "/fonts/*", "/api-docs/*", "/api-docs/default/*", "/o2c.html","index.html","/webjars/**","/hystrix/**").permitAll()
                .antMatchers(HttpMethod.GET, "/api/*").access("#oauth2.hasScope('read')")
                .antMatchers(HttpMethod.POST, "/register", "/authenticate").permitAll()
                .anyRequest().authenticated();
    }

}