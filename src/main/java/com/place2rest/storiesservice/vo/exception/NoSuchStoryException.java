package com.place2rest.storiesservice.vo.exception;

import org.springframework.http.HttpStatus;
import place2.rest.toolbox.vo.toolbox.exception.base.BaseException;

public class NoSuchStoryException extends BaseException {
    private NoSuchStoryException(String message, HttpStatus code) {
        super(message, code);
    }

    public static NoSuchStoryException createExceptionById(String id) {
        return new NoSuchStoryException("no such restaurant " + id, HttpStatus.NOT_FOUND);
    }
}
