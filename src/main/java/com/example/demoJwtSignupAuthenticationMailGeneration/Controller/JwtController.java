package com.example.demoJwtSignupAuthenticationMailGeneration.Controller;


import com.example.demoJwtSignupAuthenticationMailGeneration.EmailConfiguration.EmailService;
import com.example.demoJwtSignupAuthenticationMailGeneration.Entity.JwtRequest;
import com.example.demoJwtSignupAuthenticationMailGeneration.Entity.JwtResponse;
import com.example.demoJwtSignupAuthenticationMailGeneration.Service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin
@Profile(value = {"local","prod","dev"})
public class JwtController {
    @Autowired
    private JwtService jwtService;
    @Autowired
    private EmailService emailService;

    @PostMapping("/signUpUser")
    public Map<String, Object> signUpProcessUserWithJwtToken(@RequestBody JwtRequest jwtRequest) throws Exception {
        return jwtService.signUpProcessUserWithJwtToken(jwtRequest);
    }
    @PostMapping("/signUpAdmin")
    public Map<String, Object> signUpProcessAdminWithJwtToken(@RequestBody JwtRequest jwtRequest) throws Exception {
        return jwtService.signUpProcessAdminWithJwtToken(jwtRequest);
    }

    @GetMapping("/verifyToken")
    public String tokenVerification(@RequestParam(value = "token") String token){
        return jwtService.tokenVerification(token);
    }

    @PostMapping({"/authenticate"})
    public JwtResponse createJwtAuthenticationToken(@RequestBody JwtRequest jwtRequest) throws Exception {
        return jwtService.createJwtAuthenticationToken(jwtRequest);
    }

}
