package com.place2rest.storiesservice.vo.view.main;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.place2rest.storiesservice.vo.enums.MediaType;
import com.place2rest.storiesservice.vo.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class StoryView {
    @JsonProperty("id")
    private String id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("media_type")
    private MediaType type;
    @JsonProperty("publish_date")
    private Date publishDate;
    @JsonProperty("expiration_date")
    private Date expirationDate;
    @JsonProperty("status")
    private Status status;
}
