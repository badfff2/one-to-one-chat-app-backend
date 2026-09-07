package com.peter.websocket.groupchat;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document
public class GroupChat {
    @Id
    private String id;

    @Indexed(unique = true)
    private String roomId;
    private String roomName;
    private Set<String> memberIdlist;
}
