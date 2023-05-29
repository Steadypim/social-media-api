package dev.steadypim.socialmediaapi.message;

import dev.steadypim.socialmediaapi.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * Конвертер сообщений
 */
@Component
@RequiredArgsConstructor
public class MessageMapper {
    private final UserRepository userRepository;

    public Message toEntity(MessageDto messageDto){
        return Message.builder()
                .id(messageDto.getSenderId())
                .recipient(userRepository.findById(messageDto.getRecipientId())
                        .orElseThrow(() -> new RuntimeException("Invalid recipient id")))
                .content(messageDto.getContent())
                .timestamp(LocalDateTime.now())
                .sender(userRepository.findById(messageDto.getSenderId())
                        .orElseThrow(() -> new RuntimeException("Invalid sender id")))
                .build();
    }
}
