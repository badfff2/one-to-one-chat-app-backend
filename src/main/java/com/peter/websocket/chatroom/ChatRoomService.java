package com.peter.websocket.chatroom;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;

    public Optional<String> getChatRoomId(
            String memberOneId,
            String memberTwoId,
            boolean createNewRoomIfNotExists
    ) {
        var chatId = canonicalChatId(memberOneId, memberTwoId);
        return chatRoomRepository
                .findByChatId(chatId)
                .map(ChatRoom::getChatId)
                .or(() -> {
                    if(createNewRoomIfNotExists) {
                        var created = createChatId(memberOneId, memberTwoId);
                        return Optional.of(created);
                    }

                    return Optional.empty();
                });
    }

    private String canonicalChatId(String memberOneId, String memberTwoId) {
        if (memberOneId.compareTo(memberTwoId) <= 0) {
            return String.format("%s_%s", memberOneId, memberTwoId);
        } else {
            return String.format("%s_%s", memberTwoId, memberOneId);
        }
    }

    private String createChatId(String memberOneId, String memberTwoId) {
        var chatId = canonicalChatId(memberOneId, memberTwoId);

        String first = memberOneId.compareTo(memberTwoId) <= 0 ? memberOneId : memberTwoId;
        String second = memberOneId.compareTo(memberTwoId) <= 0 ? memberTwoId : memberOneId;

        ChatRoom chatRoom = ChatRoom
                .builder()
                .chatId(chatId)
                .memberOneId(first)
                .memberTwoId(second)
                .build();

        chatRoomRepository.save(chatRoom);

        return chatId;
    }
}