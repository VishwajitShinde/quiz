package com.service.quiz.util;

import com.service.quiz.models.Role;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.AttributeConverter;
import java.io.IOException;
import java.util.Set;

public class RoleObjectConverter implements AttributeConverter<Set<Role>, String> {

	Logger logger = LoggerFactory.getLogger(RoleObjectConverter.class);
	ObjectMapper objectMapper = new ObjectMapper();
	 
	    @Override
	    public String convertToDatabaseColumn(Set<Role> userRoles) {
	 
	        String rolesJson = null;
	        try {
				rolesJson = objectMapper.writeValueAsString(userRoles);
				logger.info(" Roles Before Saving In Database : {}" , rolesJson );
	        } catch (final JsonProcessingException e) {
	            logger.error("JSON writing error", e);
	        }
	 
	        return rolesJson;
	    }
	 
	    @Override
	    public Set<Role> convertToEntityAttribute(String customerInfoJSON) {

			TypeReference<Set<Role>> typeRef  = new TypeReference<Set<Role>>() {};
			Set<Role> roles = null;
	        try {
				roles = objectMapper.readValue(customerInfoJSON, typeRef);
				logger.info(" Roles From Database : " + customerInfoJSON );
	        } catch (final IOException e) {
	            logger.error("JSON reading error", e);
	        }
	 
	        return roles;
	    }
	 
	}