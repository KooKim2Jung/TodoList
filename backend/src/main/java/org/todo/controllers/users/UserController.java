package org.todo.controllers.users;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.todo.config.security.JwtTokenProvider;
import org.todo.entities.Member;
import org.todo.models.users.UserLoginDto;
import org.todo.models.users.UserRegisterDto;
import org.todo.repositories.UserRepository;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;

    @Operation(summary = "회원가입", description = "새로운 사용자를 등록")
    @ApiResponse(responseCode = "200", description = "회원가입 성공!",
            content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = String.class)) })
    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> registerUser(@RequestBody UserRegisterDto userRegisterDto) {
        try {
            userRepository.save(Member.builder()
                    .email(userRegisterDto.getEmail())
                    .password(passwordEncoder.encode(userRegisterDto.getPassword()))
                    .nickname(userRegisterDto.getNickname())
                    .phone(userRegisterDto.getPhone())
                    .build());
            Map<String, String> response = Collections.singletonMap("message", "회원가입 성공!");
            return ResponseEntity.ok(response);
        } catch (ResponseStatusException e) {
            return ResponseEntity
                    .status(e.getStatusCode())
                    .body(Collections.singletonMap("message", e.getReason()));
        }
    }

    @Operation(summary = "로그인", description = "사용자 로그인")
    @ApiResponse(responseCode = "200", description = "로그인 성공!",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = String.class))})
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody UserLoginDto userLoginDto) {
        Member member = userRepository.findByEmail(userLoginDto.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 이메일입니다."));
        if (!passwordEncoder.matches(userLoginDto.getPassword(), member.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 맞지 않습니다.");
        }

        String token = jwtTokenProvider.createToken(member.getId());
        String welcomeMessage = member.getNickname() + "님, 환영합니다!";

        Map<String, String> response = new HashMap<>();
        response.put("message", welcomeMessage);
        response.put("token", token); // JWT 토큰을 응답에 추가

        return ResponseEntity.ok(response);
    }


}
