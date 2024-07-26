package com.place2rest.storiesservice.vo.controller.response.story;

import org.springframework.http.HttpStatus;
import place2.rest.toolbox.vo.entity.common.BaseResponse;
import place2.rest.toolbox.vo.entity.common.request.StatusCodes;

public class ChangeStoryResponse extends BaseResponse {
    public ChangeStoryResponse() {
    }

    public static ChangeStoryResponse success() {
        var response = new ChangeStoryResponse();
        response.HTTP_STATUS = StatusCodes.UPDATED;

        return response;
    }

    public static ChangeStoryResponse error(String message, HttpStatus status) {
        ChangeStoryResponse response = new ChangeStoryResponse();
        response.serveError(message, status);

        return response;
    }

    public static ChangeStoryResponse error(String message, HttpStatus status, Throwable e) {
        ChangeStoryResponse response = new ChangeStoryResponse();
        response.serveError(message, status);

        response.addData(PROPERTY_EXCEPTION, e.getMessage());
        response.addData(PROPERTY_STACKTRACE, e.getStackTrace());

        return response;
    }
}
