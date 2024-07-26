package com.place2rest.storiesservice.api.mapper;

import com.place2rest.storiesservice.vo.controller.request.story.ChangeStoryRequest;
import com.place2rest.storiesservice.vo.domain.Story;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface StoryMapper {
    void updateStory(ChangeStoryRequest dto, @MappingTarget Story entity);
}
