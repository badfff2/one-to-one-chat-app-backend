package com.peter.websocket.groupchat;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface GroupChatRepository extends MongoRepository<GroupChat, String> {
    GroupChat findByRoomId(String roomId);
}
