package com.reservation.confirmation;

import com.reservation.confirmation.exception.ReservationNotValidException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ControllerAdvice
public class ControllerExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ControllerExceptionHandler.class);

    @ExceptionHandler(ReservationNotValidException.class)
    public ResponseEntity<ApiError> badRequest(ReservationNotValidException ex) {
        LOGGER.info(ex.getMessage());
        return new ResponseEntity<>(new ApiError(ex.getMessage()), BAD_REQUEST);
    }
}
