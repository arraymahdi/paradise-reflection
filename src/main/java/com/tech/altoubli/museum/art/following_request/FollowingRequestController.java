package com.tech.altoubli.museum.art.following_request;

import com.tech.altoubli.museum.art.user.User;
import com.tech.altoubli.museum.art.user.UserRepository;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class FollowingRequestController {

    private final UserRepository userRepository;
    private final FollowingRequestService followingRequestService;

    @PostMapping("/send/follow-request/{username}")
    public ResponseEntity<Map<String, String>> sendFollowingRequest(@PathVariable String username,
                                                                    Authentication connectedUser) throws MessagingException {
        User sender = userRepository.findByEmail(connectedUser.getName())
                .orElseThrow(()-> new UsernameNotFoundException("User Not Found"));
        User receiver = userRepository.findByNickName(username)
                .orElseThrow(()-> new UsernameNotFoundException("User Not Found"));
        return followingRequestService.sendFollowingRequest(sender, receiver);
    }
}
