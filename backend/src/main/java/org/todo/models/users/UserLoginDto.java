package org.todo.models.users;

import lombok.Data;

@Data
public class UserLoginDto {
    private String email;
    private String password;
}
