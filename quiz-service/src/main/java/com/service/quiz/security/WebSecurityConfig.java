package com.service.quiz.security;

import com.service.quiz.security.jwt.AuthEntryPointJwt;
import com.service.quiz.security.jwt.AuthTokenFilter;
import com.service.quiz.security.services.UserDetailsServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
		 // securedEnabled = true,
		// jsr250Enabled = true,
		prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	Logger logger = LoggerFactory.getLogger(WebSecurityConfig.class);

	@Autowired
	UserDetailsServiceImpl userDetailsService;

	@Autowired
	private AuthEntryPointJwt unauthorizedHandler;

	@Bean
	public AuthTokenFilter authenticationJwtTokenFilter() {
		return new AuthTokenFilter();
	}

	@Override
	public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
		authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	/**
	 * Must Notify all the controllers Starting
	 * Controllers Must Have handled Specific Request
	 * Please Look Into test Controller
	 * @param http
	 * @throws Exception
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.cors().and().csrf().disable()
			.exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
			.authorizeRequests().antMatchers("/api/auth/**").permitAll()

			.antMatchers("/test/**").permitAll()
			.antMatchers("/api/questions/**").permitAll()

			.antMatchers("/api/registerParticipant").permitAll()

			.antMatchers("/error").permitAll()
			.antMatchers("/error/**").permitAll()

				// Swagger Enabling
			.antMatchers(
					"/configuration/ui/**",
					"/resources/webjars/springfox-swagger-ui/**",
					"/webjars/**",
					"/swagger-ui.html",
					"/swagger-ui/index.html",
					"/swagger-resources/**",
					"/configuration/security/**",
					"/v3/api-docs/**",
					"/v2/api-docs/**",
					"/swagger-ui/**").permitAll()

			.anyRequest().authenticated();

		// Adding it for Https security + https
        http
                .requiresChannel()
                .anyRequest()
                .requiresSecure();



		http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

	}

	@Override
	public void configure(WebSecurity webSecurity) throws Exception
	{
		webSecurity
				.ignoring()
				// All of Spring Security will ignore the requests
				.antMatchers("/error/**");
	}
}