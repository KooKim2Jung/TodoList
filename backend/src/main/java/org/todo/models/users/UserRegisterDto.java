package org.todo.models.users;

import lombok.Data;

@Data
public class UserRegisterDto {
    private String email;
    private String password;
    private String nickname;
    private String phone;

}
