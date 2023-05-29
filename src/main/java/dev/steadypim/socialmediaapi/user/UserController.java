package dev.steadypim.socialmediaapi.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Контроллер пользователя
 */
@Tag(name = "user", description = "Контроллер пользователя")
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(summary = "Метод подписывает пользователя на другого пользователя")
    @PostMapping("/{subscriberId}/subscribe/{targetUserId}")
    public ResponseEntity<String> subscribeUser(
            @PathVariable Integer subscriberId,
            @PathVariable Integer targetUserId
    ) {
        userService.subscribeUser(subscriberId, targetUserId);
        return ResponseEntity.ok("Пользователь успешно подписан.");
    }

    @Operation(summary = "Метод отписывает пользователя")
    @PostMapping("/{subscriberId}/unsubscribe/{targetUserId}")
    public ResponseEntity<String> unsubscribeUser(
            @PathVariable Integer subscriberId,
            @PathVariable Integer targetUserId
    ) {
        userService.unsubscribeUser(subscriberId, targetUserId);
        return ResponseEntity.ok("Пользователь успешно отписан.");
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<String> handleException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Внутренняя ошибка сервера: " + ex.getMessage());
    }
}
