package com.maxswaine.gig.api.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(fluent = true)
public class UserRequest {
    private String firstname;
    private String lastname;
    private String email;
    private String username;
    private String password;
}
