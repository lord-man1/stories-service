package com.place2rest.storiesservice.impl.orchestration;

import com.place2rest.storiesservice.impl.handler.StoryHandler;
import com.place2rest.storiesservice.vo.controller.response.story.ChangeStoryResponse;
import com.place2rest.storiesservice.vo.controller.response.story.CreateStoryResponse;
import com.place2rest.storiesservice.vo.controller.response.story.DeleteStoryResponse;
import com.place2rest.storiesservice.vo.controller.response.story.GetStoriesResponse;
import com.place2rest.storiesservice.vo.meta.story.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class Orchestration {
    private final StoryHandler storyHandler;

    public GetStoriesResponse orchestrate(GetStoriesRequestMeta request) {
        return storyHandler.getStories(request);
    }

    public CreateStoryResponse orchestrate(CreateStoryRequestMeta request) {
        return storyHandler.createStory(request);
    }

    public GetStoriesResponse orchestrate(GetStoryRequestMeta request) {
        return storyHandler.getStory(request);
    }

    public ChangeStoryResponse orchestrate(ChangeStoryRequestMeta request) {
        return storyHandler.changeStory(request);
    }

    public DeleteStoryResponse orchestrate(DeleteStoryRequestMeta request) {
        return storyHandler.deleteStory(request);
    }
}
