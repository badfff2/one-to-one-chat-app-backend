package com.peter.websocket.groupchat;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class GroupChatController {

    private final SimpMessagingTemplate messagingTemplate;
    private final GroupChatService groupChatService;

    @MessageMapping("/chat/group/{groupId}")
    public void processMessage(@Payload GroupChatMessage groupChatMessage) {
        GroupChatMessage savedMsg = groupChatService.save(groupChatMessage);

        String destination = "/group/" + savedMsg.getChatRoomId() + "/message";

        messagingTemplate.convertAndSend(
                destination,
                new GroupChatMessageResponse(
                        savedMsg.getPublicId(),
                        savedMsg.getChatRoomId(),
                        savedMsg.getSenderId(),
                        savedMsg.getContent(),
                        savedMsg.getTimestamp()
                )
        );
    }

    @GetMapping("/group")
    public ResponseEntity<List<GroupChatResponse>> findGroupList() {
        List<GroupChat> groupList = groupChatService.findGroupList();

        List<GroupChatResponse> response = groupList.stream()
                .map(msg -> GroupChatResponse.builder()
                        .roomId(msg.getRoomId())
                        .roomName(msg.getRoomName())
                        .memberIdlist(msg.getMemberIdlist())
                        .build())
                .toList();
        return ResponseEntity.ok(response);
    }


    @GetMapping("/messages/group/{groupId}")
    public ResponseEntity<List<GroupChatMessageResponse>> findGroupChatMessages(@PathVariable String groupId) {

        List<GroupChatMessage> groupChatMessages = groupChatService.findGroupMessages(groupId);

        List<GroupChatMessageResponse> response = groupChatMessages.stream()
                .map(msg -> GroupChatMessageResponse.builder()
                        .publicId(msg.getPublicId())
                        .chatRoomId(msg.getChatRoomId())
                        .senderId(msg.getSenderId())
                        .content(msg.getContent())
                        .timestamp(msg.getTimestamp())
                        .build())
                .toList();

        return ResponseEntity.ok(response);
    }
}
