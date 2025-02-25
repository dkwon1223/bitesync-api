package com.bitesync.api.service;

import com.bitesync.api.entity.User;
import com.bitesync.api.exception.DuplicateUserException;
import com.bitesync.api.exception.EntityNotFoundException;
import com.bitesync.api.exception.InvalidPasswordException;
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
        if(userRepository.existsByEmail(user.getEmail())) {
            throw new DuplicateUserException(user.getEmail());
        }
        if(!isStrongPassword(user.getPassword())) {
            throw new InvalidPasswordException();
        }
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    public User getUserByEmail(String username) {
        Optional<User> user = userRepository.findByEmail(username);
        return unwrapUser(user, 404L);
    }

    @Override
    public User getById(Long id) {
        Optional<User> user = userRepository.findById(id);
        return unwrapUser(user, id);
    }

    public static User unwrapUser(Optional<User> entity, Long id) {
        if(entity.isPresent()) {
            return entity.get();
        } else {
            throw new EntityNotFoundException(id, User.class);
        }
    }

    private boolean isStrongPassword(String password) {
        return password.length() >= 8 &&
               password.length() <= 20 &&
               password.matches(".*[0-9].*") &&
               password.matches(".*[a-z].*") &&
               password.matches(".*[A-Z].*") &&
               password.matches(".*[@#$%^&+=!/].*") &&
               !password.contains(" ");
    }
}
