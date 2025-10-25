package com.example.app.user.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Past;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProfileUpdateRequest {
    @NotBlank(message = "VALIDATION.PROFILE_UPDATE.FIRSTNAME.NOT_BLANK")
    @Size(
            min = 1,
            max = 255,
            message = "VALIDATION.PROFILE_UPDATE.FIRSTNAME.SIZE"
    )
    @Pattern(
            regexp = "^[\\p{L} '-]+$",
            message = "VALIDATION.PROFILE_UPDATE.FIRSTNAME.PATTERN"
    )
    @Schema(example = "Abc")
    private String firstName;
    @NotBlank(message = "VALIDATION.PROFILE_UPDATE.LASTNAME.NOT_BLANK")
    @Size(
            min = 1,
            max = 255,
            message = "VALIDATION.PROFILE_UPDATE.LASTNAME.SIZE"
    )
    @Pattern(
            regexp = "^[\\p{L} '-]+$",
            message = "VALIDATION.PROFILE_UPDATE.LASTNAME.PATTERN"
    )
    @Schema(example = "Abc")
    private String lastName;
    @Past(message = "Date of birth must be in the past")
    @Schema(example = "01/11/1900")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate dateOfBirth;

}
