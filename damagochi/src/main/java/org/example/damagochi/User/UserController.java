package org.example.damagochi.User;

import lombok.RequiredArgsConstructor;
import org.example.damagochi.CustomUserDetailsService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<Map<String, String>> signup(@ModelAttribute UserSignupRequest request) {
        userService.signup(request);

        return ResponseEntity.ok(Map.of("message", "회원가입 성공", "username", request.getUsername(), "nickname", request.getNickname()));
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@ModelAttribute UserLoginRequest request) {
        String token = userService.login(request);
        String username = request.getUsername();
        return ResponseEntity.ok(Map.of("message", "로그인 성공", "token", token, "username", username));
    }

    @PutMapping("/users/me")
    public ResponseEntity<Map<String, String>> updateProfile(
            @ModelAttribute UserUpdateRequest request,
            @AuthenticationPrincipal UserDetails userDetails) {
        userService.updateProfile(request, userDetails.getUsername());

        return ResponseEntity.ok(Map.of("message", "프로필 수정 성공", "nickname, userimage", request.getNickname() + ", "+ request.getUserimage() ));
    }
}
