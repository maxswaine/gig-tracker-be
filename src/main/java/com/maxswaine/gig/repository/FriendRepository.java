package com.maxswaine.gig.repository;

import com.maxswaine.gig.api.dto.Friend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FriendRepository extends JpaRepository<Friend, String> {

    @Query("SELECT f FROM Friend f WHERE f.userId = :userId AND f.status = com.maxswaine.gig.api.dto.FriendshipStatus.ACCEPTED")
    List<Friend> findAcceptedFriendsByUserId(@Param("userId") String userId);

    @Query("SELECT f FROM Friend f WHERE f.friendId = :userId AND f.status = com.maxswaine.gig.api.dto.FriendshipStatus.ACCEPTED")
    List<Friend> findAcceptedFriendsByFriendId(@Param("userId") String userId);
}
