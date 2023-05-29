package dev.steadypim.socialmediaapi.userActivity;


import dev.steadypim.socialmediaapi.post.Post;
import dev.steadypim.socialmediaapi.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Лента активности пользователя
 * @field id - идентификатор пользователя
 * @field user - пользователь, выложивший пост
 * @field post - пост пользователя
 * @field timestamp - дата публикации
 */
@Entity
@Table(name = "user_activity")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserActivity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    private LocalDateTime timestamp;

}
