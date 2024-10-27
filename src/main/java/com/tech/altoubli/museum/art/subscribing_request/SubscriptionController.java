package com.tech.altoubli.museum.art.subscribing_request;

import com.paypal.base.rest.PayPalRESTException;
import com.tech.altoubli.museum.art.user.User;
import com.tech.altoubli.museum.art.user.UserRepository;
import com.tech.altoubli.museum.art.user.UserService;
import com.tech.altoubli.museum.art.user_profile.UserProfileDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class SubscriptionController {
    private final UserRepository userRepository;
    private final UserService userService;

    @GetMapping("/get/subscribers")
    public List<UserProfileDto> getSubscribers(Authentication connectedUser) {
        User user = userRepository.findByEmail(connectedUser.getName())
                .orElseThrow(()-> new UsernameNotFoundException("User Not Found"));
        return user.getSubscribers().stream().map((subscriber) -> UserProfileDto
                .builder()
                .nickName(subscriber.getNickName())
                .imageUrl(subscriber.getProfile().getImageUrl())
                .bio(subscriber.getProfile().getDescription())
                .build()).collect(Collectors.toList());
    }

    @GetMapping("/get/subscribing")
    public List<UserProfileDto> getSubscribing(Authentication connectedUser) {
        User user = userRepository.findByEmail(connectedUser.getName())
                .orElseThrow(()-> new UsernameNotFoundException("User Not Found"));
        return user.getSubscribing().stream().map((subscriber) -> UserProfileDto
                .builder()
                .nickName(subscriber.getNickName())
                .imageUrl(subscriber.getProfile().getImageUrl())
                .bio(subscriber.getProfile().getDescription())
                .build()).collect(Collectors.toList());
    }

}
