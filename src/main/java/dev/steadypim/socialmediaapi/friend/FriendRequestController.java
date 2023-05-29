package dev.steadypim.socialmediaapi.friend;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

/**
 * Контроллер запросов на дружбу
 */
@Tag(name = "friend_request", description = "Контроллер запросов на дружбу")
@RestController
@RequestMapping("/api/v1/friends")
@RequiredArgsConstructor
public class FriendRequestController {
    private final FriendRequestService friendRequestService;

    @Operation(summary = "Метод отправляет запрос на дружбу")
    @PostMapping("/requests")
    public ResponseEntity<Void> sendFriendRequest(
            @RequestParam Integer senderId,
            @RequestParam Integer recipientId
    ) {
        friendRequestService.sendFriendRequest(senderId, recipientId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Метод принимает запрос дружбы")
    @PostMapping("/requests/{requestId}/accept")
    public ResponseEntity<Void> acceptFriendRequest(
            @PathVariable Integer requestId
    ) {
        friendRequestService.acceptFriendRequest(requestId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Метод отклоняет запрос дружбы")
    @PostMapping("/requests/{requestId}/decline")
    public ResponseEntity<Void> declineFriendRequest(
            @PathVariable Integer requestId
    ) {
        friendRequestService.declineFriendRequest(requestId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Метод удаляет друга")
    @DeleteMapping("/friends/{userId}")
    public ResponseEntity<Void> removeFriend(
            @PathVariable Integer userId
    ) {
        friendRequestService.removeFriend(userId);
        return ResponseEntity.ok().build();
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
