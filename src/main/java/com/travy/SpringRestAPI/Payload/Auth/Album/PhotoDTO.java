package com.travy.SpringRestAPI.Payload.Auth.Album;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class PhotoDTO {

    private Long id;
    private String name;
    private String description;
    private String filename;
    private String download_link;
    
}
