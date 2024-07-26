package com.place2rest.storiesservice.vo.controller.request.story;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChangeStoryRequest {
    @JsonProperty("name")
    private String name;
    @JsonProperty("publish_date")
    private Date publishDate;
    @JsonProperty("expiration_date")
    private Date expirationDate;
}
