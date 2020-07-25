package com.api.quiz.security.jwt;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class AuthEntryPointJwt implements AuthenticationEntryPoint {

	private static final Logger logger = LoggerFactory.getLogger(AuthEntryPointJwt.class);

	private static String CUSTOM_HEADER = "customError" ;
	private static String STATUS_CODE = "statusCode" ;


	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException {

		String errorMessage =  response.containsHeader( CUSTOM_HEADER ) ? response.getHeader(CUSTOM_HEADER ) : null ;
		int statusCode =  response.containsHeader( STATUS_CODE ) ? Integer.parseInt(response.getHeader( STATUS_CODE )) : HttpServletResponse.SC_UNAUTHORIZED ;

		logger.error("Unauthorized error: {}, [ {} ] Response Custom Header ( customError ) : {} ",
				authException.getMessage(), statusCode, errorMessage );

		final String message =  StringUtils.hasText(errorMessage) ? errorMessage : authException.getMessage();
		response.setStatus(statusCode);
		//response.sendError( HttpServletResponse.SC_UNAUTHORIZED,  message  );
		response.sendError( statusCode,  message  );
	}

}