package com.peter.websocket.chatroom;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;

    public Optional<String> getChatRoomId(
            String senderId,
            String recipientId,
            boolean createNewRoomIfNotExists
    ) {
        var chatId = canonicalChatId(senderId, recipientId);
        return chatRoomRepository
                .findByChatId(chatId)
                .map(ChatRoom::getChatId)
                .or(() -> {
                    if(createNewRoomIfNotExists) {
                        var created = createChatId(senderId, recipientId);
                        return Optional.of(created);
                    }

                    return Optional.empty();
                });
    }

    private String canonicalChatId(String senderId, String recipientId) {
        if (senderId.compareTo(recipientId) <= 0) {
            return String.format("%s_%s", senderId, recipientId);
        } else {
            return String.format("%s_%s", recipientId, senderId);
        }
    }

    private String createChatId(String senderId, String recipientId) {
        var chatId = canonicalChatId(senderId, recipientId);

        String first = senderId.compareTo(recipientId) <= 0 ? senderId : recipientId;
        String second = senderId.compareTo(recipientId) <= 0 ? recipientId : senderId;

        ChatRoom chatRoom = ChatRoom
                .builder()
                .chatId(chatId)
                .senderId(first)
                .recipientId(second)
                .build();

        chatRoomRepository.save(chatRoom);

        return chatId;
    }
}