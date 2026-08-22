package com.peter.websocket.chat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatNotification {
    private String publicId;
    private String chatRoomId;
    private String senderId;
    private String recipientId;
    private String content;
    private Date timestamp;
}