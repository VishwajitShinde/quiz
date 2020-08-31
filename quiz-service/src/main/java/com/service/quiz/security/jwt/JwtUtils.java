package com.service.quiz.security.jwt;

import com.service.quiz.security.services.UserDetailsImpl;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtils {
	private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

	@Value("${app.auth.jwt.secret:quiz-service-jwt-secret}")
	private String jwtSecret;

	@Value("${app.auth.jwt.token.expiration.seconds:600}")
	private int jwtExpirationMs;

	private String tokenValidationMessage = null;
	private int responseStatus = 401;


	public int getResponseStatus() {
		return responseStatus;
	}
	public String getTokenValidationMessage() { return this.tokenValidationMessage; }


	public String generateJwtToken(Authentication authentication) {

		UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
		Date dateForToken = new Date();

		return Jwts.builder()
				.setSubject((userPrincipal.getUsername()))
				.setIssuedAt(dateForToken)
				.setExpiration(new Date(dateForToken.getTime() + (jwtExpirationMs * 1000l)) )
				.signWith(SignatureAlgorithm.HS512, jwtSecret)
				.compact();
	}

	public String getUserNameFromJwtToken(String token) {
		return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
	}

	public boolean validateJwtToken(String authToken) {
		String exception = null ;
		this.tokenValidationMessage = null;

		/*
				200	OK
				201	Created
				401	Unauthorized
				403	Forbidden
				404	Not Found
				205 Reset Content
				415 Unsupported Media Type
				501 Not Implemented
				511 Network Authentication Required
				400 Bad Request
				408 Request Timeout
		 */

		try {
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
			this.responseStatus = 200;
			return true;
		} catch (SignatureException e) {
			logger.error("Invalid JWT signature: {}", e.getMessage());
			exception = "Invalid JWT signature: " + e.getMessage() ;
			this.responseStatus = 400;
		} catch (MalformedJwtException e) {
			logger.error("Invalid JWT token: {}", e.getMessage());
			exception = "Invalid JWT token: " + e.getMessage() ;
			this.responseStatus = 415;
		} catch (ExpiredJwtException e) {
			logger.error("JWT token is expired: {}", e.getMessage());
			exception = "JWT token is expired: " + e.getMessage() ;
			this.responseStatus = 205;
		} catch (UnsupportedJwtException e) {
			logger.error("JWT token is unsupported: {}", e.getMessage());
			exception = "JWT token is unsupported:  " + e.getMessage() ;
			this.responseStatus = 415;
		} catch (IllegalArgumentException e) {
			logger.error("JWT claims string is empty: {}", e.getMessage());
			exception = "JWT claims string is empty:  " + e.getMessage();
			this.responseStatus = 415;
		}

		if ( exception != null )
			this.tokenValidationMessage = exception;

		return false;
	}
}