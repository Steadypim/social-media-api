package dev.steadypim.socialmediaapi.friend;

import dev.steadypim.socialmediaapi.user.User;
import jakarta.persistence.*;
import lombok.*;

/**
 * Друзья
 * @field id - идентификатор друга в БД
 * @field sender - отправитель запроса на дружбу
 * @field recipient - тот, кому отправили запрос
 * @field status - статус запроса
 */
@Entity
@Table(name = "friend_request")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FriendRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "sender_id", referencedColumnName = "id")
    private User sender;

    @ManyToOne
    @JoinColumn(name = "recipient_id", referencedColumnName = "id")
    private User recipient;

    @Enumerated(EnumType.STRING)
    private Status status;
}
