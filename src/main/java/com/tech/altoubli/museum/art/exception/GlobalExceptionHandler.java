package com.tech.altoubli.museum.art.exception;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.IOException;
import jakarta.mail.MessagingException;
import jakarta.servlet.ServletException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.mail.MailSendException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(MessagingException.class)
    public ResponseEntity<ErrorDto> handleMessagingException(MessagingException ex) {
        logger.error("MessagingException: ", ex);
        return new ResponseEntity<>(ErrorDto.builder().error("Messaging Error")
                .message("An internal server error occurred.")
                .build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorDto> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        logger.error("DataIntegrityViolationException: ", ex);
        return new ResponseEntity<>(
                ErrorDto.builder()
                        .error("DataIntegrityViolationException: ")
                        .message("This Email is actually has an account on the system.")
                        .build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PasswordMismatchException.class)
    public ResponseEntity<ErrorDto> handlePasswordMismatchException(PasswordMismatchException ex) {
        logger.error("PasswordMismatchException: ", ex);
        return new ResponseEntity<>(
                ErrorDto.builder()
                        .error("Password Mismatch")
                        .message(ex.getMessage())
                        .build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<ErrorDto> handleJwtException(JwtException ex) {
        logger.error("JwtException: ", ex);
        return new ResponseEntity<>(
                ErrorDto.builder()
                        .error("Failed in parsing jwt claims")
                        .message(ex.getMessage())
                        .build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorDto> handleBadCredentialsException(BadCredentialsException ex) {
        logger.error("BadCredentialsException: ", ex);
        return new ResponseEntity<>(
                ErrorDto.builder()
                        .error("Authentication Failed")
                        .message("Invalid username or password.")
                        .build(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(LockedException.class)
    public ResponseEntity<ErrorDto> handleLockedException(LockedException ex) {
        logger.error("LockedAccountException: ", ex);
        return new ResponseEntity<>(
                ErrorDto.builder()
                        .error("Locked Account")
                        .message("Your account is locked, please reach out to the admin.")
                        .build(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ErrorDto> handleUsernameNotFoundException(UsernameNotFoundException ex) {
        logger.error("UsernameNotFoundException: ", ex);
        return new ResponseEntity<>(ErrorDto.builder().error("User Not Found")
                .message(ex.getMessage())
                .build(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ExpiredTokenException.class)
    public ResponseEntity<ErrorDto> handleExpiredTokenException(ExpiredTokenException ex) {
        logger.error("ExpiredTokenException: ", ex);
        return new ResponseEntity<>(ErrorDto.builder().error("Expired Token")
                .message("The token has expired.")
                .build(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorDto> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex) {
        logger.error("HTTP request method not supported: {}", ex.getMessage());
        return new ResponseEntity<>(ErrorDto.builder()
                .error("Unsupported HTTP method.")
                .message("HTTP request method not supported. Please check the request method.")
                .build(), HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorDto> handleHttpMessageNotReadableException(DateFormatException ex) {
        logger.error("HttpMessageNotReadableException: ", ex);
        return new ResponseEntity<>(
                ErrorDto.builder()
                        .error("HttpMessageNotReadableException")
                        .message("You're request body doesn't match the API requirements")
                        .build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DateFormatException.class)
    public ResponseEntity<ErrorDto> handleDateFormatException(DateFormatException ex) {
        logger.error("DateFormatException: ", ex);
        return new ResponseEntity<>(
                ErrorDto.builder()
                .error("The date is not formatted properly")
                .message("please follow the following format: yyyy-MM-dd")
                .build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NonAuthorizedActionException.class)
    public ResponseEntity<ErrorDto> handleNonAuthorizedActionException(InvalidTokenException ex) {
        logger.error("NonAuthorizedActionException: ", ex);
        return new ResponseEntity<>(ErrorDto.builder().error("Invalid Token")
                .message("You are not authorized to perform this action.")
                .build(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<ErrorDto> handleInvalidTokenException(InvalidTokenException ex) {
        logger.error("InvalidTokenException: ", ex);
        return new ResponseEntity<>(ErrorDto.builder().error("Invalid Token")
                .message("The token is invalid.")
                .build(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(MailSendException.class)
    public ResponseEntity<ErrorDto> handleMailSendException(MailSendException ex) {
        logger.error("MailSendException: ", ex);
        return new ResponseEntity<>(ErrorDto.builder().error("MailSendException")
                .message("Mail server is OFF.")
                .build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ServletException.class)
    public ResponseEntity<ErrorDto> handleServletException(ServletException ex) {
        logger.error("ServletException: ", ex);
        return new ResponseEntity<>(ErrorDto.builder().error("Servlet Error")
                .message("An internal server error occurred.")
                .build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<ErrorDto> handleIOException(IOException ex) {
        logger.error("IOException: ", ex);
        return new ResponseEntity<>(ErrorDto.builder().error("I/O Error")
                .message("An internal server error occurred.")
                .build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MalformedJwtException.class)
    public ResponseEntity<ErrorDto> handleMalformedJwtException(MalformedJwtException ex) {
        logger.error("MalformedJwtException: ", ex);
        return new ResponseEntity<>(ErrorDto.builder().error("Malformed JWT")
                .message("The provided JWT is malformed.")
                .build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ErrorDto> handleExpiredJwtException(ExpiredJwtException ex) {
        logger.error("ExpiredJwtException: ", ex);
        return new ResponseEntity<>(ErrorDto.builder().error("Expired JWT")
                .message("The JWT has expired.")
                .build(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(UnsupportedJwtException.class)
    public ResponseEntity<ErrorDto> handleUnsupportedJwtException(UnsupportedJwtException ex) {
        logger.error("UnsupportedJwtException: ", ex);
        return new ResponseEntity<>(ErrorDto.builder().error("Unsupported JWT")
                .message("The JWT is unsupported.")
                .build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorDto> handleIllegalArgumentException(IllegalArgumentException ex) {
        logger.error("IllegalArgumentException: ", ex);
        return new ResponseEntity<>(ErrorDto.builder().error("Illegal Argument")
                .message("An illegal argument was provided.")
                .build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<ErrorDto> handleDisabledException(DisabledException ex) {
        logger.error("Disabled exception: ", ex);
        return new ResponseEntity<>(ErrorDto.builder().error("The account is not active")
                .message("Please Confirm your account through the token sent to your email.")
                .build(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorDto> handleAuthenticationException(AuthenticationException ex) {
        logger.error("Authentication exception: ", ex);
        return new ResponseEntity<>(ErrorDto.builder().error("Credential does not match")
                .message("Authentication failed.")
                .build(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDto> handleGlobalException(Exception ex) {
        logger.error("Exception: ", ex);
        return new ResponseEntity<>(ErrorDto.builder().error("Error")
                .message("An internal server error occurred.")
                .build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}