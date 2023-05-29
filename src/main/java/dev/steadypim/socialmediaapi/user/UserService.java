package dev.steadypim.socialmediaapi.user;

import dev.steadypim.socialmediaapi.friend.FriendRequest;
import dev.steadypim.socialmediaapi.friend.FriendRequestRepository;
import dev.steadypim.socialmediaapi.friend.Status;
import dev.steadypim.socialmediaapi.message.Message;
import dev.steadypim.socialmediaapi.message.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final FriendRequestRepository friendRequestRepository;
    private final MessageRepository messageRepository;
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        return User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .email(user.getEmail())
                .build();
    }

    public User getCurrentUser(Authentication authentication) {
        String username = authentication.getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }

    public void sendFriendRequest(User sender, User recipient) {
        FriendRequest friendRequest = FriendRequest.builder()
                .sender(sender)
                .recipient(recipient)
                .status(Status.PENDING)
                .build();
        friendRequestRepository.save(friendRequest);
    }

    public void acceptFriendRequest(FriendRequest friendRequest) {
        friendRequest.setStatus(Status.ACCEPTED);
        friendRequestRepository.save(friendRequest);

        User sender = friendRequest.getSender();
        User recipient = friendRequest.getRecipient();
        sender.getFriends().add(recipient);
        recipient.getFriends().add(sender);
        userRepository.save(sender);
        userRepository.save(recipient);
    }

    public void rejectFriendRequest(FriendRequest friendRequest) {
        friendRequestRepository.delete(friendRequest);
    }

    public void removeFriend(User user, User friend) {
        user.getFriends().remove(friend);
        friend.getFriends().remove(user);
        userRepository.save(user);
        userRepository.save(friend);
    }

    public void sendMessage(User sender, User recipient, String content) {
        Message message = new Message();
        message.setSender(sender);
        message.setRecipient(recipient);
        message.setContent(content);
        message.setTimestamp(LocalDateTime.now());
        messageRepository.save(message);
    }

    public List<Message> getMessages(User user1, User user2) {
        return messageRepository.findBySenderAndRecipientOrRecipientAndSenderOrderByTimestampAsc(user1, user2, user1, user2);
    }

}
