package com.travy.SpringRestAPI.Payload.Auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ProfileDTO {
    
    private Long id;

    private String email;

    private String authorities;
}
