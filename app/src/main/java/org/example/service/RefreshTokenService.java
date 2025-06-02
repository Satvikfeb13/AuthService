package org.example.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.entities.RefreshToken;
import org.example.entities.UserInfo;
import org.example.repository.RefreshTokenRepository;
import org.example.repository.UserRepositary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;
@Data
@Component
@AllArgsConstructor
@Service
public class RefreshTokenService {
    @Autowired RefreshTokenRepository refreshTokenRepository;
    @Autowired UserRepositary userRepositary;
    public RefreshToken createRefreshToken(String userName){
        UserInfo userInfoExtracted=userRepositary.findByUserName(userName);
        RefreshToken refreshToken=RefreshToken.builder()
                .userInfo(userInfoExtracted)
                .token(UUID.randomUUID().toString())
                .expiry_date(Instant.now().plusMillis(6000000))
                .build();
        return refreshTokenRepository.save(refreshToken);
    }

    public RefreshToken verifyExpiration(RefreshToken token){
        if(token.getExpiry_date().compareTo(Instant.now())<0){
            refreshTokenRepository.delete(token);
            throw  new RuntimeException(token.getToken()+" Refresh token is expired. Please make a new login..!");
        }
        return token;
    }
    public Optional<RefreshToken>findByToken(String token){
        return refreshTokenRepository.findByToken(token);
    }

}
