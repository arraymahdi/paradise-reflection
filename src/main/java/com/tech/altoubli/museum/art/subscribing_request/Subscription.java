package com.tech.altoubli.museum.art.subscribing_request;

import com.tech.altoubli.museum.art.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.time.LocalDate;

@Entity
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Subscription {
    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne()
    private User sender;
    @ManyToOne()
    private User receiver;
    private LocalDate startedAt;
    private LocalDate endedAt;
    private Long renew;
}
