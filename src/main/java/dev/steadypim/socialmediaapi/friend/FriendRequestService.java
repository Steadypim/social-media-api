package dev.steadypim.socialmediaapi.friend;

import dev.steadypim.socialmediaapi.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Сервис добавления в друзья
 */
@Service
@RequiredArgsConstructor
public class FriendRequestService {

    private final FriendRequestRepository friendRequestRepository;
    private final UserRepository userRepository;

    /**
     * Метод принимает запрос дружбы
     * @param requestId идентификатор пользователя запрашивающего дружбу
     */
    public void acceptFriendRequest(Integer requestId) {
        FriendRequest friendship = friendRequestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Friendship request not found"));

        if (friendship.getStatus() == Status.PENDING) {
            friendship.setStatus(Status.ACCEPTED);
            friendRequestRepository.save(friendship);
        }
    }
    /**
     * Метод отклоняет запрос дружбы
     * @param requestId идентификатор пользователя запрашивающего дружбу
     */
    public void declineFriendRequest(Integer requestId) {
        FriendRequest friendship = friendRequestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Friendship request not found"));

        if (friendship.getStatus() == Status.PENDING) {
            friendship.setStatus(Status.DECLINED);
            friendRequestRepository.save(friendship);
        }
    }
    /**
     * Метод отправляет запрос дружбы
     * @param senderId идентификатор пользователя запрашивающего дружбу
     * @param recipientId идентификатор пользователя, которому отправляется запрос
     */
    public void sendFriendRequest(Integer senderId, Integer recipientId) {
        FriendRequest friendRequest = friendRequestRepository.findBySenderIdAndRecipientId(senderId, recipientId);

        if (friendRequest == null) {
            friendRequest = createFriendship(senderId, recipientId, Status.PENDING);
            friendRequestRepository.save(friendRequest);
        } else {
            throw new IllegalArgumentException("Friend request already sent");
        }
    }

    /**
     * Метод создает состояние "дружбы" между двумя пользователями
     * @param senderId идентификатор пользователя запрашивающего дружбу
     * @param recipientId идентификатор пользователя, которому отправляется запрос
     * @param status статус состояния
     * @return состояние "дружбы"
     */
    private FriendRequest createFriendship(Integer senderId, Integer recipientId, Status status) {
        FriendRequest friendship = new FriendRequest();
        friendship.setId(senderId);
        friendship.setRecipient(userRepository.findById(recipientId)
                .orElseThrow(() -> new RuntimeException("Invalid recipient id")));
        friendship.setStatus(status);
        return friendship;
    }


    /**
     * Метод удаляет друга
     * @param userId идентификатор удаляемого друга
     */
    public void removeFriend(Integer userId) {
        List<FriendRequest> friendships = friendRequestRepository.findBySender(userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Invalid user id")));

        for (FriendRequest friendship : friendships) {
            if (friendship.getStatus() == Status.ACCEPTED) {
                friendRequestRepository.delete(friendship);
            }
        }
    }
}
