package com.place2rest.storiesservice.vo.exception;

import org.springframework.http.HttpStatus;
import place2.rest.toolbox.vo.toolbox.exception.base.BaseException;

public class NoSuchRestaurantException extends BaseException {

    private NoSuchRestaurantException(String message, HttpStatus code) {
        super(message, code);
    }

    public static NoSuchRestaurantException createExceptionById(String id) {
        return new NoSuchRestaurantException("no such restaurant " + id, HttpStatus.NOT_FOUND);
    }
}
