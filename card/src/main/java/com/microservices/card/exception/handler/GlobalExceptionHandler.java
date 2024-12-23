package com.microservices.card.exception.handler;

import com.microservices.card.exception.custom.CardAlreadyExistsException;
import com.microservices.card.exception.custom.DataNotFoundException;
import com.microservices.card.service.dto.response.ErrorResponseDto;
import jakarta.validation.ConstraintViolationException;
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
        Map<String, String> validationErrors = new LinkedHashMap<>();
        validationErrors.put("apiPath", webRequest.getDescription(false));
        validationErrors.put("httpStatus", String.valueOf(status.value()));
        List<ObjectError> validationErrorList = ex.getBindingResult().getAllErrors();

        validationErrorList.forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String validationMsg = error.getDefaultMessage();
            validationErrors.put(fieldName, validationMsg);
        });
        validationErrors.put("errorTime", String.valueOf(LocalDateTime.now()));
        return new ResponseEntity<>(validationErrors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponseDto> handleConstraintViolationException(ConstraintViolationException exception) {
        ErrorResponseDto errorResponseDTO = ErrorResponseDto.builder()
                .httpStatusCode(HttpStatus.BAD_REQUEST.value())
                .message(exception.getMessage())
                .errorTime(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(errorResponseDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleResourceNotFoundException(DataNotFoundException exception) {
        ErrorResponseDto errorResponseDTO = ErrorResponseDto.builder()
                .httpStatusCode(HttpStatus.NOT_FOUND.value())
                .message(exception.getMessage())
                .errorTime(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(errorResponseDTO, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CardAlreadyExistsException.class)
    public ResponseEntity<ErrorResponseDto> handleCardAlreadyExistsException(CardAlreadyExistsException exception) {
        ErrorResponseDto errorResponseDTO = ErrorResponseDto.builder()
                .httpStatusCode(HttpStatus.CONFLICT.value())
                .message(exception.getMessage())
                .errorTime(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(errorResponseDTO, HttpStatus.CONFLICT);
    }

//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<ErrorResponseDto> handleAllException(Exception exception, WebRequest webRequest) {
//        ErrorResponseDto errorResponseDTO = ErrorResponseDto.builder()
//                .apiPath(webRequest.getDescription(false))
//                .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//                .message(exception.getMessage())
//                .errorTime(LocalDateTime.now())
//                .build();
//        return new ResponseEntity<>(errorResponseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
//    }
}
