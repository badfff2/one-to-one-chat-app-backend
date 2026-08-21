package com.peter.websocket.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;

    public User connectUser(User user){
        User foundUser = findByNickName(user.getNickName());
        if(foundUser != null){
            return saveUser(foundUser, Status.ONLINE);
        }
        else{
            user.setPublicId(UUID.randomUUID().toString());
            return saveUser(user, Status.ONLINE);
        }
    }

    public User disconnect(User user) {
        User storedUser = findByPublicId(user.getPublicId());
        if (storedUser != null) {
            saveUser(storedUser, Status.OFFLINE);
            return storedUser;
        }
        return null;
    }

    public User saveUser(User user, Status status) {
        user.setStatus(status);
        return repository.save(user);
    }

    public List<User> findAllUsers() {
        return repository.findAll();
    }
    public User findByNickName(String nickName) {return repository.findByNickName(nickName);}
    public User findByPublicId(String publicId) {return repository.findByPublicId(publicId);}
}