package org.example.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
//@Data it is made up of Getter Setter and Builder
//@Builder which is use to set Attribute
@AllArgsConstructor
@Data
@NoArgsConstructor
public class AuthRequestDTO {
    private  String username;
    private  String password;
}
