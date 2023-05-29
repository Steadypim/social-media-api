package dev.steadypim.socialmediaapi;

import dev.steadypim.socialmediaapi.user.UserController;
import dev.steadypim.socialmediaapi.user.UserService;
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
@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    public void testSubscribeUser() throws Exception {
        // Arrange
        Integer subscriberId = 1;
        Integer targetUserId = 2;

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/users/{subscriberId}/subscribe/{targetUserId}",
                        subscriberId, targetUserId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Пользователь успешно подписан."));

        Mockito.verify(userService, Mockito.times(1)).subscribeUser(subscriberId, targetUserId);
    }

    @Test
    public void testUnsubscribeUser() throws Exception {
        // Arrange
        Integer subscriberId = 1;
        Integer targetUserId = 2;

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/users/{subscriberId}/unsubscribe/{targetUserId}",
                        subscriberId, targetUserId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Пользователь успешно отписан."));

        Mockito.verify(userService, Mockito.times(1)).unsubscribeUser(subscriberId, targetUserId);
    }
}
