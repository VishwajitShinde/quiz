package com.api.quiz.util;

import com.api.quiz.models.Password;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordValidator implements ConstraintValidator<Password, String> {

    Logger logger = LoggerFactory.getLogger(PasswordValidator.class);

    Pattern pattern =
            Pattern.compile("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[^a-zA-Z0-9])(?!.*\\s).{8,20}$");

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
       boolean valid =  validateRegex( value ) ;
       logger.info( " Password Validation Status -> {} ", valid );
        return valid;
    }

    private  boolean validateRegex( String value ) {
        Matcher matcher = pattern.matcher(value);
        return matcher.matches();
    }
}
