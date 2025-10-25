package com.example.app.user.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChangePasswordRequest {

    @NotBlank(message = "VALIDATION.CHANGE_PASSWORD.PASSWORD.NOT_NULL")
    @Schema(example = "<PASSWORD>")
    private String currentPassword;
    @NotBlank(message = "VALIDATION.CHANGE_PASSWORD.NEW_PASSWORD.NOT_NULL")
    @Size(
            min = 8,
            max = 50,
            message = "VALIDATION.CHANGE_PASSWORD.NEW_PASSWORD.SIZE"
    )
    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*\\W).*$",
            message = "VALIDATION.CHANGE_PASSWORD.NEW_PASSWORD.WEAK"
    )
    @Schema(example = "<PASSWORD>")
    private String newPassword;
    @NotBlank(message = "VALIDATION.CHANGE_PASSWORD.CONFIRM_PASSWORD.NOT_NULL")
    @Size(
            min = 8,
            max = 50,
            message = "VALIDATION.CHANGE_PASSWORD.CONFIRM_PASSWORD.SIZE"
    )
    @Schema(example = "<PASSWORD>")
    private String confirmPassword;
}
