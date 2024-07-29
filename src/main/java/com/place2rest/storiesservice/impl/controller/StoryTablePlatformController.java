package com.place2rest.storiesservice.impl.controller;

import com.place2rest.storiesservice.impl.orchestration.Orchestration;
import com.place2rest.storiesservice.vo.controller.request.story.ChangeStoryRequest;
import com.place2rest.storiesservice.vo.controller.response.story.*;
import com.place2rest.storiesservice.vo.enums.MediaType;
import com.place2rest.storiesservice.vo.enums.Status;
import com.place2rest.storiesservice.vo.meta.story.*;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;
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
            @RequestParam(required = false) Status status,
            @RequestParam(required = false) int offset,
            @RequestParam(required = false) int limit) {

        var request = GetStoriesRequestMeta.builder()
                .restaurantId(restaurantId)
                .status(status)
                .offset(offset)
                .limit(limit)
                .isPlatform(IS_PLATFORM)
                .token(token)
                .build();

        var response = orchestration.orchestrate(request);

        return ServiceUtils.response(response);
    }

    @CrossOrigin("http://localhost:63342")
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
    public ResponseEntity<GetStoryResponse> getStory(
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

    @GetMapping("/{storyId}/poster")
    public ResponseEntity<GetStoryPosterResponse> getStoryPoster(
            @RequestHeader(name = HeaderNames.SESSION_TOKEN, required = false)
            String token,
            @PathVariable String storyId,
            @PathVariable String restaurantId) {

        var request = GetStoryPosterRequestMeta.builder()
                .storyId(storyId)
                .restaurantId(restaurantId)
                .isPlatform(IS_PLATFORM)
                .token(token)
                .build();

        var response = orchestration.orchestrate(request);
        return ServiceUtils.response(response);
    }

    @PostMapping("/{storyId}/poster")
    public ResponseEntity<CreateStoryPosterResponse> createStoryPoster(
            @RequestHeader(name = HeaderNames.SESSION_TOKEN, required = false)
            String token,
            @PathVariable String storyId,
            @PathVariable String restaurantId,
            @RequestParam MultipartFile poster) {

        var request = CreateStoryPosterRequestMeta.builder()
                .storyId(storyId)
                .restaurantId(restaurantId)
                .poster(poster)
                .isPlatform(IS_PLATFORM)
                .token(token)
                .build();

        var response = orchestration.orchestrate(request);
        return ServiceUtils.response(response);
    }

    @CrossOrigin("http://localhost:63342")
    @GetMapping(
            value = "/{storyId}/playlist",
            produces = org.springframework.http.MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<StreamingResponseBody> getStoryPlaylist(
            @PathVariable String storyId
    ) {

        var request = GetStoryPlaylistRequestMeta.builder()
                .storyId(storyId)
                .isPlatform(IS_PLATFORM)
//                .token()
                .build();

        var response = orchestration.orchestrate(request);

        var headers = new HttpHeaders();
        headers.set("Content-Type", "application/vnd.apple.mpegurl");
        headers.set("Content-Disposition", "attachment;filename=index.m3u8");

        return new ResponseEntity<>(response.getResponseBody(), headers, HttpStatus.OK);
    }

    @CrossOrigin("http://localhost:63342")
    @GetMapping(
            value = "/{storyId}/{ts:.+}.ts",
            produces = org.springframework.http.MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<StreamingResponseBody> ts(
            @PathVariable String storyId,
            @PathVariable String ts
    ) {

        var request = GetStorySegmentRequestMeta.builder()
                .storyId(storyId)
                .tsName(ts)
                .isPlatform(IS_PLATFORM)
                .build();

        var response = orchestration.orchestrate(request);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/vnd.apple.mpegurl");
        headers.set("Content-Disposition", "attachment;filename=" + ts + ".ts");

        return new ResponseEntity<>(response.getResponseBody(), headers, HttpStatus.OK);
    }
}