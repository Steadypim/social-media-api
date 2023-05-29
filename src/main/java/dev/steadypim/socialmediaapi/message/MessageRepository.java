package dev.steadypim.socialmediaapi.message;

import dev.steadypim.socialmediaapi.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {
    List<Message> findBySenderAndRecipientOrRecipientAndSenderOrderByTimestampAsc(User sender1, User recipient1, User sender2, User recipient2);

    List<Message> findBySenderIdAndRecipientIdOrSenderIdAndRecipientIdOrderByTimestampAsc(Integer senderId, Integer recipientId, Integer recipientId1, Integer senderId1);
}
