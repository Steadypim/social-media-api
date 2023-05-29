package dev.steadypim.socialmediaapi.message;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Контроллер сообщений
 */
@Tag(name = "message", description = "Контроллер сообщений")
@RestController
@RequestMapping("api/v1/messages")
@RequiredArgsConstructor
public class MessageController {
    private final MessageService messageService;

    @Operation(summary = "Метод возвращает сообщения между пользователями")
    @GetMapping("/{senderId}/{recipientId}")
    public ResponseEntity<List<Message>> getMessagesBetweenUsers(
            @PathVariable Integer senderId,
            @PathVariable Integer recipientId
    ) {
        List<Message> messages = messageService.getMessagesBetweenUsers(senderId, recipientId);
        return ResponseEntity.ok(messages);
    }

    @Operation(summary = "Метод отправляет сообщение другому пользователю")
    @PostMapping
    public ResponseEntity<Message> sendMessage(
            @Valid @RequestBody MessageDto messageDto
    ) {
        Message message = messageService.sendMessage(messageDto);
        return ResponseEntity.ok(message);
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
