package com.maxswaine.gigtracker.api.requests;

import com.maxswaine.gigtracker.api.enums.FriendshipStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FriendRequest {

    private String userId;
    private String friendId;
    private FriendshipStatus status = FriendshipStatus.PENDING;

    public FriendRequest(String userId, String friendId) {
        this.userId = userId;
        this.friendId = friendId;
    }
}
