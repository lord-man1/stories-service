package com.place2rest.storiesservice.vo.controller.response.story;


import lombok.ToString;
import org.springframework.http.HttpStatus;
import place2.rest.toolbox.vo.entity.common.BaseResponse;

@ToString
public class CreateStoryPosterResponse extends BaseResponse {
    private final static String POSTER_ID_PROPERTY_NAME = "poster_id";

    public static CreateStoryPosterResponse success(String id) {
        CreateStoryPosterResponse response = new CreateStoryPosterResponse();

        response.addData(POSTER_ID_PROPERTY_NAME, id);

        return response;
    }

    public static CreateStoryPosterResponse error(String message, HttpStatus status) {
        CreateStoryPosterResponse response = new CreateStoryPosterResponse();

        response.serveError(message, status);

        return response;
    }

    public static CreateStoryPosterResponse error(String message, HttpStatus status, Throwable e) {
        CreateStoryPosterResponse response = new CreateStoryPosterResponse();

        response.serveError(message, status, e);

        return response;
    }
}
