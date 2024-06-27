package com.forohub.controller;

import com.forohub.domain.auth.LoginRequest;
import com.forohub.domain.auth.LoginResponse;
import com.forohub.domain.auth.RegisterRequest;
import com.forohub.domain.auth.RegisterResponse;
import com.forohub.infra.security.RegisterService;
import com.forohub.infra.security.TokenService;
import com.forohub.domain.users.User;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    TokenService tokenService;

    @Autowired
    RegisterService registerService;


    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest loginRequest){

        Authentication authToken = new UsernamePasswordAuthenticationToken(loginRequest.email(), loginRequest.password());
        var userAuthenticated= authenticationManager.authenticate(authToken);
        var token = tokenService.generateToken((User) userAuthenticated.getPrincipal());
        return ResponseEntity.ok(new LoginResponse(token));
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@RequestBody @Valid RegisterRequest registerRequest){

        RegisterResponse response = registerService.register(registerRequest);

        return ResponseEntity.ok(response);
    }


}
