package com.peter.websocket.groupchat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GroupChatMessageResponse {
    private String publicId;
    private String chatRoomId;
    private String senderId;
    private String content;
    private Date timestamp;
}
