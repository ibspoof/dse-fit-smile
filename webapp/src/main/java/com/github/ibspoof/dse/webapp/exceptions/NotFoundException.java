package com.github.ibspoof.dse.webapp.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class NotFoundException extends Exception {

    public NotFoundException(String msg) {
        super(msg);
    }
}
