package com.peter.websocket.user;

import com.peter.websocket.chat.ChatNotification;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Objects;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/user.connectUser")
    @SendTo("/topic/public")
    public User connectUser(
            @Payload User user,
            SimpMessageHeaderAccessor headerAccessor
    ){
        Objects.requireNonNull(headerAccessor.getSessionAttributes()).put("username", user.getNickName());
        User userInfo = userService.connectUser(user);

        messagingTemplate.convertAndSendToUser(
                userInfo.getNickName(),
                "/systemInfo",
                userInfo
        );
        return userInfo;
    }

    @MessageMapping("/user.disconnectUser")
    @SendTo("/topic/public")
    public User disconnectUser(
            @Payload User user
    ) {
        userService.disconnect(user);
        return user;
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> findAllUsers() {
        return ResponseEntity.ok(userService.findAllUsers());
    }
}