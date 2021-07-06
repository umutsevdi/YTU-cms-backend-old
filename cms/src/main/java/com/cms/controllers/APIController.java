package com.cms.controllers;

import com.cms.models.AuthenticationRequest;
import com.cms.models.AuthenticationResponse;
import com.cms.services.AuthenticationService;
import com.cms.services.CmsUserDetailsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api")
public class APIController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private CmsUserDetailsService userDetailsService;

    @GetMapping
    public String hello() {
        return "index";
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest)
            throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getMail(),
                    authenticationRequest.getPassword()));
        } catch (BadCredentialsException e) {
            throw new Exception("IncorrectUserCredentialsException");
        }
        UserDetails userdetails = userDetailsService.loadUserByUsername(authenticationRequest.getMail());
        return ResponseEntity.ok(new AuthenticationResponse(authenticationService
                .createToken(userdetails.getUsername(), userdetails.getPassword())));
    }
}
