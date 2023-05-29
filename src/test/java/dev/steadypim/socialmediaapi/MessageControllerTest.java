package dev.steadypim.socialmediaapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.steadypim.socialmediaapi.message.Message;
import dev.steadypim.socialmediaapi.message.MessageController;
import dev.steadypim.socialmediaapi.message.MessageDto;
import dev.steadypim.socialmediaapi.message.MessageService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.containsString;

@RunWith(SpringRunner.class)
@WebMvcTest(MessageController.class)
public class MessageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MessageService messageService;

    @Test
    public void testGetMessagesBetweenUsers() throws Exception {

        Integer senderId = 1;
        Integer recipientId = 2;
        List<Message> messages = Arrays.asList(new Message(), new Message());
        Mockito.when(messageService.getMessagesBetweenUsers(senderId, recipientId)).thenReturn(messages);


        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/messages/{senderId}/{recipientId}", senderId, recipientId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(messages.size()));
    }

    @Test
    public void testSendMessage() throws Exception {

        MessageDto messageDto = new MessageDto();
        Message message = new Message();
        Mockito.when(messageService.sendMessage(messageDto)).thenReturn(message);


        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/messages")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(messageDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());
    }

    @Test
    public void testHandleValidationExceptions() throws Exception {

        MessageDto invalidMessageDto = new MessageDto(); // Invalid message DTO without required fields

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/messages")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(invalidMessageDto)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string(containsString("Ошибка валидации")));
    }

    @Test
    public void testHandleException() throws Exception {
        Mockito.when(messageService.getMessagesBetweenUsers(Mockito.anyInt(), Mockito.anyInt()))
                .thenThrow(new RuntimeException("Internal Server Error"));


        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/messages/{senderId}/{recipientId}", 1, 2))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andExpect(MockMvcResultMatchers.content().string(containsString("Внутренняя ошибка сервера")));
    }
}
