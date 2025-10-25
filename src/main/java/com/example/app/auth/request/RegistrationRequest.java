package com.example.app.auth.request;

import com.example.app.validation.NonDisposableEmail;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegistrationRequest {
   @NotBlank(message = "VALIDATION.REGISTRATION.FIRSTNAME.NOT_BLANK")
   @Size(
           min = 1,
           max = 255,
           message = "VALIDATION.REGISTRATION.FIRSTNAME.SIZE"
   )
   @Pattern(
           regexp = "^[\\p{L} '-]+$",
           message = "VALIDATION.REGISTRATION.FIRSTNAME.PATTERN"
   )
   @Schema(example = "Abc")
   private String firstName;
   @NotBlank(message = "VALIDATION.REGISTRATION.LASTNAME.NOT_BLANK")
   @Size(
           min = 1,
           max = 255,
           message = "VALIDATION.REGISTRATION.LASTNAME.SIZE"
   )
   @Pattern(
           regexp = "^[\\p{L} '-]+$",
           message = "VALIDATION.REGISTRATION.LASTNAME.PATTERN"
   )
   @Schema(example = "Abc")
   private String lastName;
   @NotBlank(message = "VALIDATION.REGISTRATION.EMAIL.NOT_BLANK")
   @Email(message = "VALIDATION.REGISTRATION.EMAIL.FORMAT")
   @NonDisposableEmail(message = "VALIDATION.REGISTRATION.EMAIL.DISPOSABLE")
   @Schema(example = "abc@email.com")
   private String email;
   @NotBlank(message = "VALIDATION.REGISTRATION.PHONE.NOT_BLANK")
   @Pattern(
           regexp = "^\\+?[0-9]{10,13}$",
           message = "VALIDATION.REGISTRATION.PHONE.PATTERN"
   )
   @Schema(example = "+919876543210")
   private String phoneNumber;
   @NotBlank(message = "VALIDATION.REGISTRATION.PASSWORD.NOT_NULL")
   @Size(
           min = 8,
           max = 50,
           message = "VALIDATION.REGISTRATION.PASSWORD.SIZE"
   )
   @Pattern(
           regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*\\W).*$",
           message = "VALIDATION.REGISTRATION.PASSWORD.WEAK"
   )
   @Schema(example = "<PASSWORD>")
   private String password;
   @NotBlank(message = "VALIDATION.REGISTRATION.PASSWORD.NOT_NULL")
   @Size(
           min = 8,
           max = 50,
           message = "VALIDATION.REGISTRATION.PASSWORD.SIZE"
   )
   @Schema(example = "<PASSWORD>")
   private String confirmPassword;

}
