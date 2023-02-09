package com.jrtg.eboto.service;

import com.jrtg.eboto.exception.RecordNotFoundException;
import com.jrtg.eboto.model.User;


public interface UserService {
    User findUserById(Long id) throws RecordNotFoundException;

    Iterable<User> findAllUser();

    User saveUser(User user);

    User updateUser(User user, Long id) throws RecordNotFoundException;

    String deleteUser(Long id) throws RecordNotFoundException;
}
