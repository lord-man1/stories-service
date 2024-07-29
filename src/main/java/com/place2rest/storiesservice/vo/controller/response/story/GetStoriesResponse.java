package com.place2rest.storiesservice.vo.controller.response.story;

import com.place2rest.storiesservice.vo.view.main.StoryView;
import lombok.ToString;
import org.springframework.http.HttpStatus;
import place2.rest.toolbox.vo.entity.common.BaseResponse;

import java.util.List;

@ToString
public class GetStoriesResponse extends BaseResponse {
    private static final String RESTAURANT_ID_PROPERTY = "restaurant_id";
    private static final String STORIES_PROPERTY = "items";
    private static final String TOTAL_STORIES_PROPERTY = "total";

    public GetStoriesResponse() {
    }

    public static GetStoriesResponse success(String restaurantId,
                                             List<StoryView> tables,
                                             int total) {
        var response = new GetStoriesResponse();
        response.addData(RESTAURANT_ID_PROPERTY, restaurantId);
        response.addData(STORIES_PROPERTY, tables);
        response.addData(TOTAL_STORIES_PROPERTY, total);
        return response;
    }

    public static GetStoriesResponse success(String restaurantId,
                                             StoryView table,
                                             int total) {
        return success(restaurantId, List.of(table), total);
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
