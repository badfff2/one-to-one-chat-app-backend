package com.peter.websocket.chat;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class ChatController {

    private final SimpMessagingTemplate messagingTemplate;
    private final ChatMessageService chatMessageService;

    @MessageMapping("/chat")
    public void processMessage(@Payload ChatMessage chatMessage) {
        ChatMessage savedMsg = chatMessageService.save(chatMessage);
        messagingTemplate.convertAndSendToUser(
                chatMessage.getRecipientId(), "/queue/messages",
                new ChatMessageResponse(
                        savedMsg.getPublicId(),
                        savedMsg.getChatRoomId(),
                        savedMsg.getSenderId(),
                        savedMsg.getRecipientId(),
                        savedMsg.getContent(),
                        savedMsg.getTimestamp()
                )
        );
    }

    @GetMapping("/messages/{senderId}/{recipientId}")
    public ResponseEntity<List<ChatMessageResponse>> findChatMessages(@PathVariable String senderId,
                                                              @PathVariable String recipientId) {

        List<ChatMessage> chatMessages = chatMessageService.findChatMessages(senderId, recipientId);

        List<ChatMessageResponse> response = chatMessages.stream()
                .map(msg -> ChatMessageResponse.builder()
                        .publicId(msg.getPublicId())
                        .chatRoomId(msg.getChatRoomId())
                        .senderId(msg.getSenderId())
                        .recipientId(msg.getRecipientId())
                        .content(msg.getContent())
                        .timestamp(msg.getTimestamp())
                        .build())
                .toList();

        return ResponseEntity.ok(response);
    }
}