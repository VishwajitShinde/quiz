package com.api.quiz.security.jwt;

import com.api.quiz.security.services.UserDetailsImpl;
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

		try {
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
			return true;
		} catch (SignatureException e) {
			logger.error("Invalid JWT signature: {}", e.getMessage());
			exception = "Invalid JWT signature: " + e.getMessage() ;
		} catch (MalformedJwtException e) {
			logger.error("Invalid JWT token: {}", e.getMessage());
			exception = "Invalid JWT token: " + e.getMessage() ;
		} catch (ExpiredJwtException e) {
			logger.error("JWT token is expired: {}", e.getMessage());
			exception = "JWT token is expired: " + e.getMessage() ;
		} catch (UnsupportedJwtException e) {
			logger.error("JWT token is unsupported: {}", e.getMessage());
			exception = "JWT token is unsupported:  " + e.getMessage() ;
		} catch (IllegalArgumentException e) {
			logger.error("JWT claims string is empty: {}", e.getMessage());
			exception = "JWT claims string is empty:  " + e.getMessage() ;
		}

		if ( exception != null )
			this.tokenValidationMessage = exception;

		return false;
	}
}