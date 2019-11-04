package com.party.entry.reservationentry;

import com.party.entry.reservationentry.exception.ReservationNotValidException;
import com.party.entry.reservationentry.exception.SecretAlreadyExistsException;
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
    public ResponseEntity<ApiError> handleSecretNotValid(ReservationNotValidException e) {
        LOGGER.info(e.getMessage());
        return new ResponseEntity<>(new ApiError("Reservation Secret is not set"), BAD_REQUEST);
    }

    @ExceptionHandler(SecretAlreadyExistsException.class)
    public ResponseEntity<ApiError> handleSecretAlreadyExists(SecretAlreadyExistsException e) {
        LOGGER.info(e.getMessage());
        return new ResponseEntity<>(new ApiError(e.getMessage()), BAD_REQUEST);
    }
}
