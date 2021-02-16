package com.reservation.entry.exception;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;

import java.util.Map;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Component
public class GlobalErrorAttributes extends DefaultErrorAttributes {

    @Override
    public Map<String, Object> getErrorAttributes(ServerRequest request, ErrorAttributeOptions options) {
        Map<String, Object> errorAttributes = super.getErrorAttributes(request, options);
        Throwable throwable = getError(request);
        if (throwable instanceof ReservationNotValidException){
            errorAttributes.put("status", BAD_REQUEST.value());
            errorAttributes.put("error", BAD_REQUEST.getReasonPhrase());
            errorAttributes.put("message", "Reservation cannot be empty!");
            return errorAttributes;
        }

        return errorAttributes;
    }
}