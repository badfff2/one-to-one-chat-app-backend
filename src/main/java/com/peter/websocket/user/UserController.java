package com.peter.websocket.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Objects;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @MessageMapping("/user.addUser")
    @SendTo("/topic/public")
    public User addUser(
            @Payload User user,
            SimpMessageHeaderAccessor headerAccessor
    ) {
        Objects.requireNonNull(headerAccessor.getSessionAttributes()).put("username", user.getNickName());
        return userService.connectUser(user);
    }

    @MessageMapping("/user.connectUser")
    @SendTo("/topic/public")
    public User connectUser(
            @Payload User user,
            SimpMessageHeaderAccessor headerAccessor
    ){
        Objects.requireNonNull(headerAccessor.getSessionAttributes()).put("username", user.getNickName());
        return userService.connectUser(user);
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