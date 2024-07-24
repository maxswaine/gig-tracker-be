package com.maxswaine.gig.service;

import com.maxswaine.gig.api.dto.Friend;
import com.maxswaine.gig.api.dto.FriendshipStatus;
import com.maxswaine.gig.api.requests.FriendRequest;
import com.maxswaine.gig.repository.FriendRepository;
import com.maxswaine.gig.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class FriendRequestService {
    private static final Logger logger = LoggerFactory.getLogger(FriendRequestService.class);
    private final FriendRepository friendRepository;
    private final UserRepository userRepository;

    @Autowired
    public FriendRequestService(FriendRepository friendRepository, UserRepository userRepository) {
        this.friendRepository = friendRepository;
        this.userRepository = userRepository;
    }

    public List<Friend> getAllFriends(String userId) {
        logger.info("Getting all friends for user {}", userId);

        List<Friend> friendsAsUser = friendRepository.findAcceptedFriendsByUserId(userId);
        List<Friend> friendsAsFriend = friendRepository.findAcceptedFriendsByFriendId(userId);

        friendsAsUser.addAll(friendsAsFriend);
        if (friendsAsUser.isEmpty()) {
            logger.warn("No friends yet");
        }
        return friendRepository.findAcceptedFriendsByUserId(userId);
    }

    public Friend sendFriendRequest(FriendRequest friendRequest) {
        userRepository.findById(friendRequest.getFriendId()).
                orElseThrow(() -> new EntityNotFoundException("User not found"));

        Friend newFriendRequest = Friend.builder()
                .userId(friendRequest.getUserId())
                .friendId(friendRequest.getFriendId())
                .status(friendRequest.getStatus())
                .createdAt(LocalDateTime.now())
                .build();

        Friend createdFriendRequest = friendRepository.save(newFriendRequest);
        logger.info("Friend request sent");
        return createdFriendRequest;
    }

    public Friend changeFriendRequestStatus(String friendshipId, FriendRequest friendRequest) {
        Friend existingFriendRequest = friendRepository.findById(friendshipId).orElseThrow(() -> new RuntimeException("Friendship Request not found"));

        switch (friendRequest.getStatus()) {
            case ACCEPTED:
                existingFriendRequest.setStatus(FriendshipStatus.ACCEPTED);
                existingFriendRequest.setUpdatedAt(LocalDateTime.now());

                break;
            case REJECTED:
                existingFriendRequest.setStatus(FriendshipStatus.REJECTED);
                existingFriendRequest.setUpdatedAt(LocalDateTime.now());
                break;
            default:
                throw new IllegalArgumentException("Not a valid response");
        }
        return friendRepository.save(existingFriendRequest);
    }

    public boolean areFriends(String userId, String attendeeId) {
        List<Friend> friends = getAllFriends(userId);
        return friends.stream()
                .filter(friend -> friend.getStatus() == FriendshipStatus.ACCEPTED)
                .anyMatch(friend -> friend.getFriendId().equals(attendeeId));
    }
}
