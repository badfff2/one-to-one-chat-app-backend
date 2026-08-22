package com.peter.websocket.chat;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document
public class ChatMessage {
    @Id
    @JsonIgnore
    private String id;

    @Indexed(unique = true)
    private String publicId;

    private String chatRoomId;
    private String senderId;
    private String recipientId;
    private String content;
    private Date timestamp;
}