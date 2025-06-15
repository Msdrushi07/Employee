package com.employee.global;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.employee.exception.ApiResponse;
import com.employee.exception.EmployeeAlreadyExist;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(EmployeeAlreadyExist.class)
	public ResponseEntity<ApiResponse>  employeeAlreadyExistHandler(EmployeeAlreadyExist exception){
		ApiResponse apiResponse = new ApiResponse(exception.getMessage(),HttpStatus.ALREADY_REPORTED);
		return new ResponseEntity<>(apiResponse,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Object> MethodargumentexceptionHandler(MethodArgumentNotValidException exception){
		Map<String,String> resp=new HashMap<>();
		exception.getBindingResult().getAllErrors().forEach((a) -> {
			String fieldname= ((FieldError) a).getField();
			String message=a.getDefaultMessage();
			resp.put(fieldname,message);
		});
		return new ResponseEntity<Object>(resp,HttpStatus.BAD_REQUEST);
		
	}

}
