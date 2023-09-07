package org.smg.carlisting.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smg.carlisting.controller.dto.RestApiErrorResponseDto;
import org.smg.carlisting.exception.CarListingNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class CarListingControllerExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(CarListingControllerExceptionHandler.class);


    @ExceptionHandler(CarListingNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ResponseBody
    RestApiErrorResponseDto handleEntityNotFoundException(CarListingNotFoundException exception) {
        LOGGER.error("Car listing not found exception", exception);
        return new RestApiErrorResponseDto(HttpStatus.NOT_FOUND.name(), exception.getMessage());
    }

}
