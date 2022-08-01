package com.smartVR.usermanagement.Controller;

import com.smartVR.usermanagement.Entity.AuthenticationRequest;
import com.smartVR.usermanagement.Payload.APIResponse;
import com.smartVR.usermanagement.Payload.TokenResponse;
import com.smartVR.usermanagement.Utility.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class LoginController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> createToken(@RequestBody AuthenticationRequest authenticationRequest){
        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUserName(),authenticationRequest.getPassword()));
        }catch (Exception e){
            return new ResponseEntity<TokenResponse>(new TokenResponse("Invalid UserName and Password",false), HttpStatus.BAD_REQUEST);
        }
        String token = jwtUtil.generateToken(authenticationRequest.getUserName());
        return new ResponseEntity(new TokenResponse(token,true),HttpStatus.OK);
    }

}
