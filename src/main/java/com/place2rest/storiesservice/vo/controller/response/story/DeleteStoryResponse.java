package com.place2rest.storiesservice.vo.controller.response.story;

import lombok.ToString;
import org.springframework.http.HttpStatus;
import place2.rest.toolbox.vo.entity.common.BaseResponse;

@ToString
public class DeleteStoryResponse extends BaseResponse {
    public DeleteStoryResponse() {
    }

    public static DeleteStoryResponse success() {
        DeleteStoryResponse response = new DeleteStoryResponse();

        response.HTTP_STATUS = HttpStatus.NO_CONTENT;

        return response;
    }

    public static DeleteStoryResponse error(String message, HttpStatus status) {
        DeleteStoryResponse response = new DeleteStoryResponse();
        response.serveError(message, status);

        return response;
    }

    public static DeleteStoryResponse error(String message, HttpStatus status, Throwable e) {
        DeleteStoryResponse response = new DeleteStoryResponse();
        response.serveError(message, status);

        response.addData(PROPERTY_EXCEPTION, e.getMessage());
        response.addData(PROPERTY_STACKTRACE, e.getStackTrace());

        return response;
    }
}
