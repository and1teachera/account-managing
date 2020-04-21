package org.zlatenov.accountmanaging.web;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.zlatenov.accountmanaging.model.dto.UserDto;
import org.zlatenov.accountmanaging.model.entity.User;
import org.zlatenov.accountmanaging.service.UserService;

import java.net.URI;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * @author Angel Zlatenov
 */

@RestController
@RequestMapping("/api/users")
@CrossOrigin("http://localhost:4200")
@Slf4j
@AllArgsConstructor
public class UserController {

    private UserService userService;
    private ModelMapper modelMapper;

    @GetMapping
    public Collection<UserDto> getUsers() {
        return userService.getUsers().stream().map(user -> modelMapper.map(user, UserDto.class)).collect(Collectors.toList());
    }

    @GetMapping("/{email}")
    public UserDto getUser(@PathVariable String email) {
        return modelMapper.map(userService.getUserByEmail(email), UserDto.class);
    }

    @DeleteMapping("/{email}")
    public void deleteUser(@PathVariable String email) {
        userService.deleteUser(userService.getUserByEmail(email));
    }

    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto user) {
        User created = userService.createUser(user);
        URI location = MvcUriComponentsBuilder.fromMethodName(UserController.class, "createUser", User.class)
                .pathSegment("{id}").buildAndExpand(created.getId()).toUri();
        log.info("User created: {}", location);
        return ResponseEntity.created(location).body(modelMapper.map(created, UserDto.class));
    }

    @PutMapping
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto user) {
        User updated = userService.updateUser(user);
        log.info("User updated: {}", updated);
        return ResponseEntity.ok(modelMapper.map(updated, UserDto.class));
    }
}
