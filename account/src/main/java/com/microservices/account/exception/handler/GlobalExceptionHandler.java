package com.microservices.account.exception.handler;

import com.microservices.account.exception.custom.CustomerAlreadyExistsException;
import com.microservices.account.exception.custom.DataNotFoundException;
import com.microservices.account.service.dto.response.ErrorResponseDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest webRequest) {
        Map<String, Object> validationErrors = new LinkedHashMap<>();
        Map<String, String> errors = new LinkedHashMap<>();
        validationErrors.put("httpStatus", String.valueOf(status.value()));
        List<ObjectError> validationErrorList = ex.getBindingResult().getAllErrors();

        validationErrorList.forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String validationMsg = error.getDefaultMessage();
            errors.put(fieldName, validationMsg);
        });
        validationErrors.put("errors", errors);
        return new ResponseEntity<>(validationErrors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponseDto> handleIllegalArgumentException(IllegalArgumentException exception) {
        ErrorResponseDto errorResponseDTO = ErrorResponseDto.builder()
                .httpStatusCode(HttpStatus.BAD_REQUEST.value())
                .message(exception.getMessage())
                .errorTime(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(errorResponseDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleDataNotFoundException(DataNotFoundException exception) {
        ErrorResponseDto errorResponseDTO = ErrorResponseDto.builder()
                .httpStatusCode(HttpStatus.NOT_FOUND.value())
                .message(exception.getMessage())
                .errorTime(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(errorResponseDTO, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CustomerAlreadyExistsException.class)
    public ResponseEntity<ErrorResponseDto> handleEmailAlreadyExists(CustomerAlreadyExistsException exception) {
        ErrorResponseDto errorResponseDTO = ErrorResponseDto.builder()
                .httpStatusCode(HttpStatus.CONFLICT.value())
                .message(exception.getMessage())
                .errorTime(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(errorResponseDTO, HttpStatus.CONFLICT);
    }

//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<ErrorResponseDto> handleGlobalException(Exception exception) {
//        ErrorResponseDto errorResponseDTO = ErrorResponseDto.builder()
//                .httpStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
//                .message(exception.getMessage())
//                .errorTime(LocalDateTime.now())
//                .build();
//        return new ResponseEntity<>(errorResponseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
//    }

//    @ExceptionHandler(ExpiredJwtException.class)
//    public ResponseEntity<ErrorResponseDto> handleExpiredJwtException(ExpiredJwtException exception) {
//        ErrorResponseDto errorResponseDTO = ErrorResponseDto.builder()
//                .httpStatus(HttpStatus.UNAUTHORIZED)
//                .message(exception.getMessage())
//                .errorTime(LocalDateTime.now())
//                .build();
//        return new ResponseEntity<>(errorResponseDTO, HttpStatus.UNAUTHORIZED);
//    }
}
