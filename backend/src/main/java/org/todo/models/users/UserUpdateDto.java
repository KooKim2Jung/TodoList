package org.todo.models.users;

import lombok.Data;

@Data
public class UserUpdateDto {
    String nickname;
    String password;
}
