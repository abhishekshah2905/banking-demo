package com.poc.banking.config;

import com.poc.banking.exceptions.BankAccountActionInvalidException;
import com.poc.banking.exceptions.HttpErrorInfo;
import com.poc.banking.exceptions.InvalidInputException;
import com.poc.banking.exceptions.NotFoundException;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.*;

/**
 * The class Global controller exception handler is a generic and central point for all
 * microservices that handles all services exceptions.
 *
 * <p>It act as filter so it is pluggable component just added to microservice context
 * automatically, when you add <code>ComponentScan</code> on your application.
 *
 * @see org.springframework.context.annotation.ComponentScan
 */
@RestControllerAdvice
@Log4j2
class GlobalRestExceptionHandler {

    /**
     * Method to handle <i>Not found exceptions</i> http error info.
     *
     * @param ex the ex to get its information
     * @return the http error information.
     */
    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public @ResponseBody
    HttpErrorInfo handleNotFoundExceptions(Exception ex) {
        return createHttpErrorInfo(NOT_FOUND, ex);
    }

    /**
     * Method to handle <i>invalid input exception</i> http error info.
     */
    @ResponseStatus(UNPROCESSABLE_ENTITY)
    @ExceptionHandler(InvalidInputException.class)
    public @ResponseBody
    HttpErrorInfo handleInvalidInputException(Exception ex) {
        return createHttpErrorInfo(UNPROCESSABLE_ENTITY, ex);
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(BankAccountActionInvalidException.class)
    public @ResponseBody
    HttpErrorInfo handleBankAccountAction(Exception ex) {
        return createHttpErrorInfo(BAD_REQUEST, ex);
    }

    @ResponseStatus(UNPROCESSABLE_ENTITY)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public @ResponseBody
    HttpErrorInfo handleUnexpectedError(DataIntegrityViolationException ex) {
        return createHttpErrorInfo(CONFLICT, ex);
    }

    private HttpErrorInfo createHttpErrorInfo(
            HttpStatus httpStatus, Exception ex) {
        final var message = ex.getMessage();
        log.debug("Returning HTTP status: {}, message: {}", httpStatus, message);
        return new HttpErrorInfo(httpStatus, message);
    }
}
