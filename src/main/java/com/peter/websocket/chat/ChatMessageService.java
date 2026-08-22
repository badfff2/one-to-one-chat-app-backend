package com.peter.websocket.chat;

import com.peter.websocket.chatroom.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ChatMessageService {
    private final ChatMessageRepository repository;
    private final ChatRoomService chatRoomService;

    public ChatMessage save(ChatMessage chatMessage) {
        var chatRoomId = chatRoomService
                .getChatRoomId(chatMessage.getSenderId(), chatMessage.getRecipientId(), true)
                .orElseThrow(); // You can create your own dedicated exception
        chatMessage.setChatRoomId(chatRoomId);
        if(chatMessage.getPublicId() == null) {
            chatMessage.setPublicId(UUID.randomUUID().toString());
        }
        return repository.save(chatMessage);
    }

    public List<ChatMessage> findChatMessages(String senderId, String recipientId) {
        var chatRoomId = chatRoomService.getChatRoomId(senderId, recipientId, false);
        return chatRoomId.map(repository::findByChatRoomId).orElse(new ArrayList<>());
    }
}