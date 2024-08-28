package com.tech.altoubli.museum.art.following_request;

import com.tech.altoubli.museum.art.email.EmailService;
import com.tech.altoubli.museum.art.email.EmailTemplateName;
import com.tech.altoubli.museum.art.user.User;
import com.tech.altoubli.museum.art.user.UserRepository;
import jakarta.mail.MessagingException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FollowingRequestService {
    private final UserRepository userRepository;
    private final FollowingRequestRepository followingRequestRepository;
    private final EmailService emailService;

    public FollowingRequestService(UserRepository userRepository, FollowingRequestRepository followingRequestRepository, EmailService emailService) {
        this.userRepository = userRepository;
        this.followingRequestRepository = followingRequestRepository;
        this.emailService = emailService;
    }

    public ResponseEntity<Map<String, String>> sendFollowingRequest(User sender, User receiver) throws MessagingException {
        if(sender.equals(receiver)){
            Map<String, String>  res = new HashMap<>();
            res.put("Status", "This is you account, no need to subscribe.");
            return ResponseEntity.ok(res);
        } else if(receiver.getIsPublic()){
            if(sender.getFollowing().contains(receiver)){
                Map<String, String>  res = new HashMap<>();
                res.put("Status", "You're already following this account.");
                return ResponseEntity.ok(res);
            }
            FollowingRequest request = FollowingRequest
                    .builder()
                    .sender(sender)
                    .receiver(receiver)
                    .acceptedAt(LocalDate.now())
                    .sentAt(LocalDate.now())
                    .accepted(true)
                    .build();
            followingRequestRepository.save(request);
            sender.getFollowing().add(receiver);
            userRepository.save(sender);
            receiver.getFollowers().add(sender);
            userRepository.save(receiver);
            Map<String, String>  res = new HashMap<>();
            res.put("Status", "Now you're following this account!");
            return ResponseEntity.ok(res);
        }
        if(sender.getFollowing().contains(receiver)){
            Map<String, String>  res = new HashMap<>();
            res.put("Status", "You're already following this account.");
            return ResponseEntity.ok(res);
        }
        if (!(sender.getSentFollowingRequests().stream()
                .filter((req) -> req.getReceiver().equals(receiver) && !req.getAccepted()).count() == 0)) {
            Map<String, String>  res = new HashMap<>();
            res.put("Status", "There is a following request has been sent previously.");
            return ResponseEntity.ok(res);
        }

        FollowingRequest request = FollowingRequest
                .builder()
                .sender(sender)
                .receiver(receiver)
                .sentAt(LocalDate.now())
                .accepted(false)
                .build();
        followingRequestRepository.save(request);
        emailService.sendFollowingRequestEmail(receiver.getEmail(), receiver.fullName(), EmailTemplateName.FOLLOWING_REQUEST,
                "http://localhost:8090", "http://localhost:8090",
                "Following Request", request);
        Map<String, String>  res = new HashMap<>();
        res.put("Status", "Your request has been sent, wait for acceptance!");
        return ResponseEntity.ok(res);
    }
}
