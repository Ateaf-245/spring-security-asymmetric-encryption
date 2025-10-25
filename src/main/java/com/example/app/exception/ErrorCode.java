package com.example.app.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    USER_NOT_FOUND("USER_NOT_FOUND","User not found with id %s",HttpStatus.NOT_FOUND),
    CHANGE_PASSWORD_MISMATCH("CHANGE_PASSWORD_MISMATCH", "Confirm password and new password are not the same", HttpStatus.BAD_REQUEST),
    INVALID_CURRENT_PASSWORD("INVALID_CURRENT_PASSWORD","Current password is invalid",HttpStatus.BAD_REQUEST),
    ACCOUNT_ALREADY_DEACTIVATED("ACCOUNT_ALREADY_DEACTIVATED","Account already deactivated", HttpStatus.BAD_REQUEST),
    ACCOUNT_ALREADY_ACTIVATED("ACCOUNT_ALREADY_ACTIVATED","Account already activated", HttpStatus.BAD_REQUEST),
    EMAIL_ALREADY_EXISTS("EMAIL_ALREADY_EXISTS","Email already exists", HttpStatus.BAD_REQUEST),
    PHONE_NUMBER_ALREADY_EXISTS("PHONE_NUMBER_ALREADY_EXISTS","Phone Number already exists", HttpStatus.BAD_REQUEST),
    PASSWORD_MISMATCH("PASSWORD_MISMATCH", "Password mismatch", HttpStatus.BAD_REQUEST),
    ERR_USER_DISABLED("ERR_USER_DISABLED", "User account is disabled", HttpStatus.UNAUTHORIZED),
    BAD_CREDENTIALS("BAD_CREDENTIALS", "Invalid username/password", HttpStatus.UNAUTHORIZED),
    USERNAME_NOT_FOUND("USERNAME_NOT_FOUND", "User not found", HttpStatus.NOT_FOUND),
    INTERNAL_EXCEPTION("INTERNAL_EXCEPTION", "Internal server error", HttpStatus.INTERNAL_SERVER_ERROR),
    ;

    private final String code;
    private final String defaultMessage;
    private final HttpStatus status;

    ErrorCode(String code, String defaultMessage, HttpStatus status) {
        this.code = code;
        this.defaultMessage = defaultMessage;
        this.status = status;
    }
}
