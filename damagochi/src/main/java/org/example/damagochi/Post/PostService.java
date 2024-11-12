package org.example.damagochi.Post;

import lombok.RequiredArgsConstructor;
import org.example.damagochi.User.User;
import org.example.damagochi.User.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public List<PostResponse> getPosts(String username, Title title) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        List<Post> posts;
        if (title != null) {
            posts = postRepository.findByUserAndTitle(user, title);
        } else {
            posts = postRepository.findByUser(user);
        }

        return posts.stream()
                .map(PostResponse::from)
                .collect(Collectors.toList());
    }

    @Transactional
    public void createPosts(List<PostCreateRequest> requests, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        List<Post> posts = requests.stream()
                .map(request -> {
                    Post post = new Post();
                    post.setTitle(request.getTitle());
                    post.setBody(request.getBody());
                    post.setUser(user);
                    return post;
                })
                .collect(Collectors.toList());

        postRepository.saveAll(posts);
    }
}