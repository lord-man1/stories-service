package com.place2rest.storiesservice.impl.handler;

import com.place2rest.storiesservice.impl.processor.StoryProcessor;
import com.place2rest.storiesservice.vo.controller.response.story.*;
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

    public GetStoryPlaylistResponse getStoryPlaylist(GetStoryPlaylistRequestMeta request) {
        GetStoryPlaylistResponse response;
        try {
            response = processor.processGetStoryPlaylist(request);
        } catch (Exception e) {
            response = GetStoryPlaylistResponse.error(DefaultExceptionMessages.INTERNAL_ERROR_500, HttpStatus.BAD_GATEWAY, e);
            log.error(e.getMessage(), e);
        }
        return response;
    }

    public GetStorySegmentResponse getStorySegment(GetStorySegmentRequestMeta request) {
        GetStorySegmentResponse response;
        try {
            response = processor.processGetStorySegment(request);
        } catch (Exception e) {
            response = GetStorySegmentResponse.error(DefaultExceptionMessages.INTERNAL_ERROR_500, HttpStatus.BAD_GATEWAY, e);
            log.error(e.getMessage(), e);
        }
        return response;
    }

    public GetStoryPosterResponse getStoryPoster(GetStoryPosterRequestMeta request) {
        GetStoryPosterResponse response;
        try {
            response = processor.processGetStoryPoster(request);
        } catch (Exception e) {
            response = GetStoryPosterResponse.error(DefaultExceptionMessages.INTERNAL_ERROR_500, HttpStatus.BAD_GATEWAY, e);
            log.error(e.getMessage(), e);
        }
        return response;
    }

    public CreateStoryPosterResponse createStoryPoster(CreateStoryPosterRequestMeta request) {
        CreateStoryPosterResponse response;
        try {
            response = processor.processCreateStoryPoster(request);
        } catch (Exception e) {
            response = CreateStoryPosterResponse.error(DefaultExceptionMessages.INTERNAL_ERROR_500, HttpStatus.BAD_GATEWAY, e);
            log.error(e.getMessage(), e);
        }
        return response;
    }
}
