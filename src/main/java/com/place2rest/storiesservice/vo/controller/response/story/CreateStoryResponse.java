package com.place2rest.storiesservice.vo.controller.response.story;

import lombok.ToString;
import org.springframework.http.HttpStatus;
import place2.rest.toolbox.vo.entity.common.BaseResponse;

@ToString
public class CreateStoryResponse extends BaseResponse {
    private static String PROPERTY_NAME = "story_id";

    public CreateStoryResponse() {
    }

    public static CreateStoryResponse success(String imageId) {
        CreateStoryResponse response = new CreateStoryResponse();

        response.HTTP_STATUS = HttpStatus.CREATED;
        response.addData(PROPERTY_NAME, imageId);

        return response;
    }

    public static CreateStoryResponse error(String message, HttpStatus status) {
        CreateStoryResponse response = new CreateStoryResponse();
        response.serveError(message, status);

        return response;
    }

    public static CreateStoryResponse error(String message, HttpStatus status, Throwable e) {
        CreateStoryResponse response = new CreateStoryResponse();
        response.serveError(message, status);

        response.addData(PROPERTY_EXCEPTION, e.getMessage());
        response.addData(PROPERTY_STACKTRACE, e.getStackTrace());

        return response;
    }
}
