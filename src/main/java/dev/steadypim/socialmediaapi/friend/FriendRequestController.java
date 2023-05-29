package dev.steadypim.socialmediaapi.friend;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/friends")
@RequiredArgsConstructor
public class FriendRequestController {
    private final FriendRequestService friendRequestService;

    @PostMapping("/requests")
    public ResponseEntity<Void> sendFriendRequest(@RequestParam Integer senderId, @RequestParam Integer recipientId) {
        friendRequestService.sendFriendRequest(senderId, recipientId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/requests/{requestId}/accept")
    public ResponseEntity<Void> acceptFriendRequest(@PathVariable Integer requestId) {
        friendRequestService.acceptFriendRequest(requestId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/requests/{requestId}/decline")
    public ResponseEntity<Void> declineFriendRequest(@PathVariable Integer requestId) {
        friendRequestService.declineFriendRequest(requestId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/friends/{userId}")
    public ResponseEntity<Void> removeFriend(@PathVariable Integer userId) {
        friendRequestService.removeFriend(userId);
        return ResponseEntity.ok().build();
    }
}
