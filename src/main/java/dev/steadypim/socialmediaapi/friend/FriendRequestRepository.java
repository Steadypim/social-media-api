package dev.steadypim.socialmediaapi.friend;

import dev.steadypim.socialmediaapi.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FriendRequestRepository extends JpaRepository<FriendRequest, Integer> {
    List<FriendRequest> findBySender(User user);

    FriendRequest findBySenderIdAndRecipientId(Integer senderId, Integer recipientId);
}
