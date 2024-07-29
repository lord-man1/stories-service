package com.place2rest.storiesservice.vo.meta.story;


import com.place2rest.storiesservice.vo.enums.Status;
import com.place2rest.storiesservice.vo.meta.Meta;
import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
@SuperBuilder
@ToString(callSuper = true)
public class GetStoriesRequestMeta extends Meta {
    private Status status;
    @Builder.Default
    private int limit = 0;
    @Builder.Default
    private int offset = 10;
}
