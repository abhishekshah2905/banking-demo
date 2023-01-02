package com.poc.banking.exceptions;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.ZonedDateTimeSerializer;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

/**
 * Record <code>HttpErrorInfo</code> which encapsulate all HTTP errors sent to client.
 */
public record HttpErrorInfo(
        @JsonProperty("status") HttpStatus httpStatus,
        @JsonProperty("message") String message,
        @JsonProperty("timestamp")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd@HH:mm:ss.SSSZ")
        @JsonSerialize(using = ZonedDateTimeSerializer.class)
        ZonedDateTime timestamp) {

    /**
     * Instantiates a new Http error info.
     *
     * @param httpStatus the http status code and type.
     * @param message    the error message.
     */
    public HttpErrorInfo(HttpStatus httpStatus, String message) {
        this(httpStatus, message, ZonedDateTime.now());
    }
}
