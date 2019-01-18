package com.saaolheart.mumbai.common.errorhandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
public class ErrorControllerExceptionHandler extends ResponseEntityExceptionHandler{
	
	
	
	@ExceptionHandler(Exception.class)
	public final ResponseEntity<String> entityNotFoundException(Exception ex,WebRequest web){
		
		return new ResponseEntity<String>("Unable to find the OutPut",HttpStatus.NOT_FOUND);
	}

}
