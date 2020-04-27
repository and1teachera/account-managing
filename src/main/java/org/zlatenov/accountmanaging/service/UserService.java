package org.zlatenov.accountmanaging.service;

import org.zlatenov.accountmanaging.model.dto.UserDto;

import java.util.Collection;

/**
 * @author Angel Zlatenov
 */

public interface UserService {

    Collection<UserDto> getUsers();

    UserDto getUserByEmail(String email);

    void createUser(UserDto userDto);

    void updateUser(UserDto userDto);

    void deleteUserByEmail(String email);
}
