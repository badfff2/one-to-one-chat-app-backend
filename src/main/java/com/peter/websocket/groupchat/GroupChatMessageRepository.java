package com.peter.websocket.groupchat;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface GroupChatMessageRepository extends MongoRepository<GroupChatMessage, String> {
    List<GroupChatMessage> findByChatRoomId(String chatId);
}
