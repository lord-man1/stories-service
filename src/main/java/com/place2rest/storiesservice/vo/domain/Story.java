package com.place2rest.storiesservice.vo.domain;

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
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    @Column(name = "publish_date")
    private Date publishDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    @Column(name = "expiration_date")
    private Date expirationDate;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;
}
