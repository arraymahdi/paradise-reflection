package com.tech.altoubli.museum.art.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {


    private final UserRepository userRepository;


    public Boolean changeAccountStatus(Boolean booleanStatus, User user) {
        user.setIsPublic(booleanStatus);
        userRepository.save(user);
        return booleanStatus;
    }
}
