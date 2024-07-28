package com.place2rest.storiesservice.vo.domain;

import com.place2rest.storiesservice.vo.domain.embedded.Poster;
import com.place2rest.storiesservice.vo.enums.MediaType;
import com.place2rest.storiesservice.vo.enums.Status;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "stories")
public class Story {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private String id;
    @Column(name = "name")
    private String name;
    @Enumerated(EnumType.STRING)
    @Column(name = "media_type")
    private MediaType type;
    @Embedded
    private Poster poster;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    @Column(name = "publish_date")
    private Date publishDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    @Column(name = "expiration_date")
    private Date expirationDate;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;
    @Column(name = "restaurant_id")
    private String restaurantId;

    public void savePoster(String id, String src) {
        this.poster = new Poster(id, src);
    }
}
