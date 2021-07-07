package com.cms.controllers;

import com.cms.models.Role;
import com.cms.models.User;
import com.cms.models.requests.RegistrationRequest;
import com.cms.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/authentication")
public class AuthenticationController {
    @Autowired
    private UserService userService;

    @PostMapping(path = "/register")
    public User register(@RequestBody RegistrationRequest request) {
        return userService.insertElement(
                new User(request.getName(), request.getMail(), request.getPassword(), 0L, true, true, true,Role.ADMIN));

    }

    @GetMapping(path = "/login")
    public String emptyPage() {
        return "login";
    }

    // @PostMapping
    // public ResponseEntity<?> createAuthenticationToken(@RequestBody
    // AuthenticationRequest authenticationRequest)
    // throws Exception {
    // System.out.println("Post: " + authenticationRequest.getMail() + "\t" +
    // authenticationRequest.getPassword());
    // try {
    // authenticationManager.authenticate(new
    // UsernamePasswordAuthenticationToken(authenticationRequest.getMail(),
    // authenticationRequest.getPassword()));
    // } catch (BadCredentialsException e) {
    // System.out.println("IncorrectsUserCredentialsException");
    // throw new Exception("IncorrectUserCredentialsException");
    // }
    // System.out.println(authenticationManager.toString()+" has authenticated :
    // "+authenticationRequest.getMail());
    // UserDetails userdetails =
    // userService.loadUserByUsername(authenticationRequest.getMail());
    // return ResponseEntity.ok(new AuthenticationResponse(
    // authenticationService.createToken(userdetails.getUsername(),
    // userdetails.getPassword())));
    // }
    // @DeleteMapping("/authenticate")
    // public ResponseEntity<?> login(@RequestHeader HttpHeaderSecurityFilter
    // header){
    // }

}
