package org.todo.controllers.users;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.todo.entities.User;
import org.todo.models.users.UserService;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Operation(summary = "회원가입", description = "새로운 사용자를 등록")
    @ApiResponse(responseCode = "200", description = "회원가입 성공!",
            content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = String.class)) })
    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> registerUser(@RequestBody User user) {
        try {
            userService.registerNewUser(user);
            Map<String, String> response = Collections.singletonMap("message", "회원가입 성공!");
            return ResponseEntity.ok(response);
        } catch (ResponseStatusException e) {
            return ResponseEntity
                    .status(e.getStatusCode())
                    .body(Collections.singletonMap("message", e.getReason()));
        }
    }

    // 기타 사용자 관련 API 구현
}
