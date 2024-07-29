package com.place2rest.storiesservice.vo.controller.response.story;

import com.place2rest.storiesservice.vo.view.main.StoryView;
import lombok.ToString;
import org.springframework.http.HttpStatus;
import place2.rest.toolbox.vo.entity.common.BaseResponse;

import java.util.List;

@ToString
public class GetStoryResponse extends BaseResponse {
    private static final String STORY_ID_PROPERTY = "id";
    private static final String STORY_NAME_PROPERTY = "name";
    private static final String STORY_TYPE_PROPERTY = "type";
    private static final String STORY_PUBLISH_PROPERTY = "publish_date";
    private static final String STORY_EXPIRATION_PROPERTY = "expiration_date";
    private static final String STORY_STATUS_PROPERTY = "status";

    public static GetStoryResponse success(StoryView table) {
        var response = new GetStoryResponse();
        response.addData(STORY_ID_PROPERTY, table.getId());
        response.addData(STORY_NAME_PROPERTY, table.getName());
        response.addData(STORY_TYPE_PROPERTY, table.getType());
        response.addData(STORY_PUBLISH_PROPERTY, table.getPublishDate());
        response.addData(STORY_EXPIRATION_PROPERTY, table.getExpirationDate());
        response.addData(STORY_STATUS_PROPERTY, table.getStatus());
        return response;
    }

    public static GetStoryResponse error(String message, HttpStatus status) {
        GetStoryResponse response = new GetStoryResponse();
        response.serveError(message, status);

        return response;
    }

    public static GetStoryResponse error(String message, HttpStatus status, Throwable e) {
        GetStoryResponse response = new GetStoryResponse();
        response.serveError(message, status);
        response.addData(PROPERTY_STACKTRACE, e.getStackTrace());
        response.addData(PROPERTY_EXCEPTION, e.getMessage());
        return response;
    }

}
