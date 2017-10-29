package org.com.deloitte.raml.impl.exceptions;

import org.springframework.http.ResponseEntity;

public class APIException extends RuntimeException {

    public APIException() {
        super();
    }

    public APIException(String message) {
        super(message);
    }

}
