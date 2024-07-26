package com.place2rest.storiesservice.impl.controller;

import com.place2rest.storiesservice.impl.orchestration.Orchestration;
import com.place2rest.storiesservice.vo.controller.request.story.ChangeStoryRequest;
import com.place2rest.storiesservice.vo.controller.response.story.ChangeStoryResponse;
import com.place2rest.storiesservice.vo.controller.response.story.CreateStoryResponse;
import com.place2rest.storiesservice.vo.controller.response.story.DeleteStoryResponse;
import com.place2rest.storiesservice.vo.controller.response.story.GetStoriesResponse;
import com.place2rest.storiesservice.vo.enums.MediaType;
import com.place2rest.storiesservice.vo.meta.story.*;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import place2.rest.toolbox.impl.annotation.Place2RestController;
import place2.rest.toolbox.utils.ServiceUtils;
import place2.rest.toolbox.vo.entity.common.request.HeaderNames;

import java.util.Date;

@RequiredArgsConstructor
@RestController
@Place2RestController(serviceName = "stories-service")
@RequestMapping("/platform/restaurants/{restaurantId}/stories")
public class StoryTablePlatformController {
    private static final boolean IS_PLATFORM = true;

    private final Orchestration orchestration;

    @GetMapping("")
    public ResponseEntity<GetStoriesResponse> getStories(
            @RequestHeader(name = HeaderNames.SESSION_TOKEN, required = false)
            String token,
            @PathVariable String restaurantId,
            @RequestParam int offset,
            @RequestParam int limit) {

        var request = GetStoriesRequestMeta.builder()
                .restaurantId(restaurantId)
                .offset(offset)
                .limit(limit)
                .isPlatform(IS_PLATFORM)
                .token(token)
                .build();

        var response = orchestration.orchestrate(request);

        return ServiceUtils.response(response);
    }

    @PostMapping("")
    public ResponseEntity<CreateStoryResponse> saveStory(
            @RequestHeader(name = HeaderNames.SESSION_TOKEN, required = false)
            String token,
            @PathVariable String restaurantId,
            @RequestParam("name") String name,
            @RequestParam("type") MediaType type,
            @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
            @RequestParam("publish_date") Date publishDate,
            @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
            @RequestParam("expiration_date") Date expirationDate,
            @RequestParam("media") MultipartFile media) {

        var request = CreateStoryRequestMeta.builder()
                .restaurantId(restaurantId)
                .name(name)
                .publishDate(publishDate)
                .expirationDate(expirationDate)
                .mediaType(type)
                .media(media)
                .isPlatform(IS_PLATFORM)
                .token(token)
                .build();

        var response = orchestration.orchestrate(request);

        return ServiceUtils.response(response);
    }

    @GetMapping("/{storyId}")
    public ResponseEntity<GetStoriesResponse> getStory(
            @RequestHeader(name = HeaderNames.SESSION_TOKEN, required = false)
            String token,
            @PathVariable String restaurantId,
            @PathVariable String storyId) {

        var request = GetStoryRequestMeta.builder()
                .restaurantId(restaurantId)
                .id(storyId)
                .isPlatform(IS_PLATFORM)
                .token(token)
                .build();

        var response = orchestration.orchestrate(request);

        return ServiceUtils.response(response);
    }

    @PatchMapping("/{storyId}")
    public ResponseEntity<ChangeStoryResponse> updateStory(
            @RequestHeader(name = HeaderNames.SESSION_TOKEN, required = false)
            String token,
            @PathVariable String restaurantId,
            @PathVariable String storyId,
            @RequestBody ChangeStoryRequest changeRequest) {

        var request = ChangeStoryRequestMeta.builder()
                .restaurantId(restaurantId)
                .id(storyId)
                .request(changeRequest)
                .isPlatform(IS_PLATFORM)
                .token(token)
                .build();

        var response = orchestration.orchestrate(request);

        return ServiceUtils.response(response);
    }

    @DeleteMapping("/{storyId}")
    public ResponseEntity<DeleteStoryResponse> deleteStory(
            @RequestHeader(name = HeaderNames.SESSION_TOKEN, required = false)
            String token,
            @PathVariable String restaurantId,
            @PathVariable String storyId) {

        var request = DeleteStoryRequestMeta.builder()
                .restaurantId(restaurantId)
                .id(storyId)
                .isPlatform(IS_PLATFORM)
                .token(token)
                .build();

        var response = orchestration.orchestrate(request);

        return ServiceUtils.response(response);
    }



}
