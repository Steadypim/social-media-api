package dev.steadypim.socialmediaapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.steadypim.socialmediaapi.friend.FriendRequestController;
import dev.steadypim.socialmediaapi.friend.FriendRequestService;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.core.MethodParameter;
import org.springframework.test.context.junit4.SpringRunner;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.bind.MethodArgumentNotValidException;

import static org.hamcrest.Matchers.containsString;

@RunWith(SpringRunner.class)
@WebMvcTest(FriendRequestController.class)
public class FriendRequestControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FriendRequestService friendRequestService;

    @Test
    public void testSendFriendRequest() throws Exception {

        Integer senderId = 1;
        Integer recipientId = 2;


        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/friends/requests")
                        .param("senderId", senderId.toString())
                        .param("recipientId", recipientId.toString()))
                .andExpect(MockMvcResultMatchers.status().isOk());

        Mockito.verify(friendRequestService).sendFriendRequest(senderId, recipientId);
    }

    @Test
    public void testAcceptFriendRequest() throws Exception {

        Integer requestId = 1;


        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/friends/requests/{requestId}/accept", requestId))
                .andExpect(MockMvcResultMatchers.status().isOk());

        Mockito.verify(friendRequestService).acceptFriendRequest(requestId);
    }

    @Test
    public void testDeclineFriendRequest() throws Exception {

        Integer requestId = 1;


        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/friends/requests/{requestId}/decline", requestId))
                .andExpect(MockMvcResultMatchers.status().isOk());

        Mockito.verify(friendRequestService).declineFriendRequest(requestId);
    }

    @Test
    public void testRemoveFriend() throws Exception {

        Integer userId = 1;


        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/friends/friends/{userId}", userId))
                .andExpect(MockMvcResultMatchers.status().isOk());

        Mockito.verify(friendRequestService).removeFriend(userId);
    }

    @Test
    public void testHandleValidationExceptions() throws Exception {

        MethodArgumentNotValidException ex = new MethodArgumentNotValidException((MethodParameter) null, null);


        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/friends/requests")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(ex)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string(containsString("Ошибка валидации")));
    }

    @Test
    public void testHandleException() throws Exception {
        Exception ex = new Exception("Internal Server Error");


        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/friends/requests")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(ex)))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andExpect(MockMvcResultMatchers.content().string(containsString("Внутренняя ошибка сервера")));
    }
}
