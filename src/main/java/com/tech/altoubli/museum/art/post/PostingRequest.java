package com.tech.altoubli.museum.art.post;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostingRequest {
    private Long userId;
    private String imageUrl;
    private String description;
    private LocalDate createdAt;
    private Boolean requireSubscription;
}
