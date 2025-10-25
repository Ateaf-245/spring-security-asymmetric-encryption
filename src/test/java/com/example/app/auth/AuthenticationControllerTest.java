package com.example.app.auth;

import com.example.app.auth.request.AuthenticationRequest;
import com.example.app.auth.request.RegistrationRequest;
import com.example.app.auth.response.AuthenticationResponse;
import com.example.app.security.JwtService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(AuthenticationController.class)
public class AuthenticationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private JwtService jwtService;

    @MockitoBean
    private AuthenticationService authenticationService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void testRegisterUser_ValidRequest() throws Exception{
        RegistrationRequest request = new RegistrationRequest();
        request.setFirstName("Ateaf");
        request.setLastName("Bankapur");
        request.setEmail("abc@gmail.com");
        request.setPhoneNumber("8909454545");
        request.setPassword("Ateaf@123");
        request.setConfirmPassword("Ateaf@123");

        mockMvc.perform(post("/api/v1/auth/register")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());

        verify(authenticationService,times(1)).register(any(RegistrationRequest.class));
    }

    @Test
    void testRegisterUser_InvalidRequest() throws Exception{
        RegistrationRequest request = new RegistrationRequest();
        request.setFirstName("Ateaf");
        request.setLastName("Bankapur");
        request.setEmail("abcgmail.com");
        request.setPhoneNumber("8909454545");
        request.setPassword("Ateaf@123");
        request.setConfirmPassword("Ateaf@123");

        mockMvc.perform(post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());

        verify(authenticationService,times(0)).register(any(RegistrationRequest.class));
    }

    @Test
    void testLogin_ValidRequest() throws Exception {
        AuthenticationRequest request = new AuthenticationRequest();
        request.setEmail("Ateaf@gmail.com");
        request.setPassword("Ateaf@786");

        AuthenticationResponse response = new AuthenticationResponse();
        response.setAccessToken("fake-access-token");
        response.setRefreshToken("fake-refresh-token");
        response.setTokenType("Bearer");

        when(authenticationService.login(any(AuthenticationRequest.class))).thenReturn(response);

        mockMvc.perform(post("/api/v1/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(response)));

        // Verify service interaction
        verify(authenticationService, times(1)).login(any(AuthenticationRequest.class));
    }

    @Test
    void testLogin_InvalidRequest() throws Exception {
        AuthenticationRequest request = new AuthenticationRequest();
        request.setEmail("Ateaf@gmail.com");
        request.setPassword("Ateaf786");

        // Mock service to throw an exception
        when(authenticationService.login(any(AuthenticationRequest.class))).thenThrow(
                new BadCredentialsException("Invalid username/password")
        );

        // Perform request
        mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isUnauthorized())
                .andExpect( jsonPath("$.message").value("Invalid username/password"));

        // Verify service interaction
        verify(authenticationService, times(1)).login(any(AuthenticationRequest.class));
    }
}
