package org.zlatenov.accountmanaging.service;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.zlatenov.accountmanaging.model.dto.UserDto;
import org.zlatenov.accountmanaging.model.entity.User;
import org.zlatenov.accountmanaging.repository.UserRepository;

import java.util.Collection;

/**
 * @author Angel Zlatenov
 */
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public Collection<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public void deleteUser(User user) {
        userRepository.delete(user);
    }

    @Override
    public User createUser(UserDto userDto) {
        return userRepository.saveAndFlush(modelMapper.map(userDto, User.class));
    }

    @Override
    public User updateUser(UserDto userDto) {
        User user = userRepository.findByEmail(userDto.getEmail());
        BeanUtils.copyProperties(modelMapper.map(userDto, User.class), user);
        return userRepository.saveAndFlush(user);
    }
}
