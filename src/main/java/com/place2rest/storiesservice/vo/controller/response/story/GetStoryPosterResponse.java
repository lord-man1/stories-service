package com.place2rest.storiesservice.vo.controller.response.story;

import lombok.ToString;
import org.springframework.http.HttpStatus;
import place2.rest.toolbox.vo.entity.common.BaseResponse;

@ToString
public class GetStoryPosterResponse extends BaseResponse {
    private final static String SRC_PROPERTY_NAME = "src";
    private final static String IMAGE_ID_PROPERTY_NAME = "image_id";

    public static GetStoryPosterResponse success(String src,String imageId) {
        GetStoryPosterResponse response = new GetStoryPosterResponse();

        response.addData(SRC_PROPERTY_NAME,src);
        response.addData(IMAGE_ID_PROPERTY_NAME,imageId);

        return response;
    }

    public static GetStoryPosterResponse error(String message, HttpStatus status) {
        GetStoryPosterResponse response = new GetStoryPosterResponse();

        response.serveError(message, status);

        return response;
    }

    public static GetStoryPosterResponse error(String message, HttpStatus status, Throwable e) {
        GetStoryPosterResponse response = new GetStoryPosterResponse();

        response.serveError(message, status, e);

        return response;
    }
}
