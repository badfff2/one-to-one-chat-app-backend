package com.peter.websocket.groupchat;


import com.peter.websocket.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GroupChatService {
    private final GroupChatRepository groupChatRepository;
    private final GroupChatMessageRepository groupChatMessageRepository;

    private final UserService userService;

    public GroupChatMessage save(GroupChatMessage groupChatMessage) {
        GroupChat chatroom = groupChatRepository.findByRoomId(groupChatMessage.getChatRoomId());

        if (chatroom == null) {
            System.out.println("Chatroom not found");
            return null;
        }

        if(groupChatMessage.getPublicId() == null) {
            groupChatMessage.setPublicId(UUID.randomUUID().toString());
        }

        return groupChatMessageRepository.save(groupChatMessage);
    }

    public List<GroupChatMessage> findGroupMessages(String chatRoomId){
        GroupChat chatroom = groupChatRepository.findByRoomId(chatRoomId);

        if (chatroom == null) {
            System.out.println("Chatroom not found");
            return null;
        }

        return groupChatMessageRepository.findByChatRoomId(chatroom.getRoomId());
    }

    public List<GroupChat> findGroupList() {

        List<GroupChat> groupList  = groupChatRepository.findAll();

        if(groupList.isEmpty()){
            System.out.println("No groups found, creating new group");
            createDefaultGroupChat();
            return groupChatRepository.findAll();
        }

        return groupList;
    }

    private void createDefaultGroupChat(){
        GroupChat groupChat = new GroupChat();
        groupChat.setRoomId(UUID.randomUUID().toString());
        groupChat.setRoomName("Public Chatroom");
        groupChat.setMemberIdlist(userService.findAllPublicIds());
        groupChatRepository.save(groupChat);
    }
}
