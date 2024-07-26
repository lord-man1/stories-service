package com.place2rest.storiesservice.vo.controller.response.story;

import com.place2rest.storiesservice.vo.view.main.StoryView;
import lombok.ToString;
import org.springframework.http.HttpStatus;
import place2.rest.toolbox.vo.entity.common.BaseResponse;

import java.util.List;

@ToString
public class GetStoriesResponse extends BaseResponse {
    private static final String PROPERTY_NAME = "items";

    public GetStoriesResponse() {
    }

    public static GetStoriesResponse success(List<StoryView> tables) {
        var response = new GetStoriesResponse();
        response.addData(PROPERTY_NAME, tables);

        return response;
    }

    public static GetStoriesResponse success(StoryView table) {
        var response = new GetStoriesResponse();
        response.addData(PROPERTY_NAME, List.of(table));

        return response;
    }

    public static GetStoriesResponse error(String message, HttpStatus status) {
        GetStoriesResponse response = new GetStoriesResponse();
        response.serveError(message, status);

        return response;
    }

    public static GetStoriesResponse error(String message, HttpStatus status, Throwable e) {
        GetStoriesResponse response = new GetStoriesResponse();
        response.serveError(message, status);
        response.addData(PROPERTY_STACKTRACE, e.getStackTrace());
        response.addData(PROPERTY_EXCEPTION, e.getMessage());
        return response;
    }
}
