package com.bitesync.api.service;

import com.bitesync.api.entity.User;

public interface UserService {
    void signupUser(User user);
    User getUserByEmail(String email);
    User getById(Long id);
}
