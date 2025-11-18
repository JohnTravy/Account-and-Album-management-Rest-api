package com.travy.SpringRestAPI.Payload.Auth.Album;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class PhotoPayloadDTO {

    @NotBlank
    @Schema(description="PhotoName", example="selfie", requiredMode = RequiredMode.REQUIRED)
    private String name;

    @NotBlank
    @Schema(description="Description of the photo", example="Description", requiredMode = RequiredMode.REQUIRED)
    private String description;

    
}
