package com.maxswaine.gig.api.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class MomentRequest {
    private List<String> descriptions;
    private String userId;
    private String gigId;
}
