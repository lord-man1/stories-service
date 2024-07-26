package com.place2rest.storiesservice.vo.meta;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@ToString
public class Meta {
    private String restaurantId;
    private boolean isPlatform;
    private String token;
}
