package com.place2rest.storiesservice.vo.meta.story;

import com.place2rest.storiesservice.vo.controller.request.story.ChangeStoryRequest;
import com.place2rest.storiesservice.vo.meta.Meta;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
@SuperBuilder
@ToString(callSuper = true)
public class ChangeStoryRequestMeta extends Meta {
    private String id;
    private ChangeStoryRequest request;
}
