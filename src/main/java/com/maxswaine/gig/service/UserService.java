package com.maxswaine.gig.service;

import com.maxswaine.gig.api.dto.User;
import com.maxswaine.gig.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User addUser(User newUser) {
        logger.info("Creating new user");
        logger.info(newUser.toString());
        return userRepository.save(newUser);
    }

    public void deleteUser(String id) {
        Optional<User> existingUser = userRepository.findById(id);
        if (existingUser.isPresent()){
            logger.info("User {} successfully deleted", existingUser.get().getId());
            userRepository.deleteById(id);
        } else {
            logger.error("User with id {} does not exist", id);
            throw new IllegalArgumentException();
        }
    }

    public void updatePassword(String id, String password) {
        User existingUser = userRepository.findById(id).orElseThrow();

        existingUser.setPassword(password);
        logger.info("Password updated successfully");
    }
}
