package com.peter.websocket.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;

    public User saveUser(User user) {
        user.setStatus(Status.ONLINE);
        return repository.save(user);
    }

    public User connectUser(User user){
        User foundUser = findByNickName(user.getNickName());
        if(foundUser != null){
            return saveUser(foundUser);
        }
        else{
            return saveUser(user);
        }
    }

    public void disconnect(User user) {
        User storedUser = repository.findByNickName(user.getNickName());
        if (storedUser != null) {
            storedUser.setStatus(Status.OFFLINE);
            repository.save(storedUser);
        }
    }

    public List<User> findAllUsers() {
        return repository.findAll();
    }
    public User findByNickName(String nickName) {return repository.findByNickName(nickName);}
}