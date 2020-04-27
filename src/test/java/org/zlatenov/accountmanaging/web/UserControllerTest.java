package org.zlatenov.accountmanaging.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.zlatenov.accountmanaging.model.dto.UserDto;
import org.zlatenov.accountmanaging.service.UserService;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Angel Zlatenov
 */
@WebMvcTest(controllers = UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testDeleteUser() throws Exception {
        String email = "test@test.com";
        when(userService.getUserByEmail(email)).thenReturn(new UserDto());

        mockMvc.perform(delete("/api/users/" + email)).andExpect(status().isOk());

        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(userService, times(1)).deleteUserByEmail(argumentCaptor.capture());

        Assert.assertEquals(email, argumentCaptor.getValue());
    }

    @Test
    void testEditUser() throws Exception {
        String email = "test@test.com";
        UserDto userDto = new UserDto("Test", "Test", email, "");
        when(userService.getUserByEmail(email)).thenReturn(userDto);

        mockMvc.perform(put("/api/users/").contentType("application/json")
                                .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isOk());

        ArgumentCaptor<UserDto> argumentCaptor = ArgumentCaptor.forClass(UserDto.class);
        verify(userService, times(1)).updateUser(argumentCaptor.capture());

        Assert.assertEquals(userDto, argumentCaptor.getValue());
    }

    @Test
    void testCreateUser() throws Exception {
        UserDto userDto = new UserDto("Test", "Test", "test@test.com", "");

        mockMvc.perform(post("/api/users/").contentType("application/json")
                                .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isCreated());

        ArgumentCaptor<UserDto> argumentCaptor = ArgumentCaptor.forClass(UserDto.class);
        verify(userService, times(1)).createUser(argumentCaptor.capture());

        Assert.assertEquals(userDto, argumentCaptor.getValue());
    }
}
