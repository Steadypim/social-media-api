package dev.steadypim.socialmediaapi;

import dev.steadypim.socialmediaapi.post.Post;
import dev.steadypim.socialmediaapi.post.PostService;
import dev.steadypim.socialmediaapi.user.User;
import dev.steadypim.socialmediaapi.user.UserService;
import dev.steadypim.socialmediaapi.userActivity.UserActivity;
import dev.steadypim.socialmediaapi.userActivity.UserActivityController;
import dev.steadypim.socialmediaapi.userActivity.UserActivityService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@WebMvcTest(UserActivityController.class)
public class UserActivityControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserActivityService userActivityService;

    @MockBean
    private UserService userService;

    @MockBean
    private PostService postService;

    @Test
    public void testCreatePostActivity() throws Exception {
        // Arrange
        Integer userId = 1;
        Integer postId = 2;
        User user = new User();
        Post post = new Post();

        Mockito.when(userService.getUserById(userId)).thenReturn(user);
        Mockito.when(postService.findById(postId)).thenReturn(post);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/activities/users/{userId}/posts/{postId}",
                        userId, postId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Лента активности пользователя создана успешно."));

        Mockito.verify(userActivityService, Mockito.times(1)).createPostActivity(user, post);
    }

    @Test
    public void testGetUserActivityFeed() throws Exception {
        // Arrange
        Integer userId = 1;
        int page = 0;
        int size = 10;
        List<UserActivity> userActivityFeed = new ArrayList<>();

        Mockito.when(userActivityService.getUserActivityFeed(userId, page, size)).thenReturn(userActivityFeed);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/activities/users/{userId}/feed", userId)
                        .param("page", String.valueOf(page))
                        .param("size", String.valueOf(size)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(userActivityFeed.size()));

        Mockito.verify(userActivityService, Mockito.times(1)).getUserActivityFeed(userId, page, size);
    }
}
