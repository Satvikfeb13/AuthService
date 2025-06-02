package org.example.controller;

import lombok.AllArgsConstructor;
import org.example.Model.UserInfoDto;
import org.example.entities.RefreshToken;
import org.example.response.JwtResponseDTO;
import org.example.service.JwtService;
import org.example.service.RefreshTokenService;
import org.example.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
public class AuthController {
    @Autowired private JwtService jwtService;
    @Autowired private RefreshTokenService refreshTokenService;
    @Autowired private UserDetailsServiceImpl userDetailsService;
    @PostMapping("auth/v1/signup")
    public ResponseEntity SignUp(@RequestBody UserInfoDto userInfoDto){
    try{
        boolean isSignedUp= userDetailsService.signUp(userInfoDto);
        if(Boolean.FALSE.equals(isSignedUp)){
            return  new ResponseEntity<>("Already exist", HttpStatus.BAD_REQUEST);
        }
        RefreshToken refreshToken=  refreshTokenService.createRefreshToken(userInfoDto.getUsername());
        String jwtToken= jwtService.GenerateToken(userInfoDto.getUsername());
        return new ResponseEntity<>(JwtResponseDTO.builder().accessToken(jwtToken).token(refreshToken.getToken()).build(),HttpStatus.OK);
    } catch (Exception e) {
        return new ResponseEntity<>("Exception in userService",HttpStatus.INTERNAL_SERVER_ERROR);
    }
    }

}
