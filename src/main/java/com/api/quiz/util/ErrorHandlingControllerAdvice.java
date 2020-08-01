package com.api.quiz.util;

import com.api.quiz.payload.response.ValidationErrorResponse;
import com.api.quiz.payload.response.Violation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

@ControllerAdvice
public class ErrorHandlingControllerAdvice {

  Logger logger = LoggerFactory.getLogger( ErrorHandlingControllerAdvice.class);

  @ExceptionHandler(ConstraintViolationException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ResponseBody
  ValidationErrorResponse onConstraintValidationException(
      ConstraintViolationException e) {
    ValidationErrorResponse error = new ValidationErrorResponse();
    for (ConstraintViolation violation : e.getConstraintViolations()) {
      logger.info(" Constraint Violation Field : {} , Violation Message : {} ", violation.getPropertyPath().toString(), violation.getMessage() );
      error.getViolations().add(
        new Violation(violation.getPropertyPath().toString(), violation.getMessage()));
    }
    return error;
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ResponseBody
  ValidationErrorResponse onMethodArgumentNotValidException(
      MethodArgumentNotValidException e) {
    ValidationErrorResponse error = new ValidationErrorResponse();
    for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
      logger.info(" Validation Field : {} , Validation Message : {} ", fieldError.getField(), fieldError.getDefaultMessage() );
      error.getViolations().add(
        new Violation(fieldError.getField(), fieldError.getDefaultMessage()));
    }
    return error;
  }

}
