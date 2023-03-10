package com.jrtg.eboto.service;

import com.jrtg.eboto.exception.RecordNotFoundException;
import com.jrtg.eboto.model.User;
import com.jrtg.eboto.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public User findUserById(Long id) throws RecordNotFoundException {
        return userRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("Record not found."));
    }

    @Override
    public List<User> findAllUser() {
        return userRepository.findAll();
    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User updateUser(User userRequest, Long id) throws RecordNotFoundException {
        User userFound = userRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Record to update not found."));
        
        userFound.setUserName(userRequest.getUserName());
        userFound.setUserPassword(userRequest.getUserPassword());
        
        return userRepository.save(userFound);
    }

    @Override
    public String deleteUser(Long id) throws RecordNotFoundException {
        User userFound = userRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Record to delete not found."));
        
        userFound.setUserPassword("updat3d");
        userRepository.save(userFound);
        return "Record deleted.";
    }
}
