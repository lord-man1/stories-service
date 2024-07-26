package com.place2rest.storiesservice.vo.remote.story;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ExistResponseDTO {
    @JsonProperty(value = "restaurant", required = true)
    private Boolean exists;
}
