package com.api.quiz.security.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.api.quiz.security.services.UserDetailsServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;


public class AuthTokenFilter extends OncePerRequestFilter {
	@Autowired
	private JwtUtils jwtUtils;

	@Autowired
	private UserDetailsServiceImpl userDetailsService;

	private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		try {
			String jwt = parseJwt(request);
			if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
				String username = jwtUtils.getUserNameFromJwtToken(jwt);
				logger.info("Token Validated..! UserName From Token : {}", username );
				UserDetails userDetails = userDetailsService.loadUserByUsername(username);
				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

				SecurityContextHolder.getContext().setAuthentication(authentication);
				logger.info( " Authentication Status : {} ", authentication ) ;
			}

			response.setHeader("customError", jwtUtils.getTokenValidationMessage() );
		} catch (Exception e) {
			logger.error("Cannot set user authentication: {}", e);
			response.setHeader("customError","Cannot set user authentication: " +  e.getMessage() );
		}

		request.setAttribute("statusCode", jwtUtils.getResponseStatus()  );
		filterChain.doFilter(request, response);
	}

	/**
	 * Parsing And Getting Token
	 * In both Ways either Query param 'access_token' Or Authorization Header
	 * With Bearer
	 * @param request
	 * @return
	 */
	private String parseJwt(HttpServletRequest request) {
		String headerAuth = request.getHeader("Authorization");

		logger.info(" headerAuth [ Authorization : Bearer ] Token : {} ", headerAuth );

		if ( StringUtils.hasText(headerAuth) ) {
			return headerAuth.startsWith("Bearer ") ?  headerAuth.substring(7, headerAuth.length()) :  headerAuth;
		}

		String paramAuth = request.getParameterMap().containsKey("access_token")
				? String.valueOf(request.getParameterMap().get("access_token")) : null;

		logger.info(" paramAuth [ access_token ] Token : {} ", headerAuth );

		if (StringUtils.hasText(paramAuth) ) {
			return paramAuth;
		}

		return null;
	}
}