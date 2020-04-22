package org.zlatenov.accountmanaging.service;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.zlatenov.accountmanaging.exception.InvalidUserException;
import org.zlatenov.accountmanaging.model.dto.UserDto;
import org.zlatenov.accountmanaging.model.entity.User;
import org.zlatenov.accountmanaging.repository.UserRepository;
import org.zlatenov.accountmanaging.util.ValidationUtil;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * @author Angel Zlatenov
 */
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    public static final String USER_WITH_THAT_EMAIL_DOESNT_EXISTS = "User with that email doesnt exists";
    public static final String USER_WITH_THAT_EMAIL_ALREADY_EXISTS = "User with that email already exists";
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validator;

    @Override
    public Collection<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Transactional
    @Override
    public void deleteUserByEmail(String email) {
        if(userRepository.existsByEmail(email)) {
            userRepository.deleteByEmail(email);
        }
        else {
            throw new InvalidUserException(USER_WITH_THAT_EMAIL_DOESNT_EXISTS);
        }
    }

    @Override
    public User createUser(UserDto userDto) {
        if (!validator.isValid(userDto)) {
            throw new InvalidUserException(validator.getViolations(userDto)
                                                   .stream()
                                                   .map(ConstraintViolation::getMessage)
                                                   .collect(Collectors.joining(",")));
        }
        if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new InvalidUserException(USER_WITH_THAT_EMAIL_ALREADY_EXISTS);
        }
        return userRepository.saveAndFlush(modelMapper.map(userDto, User.class));
    }

    @Override
    public User updateUser(UserDto userDto) {
        User user = userRepository.findByEmail(userDto.getEmail());
        if(user == null) {
            throw new InvalidUserException(USER_WITH_THAT_EMAIL_DOESNT_EXISTS);
        }
        long id = user.getId();
        BeanUtils.copyProperties(modelMapper.map(userDto, User.class), user);
        user.setId(id);
        return userRepository.saveAndFlush(user);
    }
}
