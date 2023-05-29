package dev.steadypim.socialmediaapi.user;

import dev.steadypim.socialmediaapi.friend.FriendRequest;
import dev.steadypim.socialmediaapi.message.Message;
import dev.steadypim.socialmediaapi.post.Post;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

/**
 * Пользователь
 * @field id - идентификатор пользователя в БД
 * @field username - логин пользователя
 * @field password - пароль пользователя
 * @field role - роль пользователя
 * @field sentFriendRequests - отправленные запросы на дружбу
 * @field receivedFriendRequests - принятые запросы на дружбу
 * @field friends - друзья пользователя
 * @field subscribers - подписчики пользователя
 * @field receivedMessages - поступившие сообщения
 * @field subscriptions - отправленные сообщения
 * @field posts - посты пользователя
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "_user")
public class User implements UserDetails {

    @Id
    @GeneratedValue
    private Integer id;

    private String username;

    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "sender")
    private List<FriendRequest> sentFriendRequests;

    @OneToMany(mappedBy = "recipient")
    private List<FriendRequest> receivedFriendRequests;

    @ManyToMany
    @JoinTable(
            name = "user_friends",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "friend_id")
    )
    private List<User> friends;

    @ManyToMany(mappedBy = "friends")
    private List<User> subscribers;

    @OneToMany(mappedBy = "sender")
    private List<Message> sentMessages;

    @OneToMany(mappedBy = "recipient")
    private List<Message> receivedMessages;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_subscription",
            joinColumns = @JoinColumn(name = "subscriber_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> subscriptions = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Post> posts = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
