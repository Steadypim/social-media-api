package dev.steadypim.socialmediaapi.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageDto {
    private Integer senderId;
    private Integer recipientId;
    private String content;
}
