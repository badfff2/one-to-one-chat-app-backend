package com.peter.websocket.config;

import com.peter.websocket.chat.ChatMessage;
import com.peter.websocket.user.User;
import com.peter.websocket.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.Objects;


@Component
@Slf4j
@RequiredArgsConstructor
public class WebSocketEventListener {

    private final SimpMessageSendingOperations messagingTemplate;
    private final UserService userService;

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        var sessionAttributes = headerAccessor.getSessionAttributes();
        if (Objects.nonNull(sessionAttributes)) {
            String username = (String) sessionAttributes.get("username");
            if (Objects.nonNull(username)) {
                User user = new User();
                user.setNickName(username);
                userService.disconnect(user);
                messagingTemplate.convertAndSend("/user/public", user);
                log.info("User Disconnected : {}", username);
            }
        }
    }

}
