package org.example.damagochi.User;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<Map<String, String>> signup(@ModelAttribute UserSignupRequest request) {
        userService.signup(request);
        return ResponseEntity.ok(Map.of("message", "회원가입 성공",
                "username", request.getUsername(),
                "nickname", request.getNickname()));
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@ModelAttribute UserLoginRequest request) {
        UserLoginResponse loginResponse = userService.login(request);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "로그인 성공");
        response.put("token", loginResponse.getToken());
        response.put("nickname", loginResponse.getNickname());
        response.put("userimage", loginResponse.getUserimage());
        response.put("username", loginResponse.getUsername());

        return ResponseEntity.ok(response);
    }

    @PutMapping("/users/me")
    public ResponseEntity<Map<String, Object>> updateProfile(
            @ModelAttribute UserUpdateRequest request,
            @AuthenticationPrincipal UserDetails userDetails) {
        userService.updateProfile(request, userDetails.getUsername());

        Map<String, Object> response = new HashMap<>();
        response.put("message", "프로필 수정 성공");
        response.put("nickname", request.getNickname());
        response.put("userimage", request.getUserimage());

        return ResponseEntity.ok(response);
    }
}
