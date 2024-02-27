package org.todo.models.users;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.todo.config.security.JwtTokenProvider;
import org.todo.entities.Member;
import org.todo.repositories.UserRepository;

@Service
@Transactional
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public Member getMember(HttpServletRequest request){
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7); // "Bearer " 제거
            String userIdString = jwtTokenProvider.getUserPK(token);
            int userId = Integer.parseInt(userIdString);

            return userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("Member not found"));
        } else {
            throw new RuntimeException("Authorization header is missing or invalid");
        }
    }

    public void registerNewUser(UserRegisterDto userRegisterDto) {
        userRepository.save(Member.builder()
                .email(userRegisterDto.getEmail())
                .password(passwordEncoder.encode(userRegisterDto.getPassword()))
                .nickname(userRegisterDto.getNickname())
                .phone(userRegisterDto.getPhone())
                .build());
    }

    public void updateUser(UserUpdateDto userUpdateDto, HttpServletRequest request) {
        Member member = getMember(request);

        if(userUpdateDto.getNickname() != null) {
            member.setNickname(userUpdateDto.getNickname());
        }

        if(userUpdateDto.getPassword() != null) {
            member.setPassword(passwordEncoder.encode(userUpdateDto.getPassword()));
        }

        userRepository.save(member);
    }

}
