package com.place2rest.storiesservice.vo.controller.response.story;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

@ToString
@Getter
@Setter
public class GetStoryPlaylistResponse {
    private StreamingResponseBody responseBody;

    public static GetStoryPlaylistResponse success(StreamingResponseBody body) {
        var response = new GetStoryPlaylistResponse();
        response.setResponseBody(body);
        return response;
    }

    public static GetStoryPlaylistResponse error(String message, HttpStatus status, Throwable e) {
        var response = new GetStoryPlaylistResponse();
        response.setResponseBody(null);
        return response;
    }
}
