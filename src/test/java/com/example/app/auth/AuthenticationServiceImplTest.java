package com.example.app.auth;

import com.example.app.auth.impl.AuthenticationServiceImpl;
import com.example.app.auth.request.AuthenticationRequest;
import com.example.app.auth.response.AuthenticationResponse;
import com.example.app.role.RoleRepository;
import com.example.app.security.JwtService;
import com.example.app.user.User;
import com.example.app.user.UserMapper;
import com.example.app.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthenticationServiceImplTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtService jwtService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private  UserMapper userMapper;

    @InjectMocks
    private AuthenticationServiceImpl authenticationService;


    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

//    @Test
    void login_ShouldReturnAuthenticationResponse_WhenCredentialsValid(){
        String email = "abc@gmail.com";
        String pwd = "Test@123";

        // Arrange
        AuthenticationRequest request = new AuthenticationRequest(email,pwd);

        // Mock Authenticated user
        User mockuser = new User();
        mockuser.setEmail(email);

        Authentication mockAuthentication  = new UsernamePasswordAuthenticationToken(mockuser,null, Collections.emptyList());

        when(authenticationManager.authenticate(any(Authentication.class))).thenReturn(mockAuthentication);
        when(jwtService.generateAccessToken(email)).thenReturn("mockAccessToken");
        when(jwtService.generateRefreshToken(email)).thenReturn("mockRefreshToken");

        System.out.println( mockAuthentication.getPrincipal());
        //Act
        AuthenticationResponse response = authenticationService.login(request);

        // Assert
        assertNotNull(response);
        assertEquals(response.getAccessToken(),"mockAccessToken");
        assertEquals(response.getRefreshToken(),"mockRefreshToken");
        assertEquals(response.getTokenType(),"Bearer");

        //verify
        verify(authenticationManager,times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtService,times(1)).generateAccessToken(request.getEmail());
        verify(jwtService,times(1)).generateRefreshToken(request.getEmail());

    }
}
