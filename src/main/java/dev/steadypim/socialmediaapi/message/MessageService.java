package dev.steadypim.socialmediaapi.message;

import dev.steadypim.socialmediaapi.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;
    private final UserRepository userRepository;

    public List<Message> getMessagesBetweenUsers(Integer senderId, Integer recipientId) {
        return messageRepository.findBySenderIdAndRecipientIdOrSenderIdAndRecipientIdOrderByTimestampAsc(
                senderId, recipientId, recipientId, senderId
        );
    }

    public Message sendMessage(MessageDto messageDto) {
        Message message = new Message();
        message.setId(messageDto.getSenderId());
        message.setRecipient(userRepository.findById(messageDto.getRecipientId())
                .orElseThrow(() -> new RuntimeException("Invalid recipient id")));
        message.setContent(messageDto.getContent());
        message.setTimestamp(LocalDateTime.now());

        return messageRepository.save(message);
    }
}
