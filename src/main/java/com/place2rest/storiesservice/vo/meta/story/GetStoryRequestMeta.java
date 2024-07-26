package com.place2rest.storiesservice.vo.meta.story;

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
public class GetStoryRequestMeta extends Meta {
    private String id;
}
