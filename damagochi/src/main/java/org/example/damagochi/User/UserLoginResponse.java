// New UserLoginResponse class
package org.example.damagochi.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginResponse {
    private String token;
    private String nickname;
    private Integer userimage;
    private String username;
}