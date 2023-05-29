package dev.steadypim.socialmediaapi.post;
import dev.steadypim.socialmediaapi.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * Контроллер постов
 */
@Tag(name = "post", description = "Контроллер постов")
@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final UserService userService;


    @Operation(summary = "Метод создает пост")
    @PostMapping
    public ResponseEntity<Post> createPost(
            @RequestParam("text") String text,
            @RequestParam("title") String title,
            @RequestParam(value = "imageFiles", required = false) List<MultipartFile> imageFiles,
            Authentication authentication
    ) throws IOException {
        Post createdPost = postService.create(
                userService.getCurrentUser(authentication),
                title,
                text,
                imageFiles
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPost);
    }

    @Operation(summary = "Метод возвращает все посты")
    @GetMapping
    public ResponseEntity<List<PostDto>> getAllPosts() throws IOException {
        List<PostDto> posts = postService.getAllPosts();
        return ResponseEntity.ok(posts);
    }

    @Operation(summary = "Метод возвращает пост по его идентификатору")
    @GetMapping("/{postId}")
    public ResponseEntity<PostDto> getPostById(
            @PathVariable("postId") Integer postId
    ) throws IOException {
        PostDto post = postService.getPostById(postId);
        return ResponseEntity.ok(post);
    }

    @Operation(summary = "Метод обновляет существующий пост")
    @PutMapping("/{postId}")
    public ResponseEntity<Post> updatePost(
            @PathVariable("postId") Integer postId,
            @RequestParam("text") String text,
            @RequestParam("title") String title,
            @RequestParam(value = "imageFiles", required = false) List<MultipartFile> imageFiles,
            Authentication authentication
    ) throws IOException {
        return ResponseEntity.ok(postService.updatePost(
                postId,
                title,
                text,
                imageFiles,
                userService.getCurrentUser(authentication)
        ));
    }

    @Operation(summary = "Метод удаляет пост по его идентификатору")
    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(
            @PathVariable("postId") Integer postId
    ) {
        postService.deletePost(postId);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        return ResponseEntity.badRequest().body("Ошибка валидации: " + ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<String> handleException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Внутренняя ошибка сервера: " + ex.getMessage());
    }
}
