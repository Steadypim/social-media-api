package dev.steadypim.socialmediaapi.post;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/user/posts")
@RequiredArgsConstructor
public class UserPostsController {

    private final PostService postService;
    @GetMapping("/{userId}")
    public ResponseEntity<List<PostDto>> getAllUserPosts(@PathVariable Integer userId) throws IOException {
        return ResponseEntity.ok(postService.getAllUserPosts(userId));
    }
}
