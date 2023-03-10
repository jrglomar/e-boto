package com.jrtg.eboto.service;

import com.jrtg.eboto.exception.RecordNotFoundException;
import com.jrtg.eboto.model.User;
import com.jrtg.eboto.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.shadow.com.univocity.parsers.common.record.Record;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserService userService = new UserServiceImpl();


    User user1;
    User user2;
    User user3;
    List<User> userList;

    @BeforeEach
    void setup() {

        user1 = User.builder()
                .userId(1L)
                .userName("jglomar")
                .userPassword("p@ssw0rd")
                .build();
        user2 = User.builder()
                .userId(2L)
                .userName("agutierrez")
                .userPassword("p@ssw0rd")
                .build();
        user3 = User.builder()
                .userId(3L)
                .userName("cbaquiran")
                .userPassword("p@ssw0rd")
                .build();

        userList = List.of(user1, user2, user3);


    }

    // Happy paths
    @Test
    @DisplayName("Finding user by user id")
    void findUserById() throws RecordNotFoundException {
        when(userRepository.findById(anyLong())).thenReturn(Optional.ofNullable(user1));

        User result = userService.findUserById(1L);

        verify(userRepository).findById(anyLong());
        assertThat(result).isEqualTo(user1);
    }


    @Test
    @DisplayName("Finding all users")
    void findAllUser() {
        when(userRepository.findAll()).thenReturn(userList);

        List<User> result = userService.findAllUser();

        verify(userRepository).findAll();
        assertThat(result).isEqualTo(userList);

    }

    @Test
    @DisplayName("Creating new user")
    void saveUser() {
        when(userRepository.save(any())).thenReturn(user1);

        User result = userService.saveUser(user1);

        verify(userRepository).save(user1);
        assertThat(result).isEqualTo(user1);
    }

    @Test
    @DisplayName("Updating an existing user")
    void updateUser() throws RecordNotFoundException {

        when(userRepository.findById(1L)).thenReturn(Optional.ofNullable(user1));

        User updateSample = new User(1L, "jglomar_updated", "p@ssw0rd");
        when(userRepository.save(any(User.class))).thenReturn(updateSample);
        User result = userService.updateUser(updateSample, 1L);

        verify(userRepository).findById(1L);
        verify(userRepository).save(updateSample);
        assertThat(result).isEqualTo(updateSample);

    }

    @Test
    @DisplayName("Deleting a user")
    void deleteUser() throws RecordNotFoundException {

        when(userRepository.findById(1L)).thenReturn(Optional.ofNullable(user1));

        User updateSample = new User(1L, "jglomar", "updat3d");
        String result = userService.deleteUser(1L);

        verify(userRepository).findById(1L);
        verify(userRepository).save(updateSample);
        assertThat(result).isEqualTo("Record deleted.");
    }
    // End of Happy paths

    @Test
    @DisplayName("Record not found")
    void findUserByIdError() throws RecordNotFoundException {

        assertThrows(RecordNotFoundException.class, () -> {
            userService.findUserById(1L);
        }, "Record to delete not found.");
    }
}