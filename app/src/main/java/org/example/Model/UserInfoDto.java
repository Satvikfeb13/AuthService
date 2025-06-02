package org.example.Model;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.entities.UserInfo;
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoDto extends UserInfo {
    private String userName;//  user_name
    private  String lastName;
    private  Long phoneNumber;
    private  String email;
}
