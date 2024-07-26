package com.place2rest.storiesservice.impl.service.database;

import com.place2rest.storiesservice.api.repository.StoryRepository;
import com.place2rest.storiesservice.vo.domain.Story;
import com.place2rest.storiesservice.vo.exception.NoSuchStoryException;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import place2.rest.toolbox.api.cache.Cache;
import place2.rest.toolbox.impl.cache.MapCacheServiceImpl;
import place2.rest.toolbox.vo.toolbox.cache.CacheTime;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
public class StorySQLService {
    private final StoryRepository repository;
    private final Cache<String, List<Story>> cacheService;

    public StorySQLService(StoryRepository repository) {
        this.repository = repository;
        this.cacheService = new MapCacheServiceImpl<>(true);
    }

    public Story save(Story story, String restaurantId) {
        boolean isUpdate = story.getId() == null;
        var saved = repository.save(story);
        var stories = cacheService.get(restaurantId);
        if (stories == null) {
            cacheService.save(restaurantId, new ArrayList<>(List.of(saved)), CacheTime.HOUR);
        } else {
            if (isUpdate) {
                cacheService.get(restaurantId)
                        .removeIf(v -> v.getId().equals(saved.getId()));
            }
            cacheService.get(restaurantId).add(saved);
        }
        return saved;
    }

    public List<Story> findAllByRestaurantId(String restaurantId) {
        var cachedStories = cacheService.get(restaurantId);
        if (cachedStories.isEmpty()) {
            var stories = repository.findAllByRestaurantId(restaurantId);
            cacheService.save(restaurantId, stories, CacheTime.HOUR);
            return stories;
        }
        return cachedStories;
    }

    public Story findByIdAndRestaurantId(String id, String restaurantId) throws NoSuchStoryException {
        var cachedStory = cacheService.get(restaurantId).stream()
                .filter(v -> v.getId().equals(id))
                .findFirst();
        if (cachedStory.isEmpty()) {
            var story = repository.findByIdAndRestaurantId(id, restaurantId)
                    .orElseThrow(() -> NoSuchStoryException.createExceptionById(id));
            cacheService.get(restaurantId).add(story);
            return story;
        }
        return cachedStory.get();
    }

    public void deleteByIdAndRestaurantId(String id, String restaurantId) {
        repository.deleteById(id);
        cacheService.get(restaurantId)
                .removeIf(v -> v.getId().equals(id));
    }
}
