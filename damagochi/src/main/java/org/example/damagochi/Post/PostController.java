package org.example.damagochi.Post;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostController {
    private final PostService postService;

    @GetMapping
    public ResponseEntity<List<PostResponse>> getPosts(
            @RequestParam(required = false) Title title,
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(postService.getPosts(userDetails.getUsername(), title));
    }

    @PostMapping
    public ResponseEntity<Map<String, String>> createPost(
            @RequestBody List<PostCreateRequest> requests,
            @AuthenticationPrincipal UserDetails userDetails) {
        postService.createPosts(requests, userDetails.getUsername());
        return ResponseEntity.ok(Map.of("message", "게시글 작성 성공"));
    }
}