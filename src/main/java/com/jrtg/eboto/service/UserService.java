package com.jrtg.eboto.service;

import com.jrtg.eboto.exception.RecordNotFoundException;
import com.jrtg.eboto.model.User;

import java.util.List;


public interface UserService {
    User findUserById(Long id) throws RecordNotFoundException;

    List<User> findAllUser();

    User saveUser(User user);

    User updateUser(User user, Long id) throws RecordNotFoundException;

    String deleteUser(Long id) throws RecordNotFoundException;
}
