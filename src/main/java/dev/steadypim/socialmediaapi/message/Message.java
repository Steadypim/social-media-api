package dev.steadypim.socialmediaapi.message;

import dev.steadypim.socialmediaapi.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Сообщения
 * @field id - идентификатор сообщения в БД
 * @field sender - отправитель сообщения
 * @field recipient - получатель сообщения
 * @field timestamp - время отправки сообщения
 */
@Entity
@Table(name = "message")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "sender_id", referencedColumnName = "id")
    private User sender;

    @ManyToOne
    @JoinColumn(name = "recipient_id", referencedColumnName = "id")
    private User recipient;

    private String content;

    private LocalDateTime timestamp;
}
