package com.tech.altoubli.museum.art.subscribing_request;

import com.tech.altoubli.museum.art.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    Optional<Subscription> findBySenderAndReceiver(User sender, User receiver);
}
