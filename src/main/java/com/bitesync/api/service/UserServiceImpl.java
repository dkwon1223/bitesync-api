package com.bitesync.api.service;

import com.bitesync.api.entity.User;
import com.bitesync.api.exception.DuplicateUserException;
import com.bitesync.api.exception.EntityNotFoundException;
import com.bitesync.api.repository.UserRepository;
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
        if(userRepository.existsByUsername(user.getEmail())) {
            throw new DuplicateUserException(user.getEmail());
        }
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    public User getUserByUsername(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        return unwrapUser(user, 404L);
    }

    @Override
    public User getById(Long id) {
        Optional<User> user = userRepository.findById(id);
        return unwrapUser(user, id);
    }

    static User unwrapUser(Optional<User> entity, Long id) {
        if(entity.isPresent()) {
            return entity.get();
        } else {
            throw new EntityNotFoundException(id, User.class);
        }
    }
}
