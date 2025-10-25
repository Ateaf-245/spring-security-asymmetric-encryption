package com.example.app.auth;

import com.example.app.auth.request.AuthenticationRequest;
import com.example.app.auth.request.RefreshRequest;
import com.example.app.auth.request.RegistrationRequest;
import com.example.app.auth.response.AuthenticationResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
@Tag(name = "Authentication", description = "Authentication API")
@Slf4j
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(
            @Valid
            @RequestBody final AuthenticationRequest request
    ) {
        return ResponseEntity.ok(this.authenticationService.login(request));
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(
            @Valid @RequestBody final RegistrationRequest request
    ) {
        log.info("in register service");
        this.authenticationService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthenticationResponse> refreshAccessToken(
            @Valid
            @RequestBody final RefreshRequest request
    ) {
        return ResponseEntity.ok(this.authenticationService.refreshToken(request));
    }

}
