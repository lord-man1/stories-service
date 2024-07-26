package com.place2rest.storiesservice.impl.handler;

import com.place2rest.storiesservice.impl.processor.StoryProcessor;
import com.place2rest.storiesservice.vo.controller.response.story.ChangeStoryResponse;
import com.place2rest.storiesservice.vo.controller.response.story.CreateStoryResponse;
import com.place2rest.storiesservice.vo.controller.response.story.DeleteStoryResponse;
import com.place2rest.storiesservice.vo.controller.response.story.GetStoriesResponse;
import com.place2rest.storiesservice.vo.meta.story.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import place2.rest.toolbox.vo.entity.common.exception.DefaultExceptionMessages;

@Service
@Log4j2
@RequiredArgsConstructor
public class StoryHandler {
    private final StoryProcessor processor;

    public GetStoriesResponse getStories(GetStoriesRequestMeta request) {
        GetStoriesResponse response;
        try {
            response = processor.processGetStories(request);
        } catch (Exception e) {
            response = GetStoriesResponse.error(DefaultExceptionMessages.INTERNAL_ERROR_500, HttpStatus.BAD_GATEWAY, e);
            log.error(e.getMessage(), e);
        }
        return response;
    }

    public GetStoriesResponse getStory(GetStoryRequestMeta request) {
        GetStoriesResponse response;
        try {
            response = processor.processGetStory(request);
        } catch (Exception e) {
            response = GetStoriesResponse.error(DefaultExceptionMessages.INTERNAL_ERROR_500, HttpStatus.BAD_GATEWAY, e);
            log.error(e.getMessage(), e);
        }
        return response;
    }

    public CreateStoryResponse createStory(CreateStoryRequestMeta request) {
        CreateStoryResponse response;
        try {
            response = processor.processCreateStory(request);
        } catch (Exception e) {
            response = CreateStoryResponse.error(DefaultExceptionMessages.INTERNAL_ERROR_500, HttpStatus.BAD_GATEWAY, e);
            log.error(e.getMessage(), e);
        }
        return response;
    }

    public DeleteStoryResponse deleteStory(DeleteStoryRequestMeta request) {
        DeleteStoryResponse response;
        try {
            response = processor.processDeleteStory(request);
        } catch (Exception e) {
            response = DeleteStoryResponse.error(DefaultExceptionMessages.INTERNAL_ERROR_500, HttpStatus.BAD_GATEWAY, e);
            log.error(e.getMessage(), e);
        }
        return response;
    }

    public ChangeStoryResponse changeStory(ChangeStoryRequestMeta request) {
        ChangeStoryResponse response;
        try {
            response = processor.processUpdateStory(request);
        } catch (Exception e) {
            response = ChangeStoryResponse.error(DefaultExceptionMessages.INTERNAL_ERROR_500, HttpStatus.BAD_GATEWAY, e);
            log.error(e.getMessage(), e);
        }
        return response;
    }

}
