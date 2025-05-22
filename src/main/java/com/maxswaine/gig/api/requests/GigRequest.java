package com.maxswaine.gig.api.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class GigRequest {
    private String userId;
    private String artist;
    private String venue;
    private String location;
    private LocalDateTime date;
    private boolean favourite;
    private List<String> attendeeIds;

    public GigRequest(String userId, String artist, String venue, String location, LocalDateTime date, boolean favourite) {
        this.userId = userId;
        this.artist = artist;
        this.venue = venue;
        this.location = location;
        this.date = date;
        this.favourite = favourite;
    }


}
