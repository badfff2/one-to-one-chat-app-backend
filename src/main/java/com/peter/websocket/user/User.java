package com.peter.websocket.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document
public class User {
    @Id
    @JsonIgnore
    private String id;

    @Indexed(unique = true)
    private String publicId;

    @Indexed(unique = true)
    private String nickName;

    private String fullName;
    private Status status;
}