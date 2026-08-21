package com.peter.websocket.user;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UserRepository  extends MongoRepository<User, String> {
    User findByNickName(String nickName);
}