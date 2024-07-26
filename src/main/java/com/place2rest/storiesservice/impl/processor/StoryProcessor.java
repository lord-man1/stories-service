package com.place2rest.storiesservice.impl.processor;

import com.place2rest.storiesservice.api.mapper.StoryMapper;
import com.place2rest.storiesservice.impl.service.database.StorySQLService;
import com.place2rest.storiesservice.impl.service.remote.RestaurantServiceStub;
import com.place2rest.storiesservice.vo.controller.response.story.ChangeStoryResponse;
import com.place2rest.storiesservice.vo.controller.response.story.CreateStoryResponse;
import com.place2rest.storiesservice.vo.controller.response.story.DeleteStoryResponse;
import com.place2rest.storiesservice.vo.controller.response.story.GetStoriesResponse;
import com.place2rest.storiesservice.vo.domain.Story;
import com.place2rest.storiesservice.vo.exception.NoSuchRestaurantException;
import com.place2rest.storiesservice.vo.exception.NoSuchStoryException;
import com.place2rest.storiesservice.vo.meta.story.*;
import com.place2rest.storiesservice.vo.view.main.StoryView;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import place2.rest.toolbox.api.storage.Storage;
import place2.rest.toolbox.impl.annotation.Log;
import place2.rest.toolbox.vo.toolbox.exception.RequestServiceException;

import java.util.List;

@Service
@Log4j2
public class StoryProcessor {
    private static final String BASE_PATH_STORY = "https://minio.dev.place2.rest/stories/";
    private final StoryMapper mapper;
    private final Storage minioStorage;
    private final RestaurantServiceStub restaurantService;
    private final StorySQLService sqlService;


    public StoryProcessor(StoryMapper mapper, Storage minioStorage, RestaurantServiceStub restaurantService, StorySQLService sqlService) {
        this.mapper = mapper;
        this.minioStorage = minioStorage;
        this.restaurantService = restaurantService;
        this.sqlService = sqlService;
    }


    // TODO: FFMpeg -> save .m3u8 / ts to MinIO
    @Log(
            event = "создание истории",
            description = "создание истории ресторана"
    )
    @Transactional(rollbackFor = Throwable.class)
    public CreateStoryResponse processCreateStory(CreateStoryRequestMeta meta) throws RequestServiceException, NoSuchRestaurantException {
        if (!restaurantService.exists(meta.getRestaurantId())) {
            throw NoSuchRestaurantException.createExceptionById(meta.getRestaurantId());
        }

        var story = Story.builder()
                        .name(meta.getName()).type(meta.getMediaType())
                        .publishDate(meta.getPublishDate())
                        .expirationDate(meta.getExpirationDate())
                        // .status()
                .restaurantId(meta.getRestaurantId())
                                .build();
        story = sqlService.save(story, meta.getRestaurantId());
        return CreateStoryResponse.success(story.getId());
    }


    @Log(
            event = "получение всех историй",
            description = "получение всех историй ресторана"
    )
    @Transactional(readOnly = true)
    public GetStoriesResponse processGetStories(GetStoriesRequestMeta meta) {
        var stories = sqlService.findAllByRestaurantId(meta.getRestaurantId());
        return GetStoriesResponse.success(stories.stream()
                .skip(meta.getOffset())
                .limit(meta.getLimit())
                .map(v -> new StoryView(
                        v.getId(), v.getName(), v.getType(),
                        v.getPublishDate(), v.getExpirationDate(),
                        v.getStatus()
                )).toList());
    }

    @Log(
            event = "получение истории",
            description = "получение истории ресторана"
    )
    @Transactional(readOnly = true)
    public GetStoriesResponse processGetStory(GetStoryRequestMeta meta) throws NoSuchStoryException {
        var story = sqlService.findByIdAndRestaurantId(
                meta.getId(), meta.getRestaurantId()
        );
        return GetStoriesResponse.success(new StoryView(
                story.getId(), story.getName(), story.getType(),
                story.getPublishDate(), story.getExpirationDate(),
                story.getStatus()));
    }

    @Log(
            event = "обновление истории",
            description = "обновление истории ресторана"
    )
    @Transactional(rollbackFor = Throwable.class)
    public ChangeStoryResponse processUpdateStory(ChangeStoryRequestMeta meta) throws NoSuchStoryException {
        var story = sqlService.findByIdAndRestaurantId(
                meta.getId(), meta.getRestaurantId()
        );
        mapper.updateStory(meta.getRequest(), story);
        sqlService.save(story, meta.getRestaurantId());
        return ChangeStoryResponse.success();
    }

    // TODO: delete .m3u8 / ts from MinIO
    @Log(
            event = "удаление истории",
            description = "удаление истории ресторана"
    )
    @Transactional(rollbackFor = Throwable.class)
    public DeleteStoryResponse processDeleteStory(DeleteStoryRequestMeta meta) {
        minioStorage.delete(List.of(BASE_PATH_STORY + meta.getId()));
        sqlService.deleteByIdAndRestaurantId(meta.getId(), meta.getRestaurantId());

        return DeleteStoryResponse.success();
    }
}
