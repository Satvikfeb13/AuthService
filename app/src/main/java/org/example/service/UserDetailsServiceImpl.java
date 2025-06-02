package org.example.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.Model.UserInfoDto;
import org.example.Util.ValidationUtil;
import org.example.entities.UserInfo;
import org.example.repository.UserRepositary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Objects;
import java.util.UUID;

@AllArgsConstructor
@Data
@Component
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private final UserRepositary userRepositary;
    @Autowired
    private final PasswordEncoder passwordEncoder;
    @Autowired private ValidationUtil validationUtil;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserInfo user = userRepositary.findByUserName(username);
        if (user == null) {
            throw new UsernameNotFoundException("Could not found");
        }
        return new CustomUserDetails(user);
    }

    public UserInfo checkIfUserExist(UserInfoDto userInfoDto) {
        return userRepositary.findByUserName(userInfoDto.getUsername());
    }

    public boolean signUp(UserInfoDto userInfoDto) {
       if(!validationUtil.isValidEmail(userInfoDto) || !validationUtil.isValidPassword(userInfoDto)){
           return  false;
        }
        userInfoDto.setPassword(passwordEncoder.encode(userInfoDto.getPassword()));
        if (Objects.nonNull(checkIfUserExist(userInfoDto))) {
            return false;
        }
        String userId = UUID.randomUUID().toString();
        userRepositary.save(new UserInfo(userId, userInfoDto.getUsername(),
                userInfoDto.getPassword(), new HashSet<>()));
        return true;
    }
}



