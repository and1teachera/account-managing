package org.zlatenov.accountmanaging.service;

import org.zlatenov.accountmanaging.model.dto.UserDto;
import org.zlatenov.accountmanaging.model.entity.User;

import java.util.Collection;

/**
 * @author Angel Zlatenov
 */

public interface UserService {

    Collection<User> getUsers();

    User getUserByEmail(String email);

    void deleteUser(User user);

    User createUser(UserDto userDto);

    User updateUser(UserDto userDto);


}