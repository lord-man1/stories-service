package com.place2rest.storiesservice.vo.meta.story;

import com.place2rest.storiesservice.vo.enums.MediaType;
import com.place2rest.storiesservice.vo.meta.Meta;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
@SuperBuilder
@ToString(callSuper = true)
public class CreateStoryRequestMeta extends Meta {
    private String restaurantId;
    private String name;
    private Date publishDate;
    private Date expirationDate;
    private MediaType mediaType;
    private MultipartFile media;
}
