package dev.steadypim.socialmediaapi.post;
import dev.steadypim.socialmediaapi.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final UserService userService;


    @PostMapping
    public ResponseEntity<Post> createPost(@RequestParam("text") String text,
                                           @RequestParam("title") String title,
                                           @RequestParam(value = "imageFiles", required = false) List<MultipartFile> imageFiles,
                                           Authentication authentication) throws IOException {



        Post createdPost = postService.create(userService.getCurrentUser(authentication),
                title, text, imageFiles);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPost);
    }

    @GetMapping
    public ResponseEntity<List<PostDto>> getAllPosts() throws IOException {
        List<PostDto> posts = postService.getAllPosts();
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostDto> getPostById(@PathVariable("postId") Integer postId) throws IOException {
        PostDto post = postService.getPostById(postId);
        return ResponseEntity.ok(post);
    }

    @PutMapping("/{postId}")
    public ResponseEntity<Post> updatePost(@PathVariable("postId") Integer postId,
                                           @RequestParam("text") String text,
                                           @RequestParam("title") String title,
                                           @RequestParam(value = "imageFiles", required = false) List<MultipartFile> imageFiles,
                                           Authentication authentication) throws IOException {

        return ResponseEntity.ok(postService.updatePost(
                postId,
                title,
                text,
                imageFiles,
                userService.getCurrentUser(authentication)));
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable("postId") Integer postId){
        postService.deletePost(postId);
        return ResponseEntity.noContent().build();
    }
}
