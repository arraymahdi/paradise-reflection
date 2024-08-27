package com.tech.altoubli.museum.art.user;

import com.tech.altoubli.museum.art.auth.AuthenticationService;
import com.tech.altoubli.museum.art.auth.requests.ChangePasswordRequest;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final AuthenticationService service;
    @PostMapping("/change-password")
    public ResponseEntity<Map<String, String>> changePassword(
            @RequestHeader("Authorization") String token,
            @RequestBody ChangePasswordRequest request
    ) throws MessagingException {
        service.changePassword(token, request.getPassword(),
                request.getNewPassword(), request.getConfirmPassword());
        HashMap<String, String> map = new HashMap<>();
        map.put("Status", "Password changed successfully");
        return ResponseEntity.ok(map);
    }
}
