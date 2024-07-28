package com.place2rest.storiesservice.vo.domain.embedded;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Embeddable
public class Poster {
    @Column(name = "poster_id")
    private String id;
    @Column(name = "poster_src")
    private String src;
}
