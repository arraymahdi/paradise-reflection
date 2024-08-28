package com.tech.altoubli.museum.art.user;

import com.tech.altoubli.museum.art.exception.UsernameNotUniqueException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {


    private final UserRepository userRepository;


    public Boolean changeAccountStatus(Boolean booleanStatus, User user) {
        user.setIsPublic(booleanStatus);
        userRepository.save(user);
        return booleanStatus;
    }

    public String changeUserName(User user, String username) {
        Optional<User> checkUsername = userRepository.findByUsername(username);
        if(checkUsername.isPresent()){
            throw new UsernameNotUniqueException("This username has been taken");
        }
        user.setUsername(username);
        userRepository.save(user);
        return username;
    }
}
