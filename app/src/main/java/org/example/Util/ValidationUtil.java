package org.example.Util;

import lombok.Data;
import org.example.Model.UserInfoDto;

import java.util.regex.Pattern;

@Data
public class ValidationUtil {
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\\\.[A-Za-z]{2,6}$");
    private static  final  Pattern PASSWORD_PATTERN=Pattern.compile(  "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,}$");
    public  boolean isValidPassword(UserInfoDto userInfoDto){
        return userInfoDto.getPassword()!=null &&
                PASSWORD_PATTERN.matcher(userInfoDto.getPassword()).matches();
    }
    public boolean isValidEmail(UserInfoDto userInfoDto) {
        return userInfoDto.getEmail() !=null && EMAIL_PATTERN.matcher(userInfoDto.getEmail()).matches();
    }
}