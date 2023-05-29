package dev.steadypim.socialmediaapi.userActivity;

import dev.steadypim.socialmediaapi.post.Post;
import dev.steadypim.socialmediaapi.post.PostService;
import dev.steadypim.socialmediaapi.user.User;
import dev.steadypim.socialmediaapi.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Контроллер ленты активности
 */
@Tag(name = "user_activity", description = "Контроллер ленты активности")
@RestController
@RequestMapping("/api/v1/activities")
@RequiredArgsConstructor
public class UserActivityController {

    private final UserActivityService userActivityService;
    private final UserService userService;
    private final PostService postService;

    @Operation(summary = "Метод создает ленту активности для пользователя")
    @PostMapping("/users/{userId}/posts/{postId}")
    public ResponseEntity<String> createPostActivity(
            @PathVariable Integer userId,
            @PathVariable Integer postId
    ) {
        User user = userService.getUserById(userId);
        Post post = postService.findById(postId);

        userActivityService.createPostActivity(user, post);
        return ResponseEntity.ok("Лента активности пользователя создана успешно.");
    }

    @Operation(summary = "Метод возвращает ленту активности пользователя")
    @GetMapping("/users/{userId}/feed")
    public ResponseEntity<List<UserActivity>> getUserActivityFeed(
            @PathVariable Integer userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        List<UserActivity> userActivityFeed = userActivityService.getUserActivityFeed(userId, page, size);
        return ResponseEntity.ok(userActivityFeed);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<String> handleException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Внутренняя ошибка сервера: " + ex.getMessage());
    }

}
