package dev.steadypim.socialmediaapi.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Запрос на регистрацию
 * @field username - ник пользователя
 * @field password - пароль
 * @field email - электронная почта
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    private String username;
    private String email;
    private String password;
}
