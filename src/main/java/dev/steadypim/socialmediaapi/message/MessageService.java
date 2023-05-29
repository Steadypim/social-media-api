package dev.steadypim.socialmediaapi.message;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * Сервис сообщений
 */
@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;
    private final MessageMapper messageMapper;

    /**
     * Возвращает сообщения пользователей
     * @param senderId идентификатор пользователя, который отправлял сообщение
     * @param recipientId идентификатор пользователя, который принимал сообщение
     * @return сообщение
     */
    public List<Message> getMessagesBetweenUsers(Integer senderId, Integer recipientId) {
        return messageRepository.findBySenderIdAndRecipientIdOrSenderIdAndRecipientIdOrderByTimestampAsc(
                senderId, recipientId, recipientId, senderId
        );
    }

    /**
     * Метод сохраняет сообщение
     * @param messageDto дто сообщения
     * @return сохраненное сообщение
     */
    public Message sendMessage(MessageDto messageDto) {
        return messageRepository.save(messageMapper.toEntity(messageDto));
    }
}
