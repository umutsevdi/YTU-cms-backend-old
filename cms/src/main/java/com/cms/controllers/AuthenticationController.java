package com.cms.controllers;

import com.cms.models.documents.Role;
import com.cms.models.documents.User;
import com.cms.models.registration.RegistrationRequest;
import com.cms.services.MailConfirmationTokenService;
import com.cms.services.UserService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthenticationController {
    private final UserService userService;
    private final MailConfirmationTokenService mailConfirmationTokenService;
   

    @PostMapping(path = "/register")
    public User register(@RequestBody RegistrationRequest request) throws Exception {
        try{
            
            User user = userService.insertElement(
                new User(request.getName(), request.getMail(), request.getPassword(), 0L,Role.ADMIN));
            mailConfirmationTokenService.sendToken(request);
            return user;

        }catch(Exception e){
            throw new Exception(e.getMessage());
        }
   
    }
    @GetMapping(path="/confirm")
    public String confirmMail(@RequestParam("token") String token) throws Exception{
        try{
            mailConfirmationTokenService.confirmMail(token);
            return "Mail Hesabın Onaylandı";
        }catch(Exception e){
            throw new Exception(e.getMessage());
        }
        
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
