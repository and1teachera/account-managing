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
    public Collection<UserDto> getUsers() {
        return userRepository.findAll()
                .stream()
                .map(user -> modelMapper.map(user, UserDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public UserDto getUserByEmail(String email) {
        return modelMapper.map(userRepository.findByEmail(email).orElseThrow(() -> new InvalidUserException(USER_WITH_THAT_EMAIL_DOESNT_EXISTS)), UserDto.class);
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
    public void createUser(UserDto userDto) {
        if (!validator.isValid(userDto)) {
            throw new InvalidUserException(validator.getViolations(userDto)
                                                   .stream()
                                                   .map(ConstraintViolation::getMessage)
                                                   .collect(Collectors.joining(",")));
        }
        if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new InvalidUserException(USER_WITH_THAT_EMAIL_ALREADY_EXISTS);
        }
        userRepository.saveAndFlush(modelMapper.map(userDto, User.class));
    }

    @Override
    public void updateUser(UserDto userDto) {
        User user = userRepository.findByEmail(userDto.getEmail()).orElseThrow(() -> new InvalidUserException(USER_WITH_THAT_EMAIL_DOESNT_EXISTS));
        long id = user.getId();
        BeanUtils.copyProperties(modelMapper.map(userDto, User.class), user);
        user.setId(id);
        userRepository.saveAndFlush(user);
    }
}
