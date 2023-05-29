package dev.steadypim.socialmediaapi.userActivity;

import dev.steadypim.socialmediaapi.post.Post;
import dev.steadypim.socialmediaapi.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Сервис ленты активности пользователя
 */
@Service
@RequiredArgsConstructor
public class UserActivityService {
    private final UserActivityRepository userActivityRepository;


    /**
     * Метод добавляет пост в ленту активности
     * @param user владелец поста
     * @param post пост
     */
    public void createPostActivity(User user, Post post) {
        userActivityRepository.save(UserActivity.builder()
                .timestamp(LocalDateTime.now())
                .post(post)
                .user(user)
                .build());
    }


    /**
     * Метод возвращает ленту активности пользователя
     * @param userId идентификатор пользователя
     * @param page номер страницы
     * @param size количество постов на странице
     * @return лента активности
     */
    public List<UserActivity> getUserActivityFeed(Integer userId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "timestamp"));
        return userActivityRepository.findByUserId(userId, pageable).getContent();
    }
}
