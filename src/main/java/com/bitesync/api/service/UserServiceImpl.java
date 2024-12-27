package com.bitesync.api.service;

import com.bitesync.api.entity.User;
import com.bitesync.api.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void signupUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    public User getUserByUsername(String username) {
        return null;
    }

    static User unwrapUser(Optional<User> entity, Long id) {
        if(entity.isPresent()) {
            return entity.get();
        } else {
            throw new EntityNotFoundException(id, User.class);
        }
    }
}
