package com.maxswaine.gigtracker.controller;


import com.maxswaine.gigtracker.api.dto.Friend;
import com.maxswaine.gigtracker.api.requests.FriendRequest;
import com.maxswaine.gigtracker.service.FriendRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/friends")
public class FriendRequestController {

    private final FriendRequestService friendRequestService;

    @Autowired
    public FriendRequestController(FriendRequestService friendRequestService) {
        this.friendRequestService = friendRequestService;
    }

    // READ
    @GetMapping(path = "{userId}")
    public ResponseEntity<List<Friend>> getAllFriends(@PathVariable("userId") String userId){
        return new ResponseEntity<>(friendRequestService.getAllFriends(userId), HttpStatus.OK);
    }

    // CREATE
    @PostMapping
    public ResponseEntity<Friend> addFriend(@RequestBody FriendRequest friendRequest){
        Friend newFriendship = friendRequestService.sendFriendRequest(friendRequest);
        return new ResponseEntity<>(newFriendship, HttpStatus.CREATED);
    }

    @PatchMapping("/{friendshipId}")
    public ResponseEntity<Friend> respondToFriendRequest(@PathVariable String friendshipId, @RequestBody FriendRequest friendRequest){
        Friend changeFriendshipStatus = friendRequestService.changeFriendRequestStatus(friendshipId, friendRequest);
        return new ResponseEntity<>(changeFriendshipStatus, HttpStatus.ACCEPTED);
    }

    // UPDATE

    // DELETE
}
