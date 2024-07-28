package com.place2rest.storiesservice.vo.controller.response.story;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

@ToString
@Getter
@Setter
public class GetStorySegmentResponse {
    private StreamingResponseBody responseBody;

    public static GetStorySegmentResponse success(StreamingResponseBody body) {
        var response = new GetStorySegmentResponse();
        response.setResponseBody(body);
        return response;
    }

    public static GetStorySegmentResponse error(String message, HttpStatus status, Throwable e) {
        var response = new GetStorySegmentResponse();
        response.setResponseBody(null);
        return response;
    }
}
