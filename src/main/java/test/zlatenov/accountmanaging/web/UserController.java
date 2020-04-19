package test.zlatenov.accountmanaging.web;

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
import test.zlatenov.accountmanaging.model.binding.DeleteUserBindingModel;
import test.zlatenov.accountmanaging.model.binding.UserBindingModel;
import test.zlatenov.accountmanaging.model.entity.User;
import test.zlatenov.accountmanaging.model.vo.UserVO;
import test.zlatenov.accountmanaging.service.UserService;

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
    public Collection<UserVO> getUsers() {
        return userService.getUsers().stream().map(user -> new UserVO()).collect(Collectors.toList());
    }

    @GetMapping("{email}")
    public UserVO getUser(@PathVariable String email) {
        return modelMapper.map(userService.getUserByEmail(email), UserVO.class);
    }

    @DeleteMapping("{email}")
    public void deleteUser(@PathVariable DeleteUserBindingModel deleteUserBindingModel) {
        userService.deleteUser(deleteUserBindingModel);
    }

    @PostMapping
    public ResponseEntity<UserVO> createUser(@RequestBody UserBindingModel user) {
        User created = userService.createUser(user);
        URI location = MvcUriComponentsBuilder.fromMethodName(UserController.class, "createUser", User.class)
                .pathSegment("{id}").buildAndExpand(created.getId()).toUri() ;
        log.info("User created: {}", location);
        return ResponseEntity.created(location).body(modelMapper.map(created, UserVO.class));
    }

    @PutMapping("{id}")
    public ResponseEntity<UserVO> updateUser(@RequestBody UserBindingModel user) {
        User updated = userService.updateUser(user);
        log.info("User updated: {}", updated);
        return ResponseEntity.ok(modelMapper.map(updated, UserVO.class));
    }
}
