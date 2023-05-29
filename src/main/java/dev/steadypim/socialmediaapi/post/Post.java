package dev.steadypim.socialmediaapi.post;

import dev.steadypim.socialmediaapi.image.Image;
import dev.steadypim.socialmediaapi.user.User;
import dev.steadypim.socialmediaapi.userActivity.UserActivity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * Посты
 * @field id - идентификатор поста в БД
 * @field title - заголовок поста
 * @field text - текст поста
 * @field images - изображения, прикрепленные к посту
 * @field userActivities - активности в ленте
 * @field user - пользователь, которому принадлежат посты
 */
@Entity
@Table(name = "post")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;

    private String text;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "posts_images",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "image_id")
    )
    private List<Image> images;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserActivity> userActivities = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
