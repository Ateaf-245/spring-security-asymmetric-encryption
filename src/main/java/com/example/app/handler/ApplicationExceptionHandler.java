package com.example.app.handler;

import com.example.app.exception.BusinessException;
import com.example.app.exception.ErrorCode;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class ApplicationExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusinessException(final BusinessException ex) {
        final ErrorResponse body = ErrorResponse.builder()
                .code(ex.getErrorCode().getCode())
                .message(ex.getMessage())
                .build();

        log.info("Business exception: {}", ex.getMessage());
        log.debug(ex.getMessage(), ex);

        return ResponseEntity.status(ex.getErrorCode().getStatus() != null
                        ? ex.getErrorCode().getStatus() : HttpStatus.BAD_REQUEST)
                .body(body);
    }

    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<ErrorResponse> handleDisabledException(final DisabledException ex) {
        final ErrorResponse body = ErrorResponse.builder()
                .code(ErrorCode.ERR_USER_DISABLED.getCode())
                .message(ErrorCode.ERR_USER_DISABLED.getDefaultMessage())
                .build();

        return ResponseEntity.status(ErrorCode.ERR_USER_DISABLED.getStatus())
                .body(body);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleBadCredentialsException(final BadCredentialsException ex) {
        log.debug(ex.getMessage(), ex);

        final ErrorResponse body = ErrorResponse.builder()
                .code(ErrorCode.BAD_CREDENTIALS.getCode())
                .message(ErrorCode.BAD_CREDENTIALS.getDefaultMessage())
                .build();

        return ResponseEntity.status(ErrorCode.BAD_CREDENTIALS.getStatus())
                .body(body);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUsernameNotFoundException(final UsernameNotFoundException ex) {
        log.debug(ex.getMessage(), ex);

        final ErrorResponse body = ErrorResponse.builder()
                .code(ErrorCode.USERNAME_NOT_FOUND.getCode())
                .message(ErrorCode.USERNAME_NOT_FOUND.getDefaultMessage())
                .build();

        return ResponseEntity.status(ErrorCode.USERNAME_NOT_FOUND.getStatus())
                .body(body);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEntityNotFoundException(final EntityNotFoundException ex) {
        log.debug(ex.getMessage(), ex);

        final ErrorResponse body = ErrorResponse.builder()
                .code("TBD")
                .message(ex.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(body);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(final MethodArgumentNotValidException ex) {
        log.debug(ex.getMessage(), ex);

        final List<ErrorResponse.ValidationError> errors = new ArrayList<>();

        ex.getBindingResult()
                .getAllErrors()
                .forEach(err ->
                        errors.add(ErrorResponse.ValidationError.builder()
                                .field(((FieldError) err).getField())
                                .code(err.getCode())
                                .message(err.getDefaultMessage())
                                .build())
                );

        final ErrorResponse errorResponse = ErrorResponse.builder()
                .validationErrors(errors)
                .build();

        return ResponseEntity.status((HttpStatus.BAD_REQUEST)).body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleOtherException(final Exception ex) {
        log.debug(ex.getMessage(), ex);

        final ErrorResponse body = ErrorResponse.builder()
                .code(ErrorCode.INTERNAL_EXCEPTION.getCode())
                .message(ErrorCode.INTERNAL_EXCEPTION.getDefaultMessage())
                .build();

        return ResponseEntity.status(ErrorCode.INTERNAL_EXCEPTION.getStatus())
                .body(body);
    }

}
