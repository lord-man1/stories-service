package com.place2rest.storiesservice.impl.processor;

import com.place2rest.storiesservice.api.mapper.StoryMapper;
import com.place2rest.storiesservice.impl.service.database.StorySQLService;
import com.place2rest.storiesservice.impl.service.remote.RestaurantServiceStub;
import com.place2rest.storiesservice.vo.controller.response.story.*;
import com.place2rest.storiesservice.vo.domain.Story;
import com.place2rest.storiesservice.vo.exception.NoSuchRestaurantException;
import com.place2rest.storiesservice.vo.exception.NoSuchStoryException;
import com.place2rest.storiesservice.vo.meta.story.*;
import com.place2rest.storiesservice.vo.view.main.StoryView;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import place2.rest.toolbox.api.storage.Storage;
import place2.rest.toolbox.impl.annotation.Log;
import place2.rest.toolbox.vo.toolbox.exception.RequestServiceException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static java.lang.String.format;

@Service
@Log4j2
public class StoryProcessor {
    private static final String BASE_PATH_STORY = "https://minio.dev.place2.rest/stories/";
    private final StoryMapper mapper;
    private final Storage minioStorage;
    private final RestaurantServiceStub restaurantService;
    private final StorySQLService sqlService;
    private final Path tempDir;

    public StoryProcessor(StoryMapper mapper, Storage minioStorage,
                          RestaurantServiceStub restaurantService, StorySQLService sqlService,
                          @Value("/home/roman/Документы/TMP_FOLDER") String tempDir) {
        this.mapper = mapper;
        this.minioStorage = minioStorage;
        this.restaurantService = restaurantService;
        this.sqlService = sqlService;
        this.tempDir = Paths.get(tempDir);
    }

    // TODO: add POSTER by GENERATING it as SCREENSHOT OF VIDEO
    @Log(
            event = "создание истории",
            description = "создание истории ресторана"
    )
    @Transactional(rollbackFor = Throwable.class)
    public CreateStoryResponse processCreateStory(CreateStoryRequestMeta meta) throws RequestServiceException, NoSuchRestaurantException, IOException, InterruptedException {
        validateRestaurantExists(meta.getRestaurantId());

        var story = createStory(meta);
        story = saveStory(story, meta.getRestaurantId());

        var videoId = story.getId();
        var videoTmpDir = createVideoTmpDir(videoId);
        var tmpFile = createTmpFile(videoId, videoTmpDir, meta.getMedia());

        var workDir = createHlsWorkDir(videoTmpDir);
        executeFfmpegCommand(tmpFile, workDir);

        saveGeneratedFiles(workDir, videoId);

        FileUtils.deleteDirectory(videoTmpDir.toFile());

        return CreateStoryResponse.success(story.getId());
    }

    private Path collectIndexFile(Path folder) throws IOException {
        Path indexFile;
        try (var files = Files.list(folder)) {
            indexFile = files
                    .filter(path -> path.toString().endsWith(".m3u8"))
                    .findFirst()
                    .orElseThrow();
        }
        return indexFile;
    }

    private void saveGeneratedFiles(Path fileSource, String videoId) throws IOException {
        var m3u8File = collectIndexFile(fileSource);
        var tsFiles = collectSegmentFiles(fileSource);
        minioStorage.save(
                format("%s.m3u8", videoId),
                Files.readAllBytes(m3u8File)
        );
        for (var ts : tsFiles) {
            minioStorage.save(
                    format("%s%s.ts", videoId, ts.getFileName()),
                    Files.readAllBytes(ts)
            );
        }
    }

    private List<Path> collectSegmentFiles(Path folder) throws IOException {
        List<Path> segments = new ArrayList<>();
        try (var files = Files.list(folder)) {
            files
                    .filter(path -> path.toString().endsWith(".ts"))
                    .forEach(segments::add);
        }
        return segments;
    }

    private void validateRestaurantExists(String restaurantId) throws NoSuchRestaurantException, RequestServiceException {
        if (!restaurantService.exists(restaurantId)) {
            throw NoSuchRestaurantException.createExceptionById(restaurantId);
        }
    }

    private Path createVideoTmpDir(String videoId) throws IOException {
        var videoTmpDir = tempDir.resolve(videoId);
        Files.createDirectories(videoTmpDir);
        return videoTmpDir;
    }

    private Path createTmpFile(String videoId, Path videoTmpDir, MultipartFile media) throws IOException {
        var tmpFile = videoTmpDir.resolve(videoId);
        media.transferTo(tmpFile);
        return tmpFile;
    }

    private Path createHlsWorkDir(Path videoTmpDir) throws IOException {
        var workDir = videoTmpDir.resolve("hls");
        Files.createDirectories(workDir);
        return workDir;
    }

    private void executeFfmpegCommand(Path tmpFile, Path workDir) throws IOException, InterruptedException {
        List<String> commands = buildFfmpegCommands(tmpFile);
        Process process = new ProcessBuilder()
                .command(commands)
                .directory(workDir.toFile())
                .start();

        process.waitFor();
    }

    private List<String> buildFfmpegCommands(Path tmpFile) {
        List<String> commands = new ArrayList<>();
        commands.add("ffmpeg");
        commands.add("-i"); commands.add(tmpFile.toString());
        commands.add("-b:v"); commands.add("1M");
        commands.add("-g"); commands.add("60");
        commands.add("-hls_time"); commands.add("2");
        commands.add("-hls_list_size"); commands.add("0");
        commands.add("-hls_segment_size"); commands.add("500000");
        commands.add(format("%s.m3u8", tmpFile.getFileName()));
        return commands;
    }

    private Story createStory(CreateStoryRequestMeta meta) {
        return Story.builder()
                .name(meta.getName())
                .type(meta.getMediaType())
                .publishDate(meta.getPublishDate())
                .expirationDate(meta.getExpirationDate())
                .restaurantId(meta.getRestaurantId())
                .build();
    }

    private Story saveStory(Story story, String restaurantId) {
        return sqlService.save(story, restaurantId);
    }

    @Log(
            event = "получение плейлиста видео истории",
            description = "получение плейлиста видео истории"
    )
    @Transactional(readOnly = true)
    public GetStoryPlaylistResponse processGetStoryPlaylist(GetStoryPlaylistRequestMeta meta) {
        var playlist = minioStorage.findByKey(
                format("%s.m3u8", meta.getStoryId())
        );
        return GetStoryPlaylistResponse.success(
                outputStream -> outputStream.write(playlist)
        );
    }


    @Log(
            event = "получение сегмента видео истории",
            description = "получение сегмента видео истории"
    )
    @Transactional(readOnly = true)
    public GetStorySegmentResponse processGetStorySegment(GetStorySegmentRequestMeta meta) {
        var segment = minioStorage.findByKey(
                format("%s%s.ts", meta.getStoryId(), meta.getTsName())
        );
        return GetStorySegmentResponse.success(
                outputStream -> outputStream.write(segment)
        );
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

    // TODO: delete all ts from MinIO
    @Log(
            event = "удаление истории",
            description = "удаление истории ресторана"
    )
    @Transactional(rollbackFor = Throwable.class)
    public DeleteStoryResponse processDeleteStory(DeleteStoryRequestMeta meta) {
        minioStorage.delete(List.of(
                format("%s.m3u8", meta.getId())
        ));

        sqlService.deleteByIdAndRestaurantId(meta.getId(), meta.getRestaurantId());
        return DeleteStoryResponse.success();
    }

    @Log(
            event = "получение постера истории",
            description = "получение постера истории"
    )
    @Transactional(readOnly = true)
    public GetStoryPosterResponse processGetStoryPoster(GetStoryPosterRequestMeta meta) throws NoSuchStoryException {
        var story = sqlService.findByIdAndRestaurantId(meta.getStoryId(), meta.getRestaurantId());

        return GetStoryPosterResponse.success(
                story.getPoster().getSrc(),
                story.getPoster().getId()
        );
    }

    @Log(
            event = "создание постера истории",
            description = "создание постера истории"
    )
    @Transactional(rollbackFor = Throwable.class)
    public CreateStoryPosterResponse processCreateStoryPoster(CreateStoryPosterRequestMeta meta) throws NoSuchStoryException, IOException {
        UUID posterId = UUID.randomUUID();
        var story = sqlService.findByIdAndRestaurantId(meta.getStoryId(), meta.getRestaurantId());

        minioStorage.save(posterId.toString(), meta.getPoster().getBytes());
        story.savePoster(posterId.toString(), BASE_PATH_STORY + posterId);

        return CreateStoryPosterResponse.success(posterId.toString());
    }
}
