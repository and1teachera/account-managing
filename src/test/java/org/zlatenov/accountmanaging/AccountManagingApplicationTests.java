package org.zlatenov.accountmanaging;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.zlatenov.accountmanaging.model.dto.UserDto;
import org.zlatenov.accountmanaging.model.entity.User;
import org.zlatenov.accountmanaging.repository.UserRepository;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class AccountManagingApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    private static List<User> users = new ArrayList<>();

    @BeforeAll
    public static void populateDB() {
        User user1 = new User();
        user1.setFirstName("Test");
        user1.setLastName("User");
        user1.setEmail("test@test.bg");
        user1.setBirthDate(LocalDate.of(2000, 10, 10));

        User user2 = new User();
        user2.setFirstName("Test2");
        user2.setLastName("User2");
        user2.setEmail("test2@test.bg");
        user2.setBirthDate(LocalDate.of(2010, 11, 1));

        users.add(user1);
        users.add(user2);
    }

    @Transactional
    @Test
    void testCreateUser() throws Exception {
        UserDto user = new UserDto("Test3", "User3", "test3@test.bg", "1999-12-12");

        mockMvc.perform(
                post("/api/users").contentType("application/json").content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isCreated());

        User userEntity = userRepository.findByEmail("test3@test.bg");
        assertThat(userEntity.getFirstName()).isEqualTo("Test3");
    }

    @Transactional
    @Test
    void testGetAllUsers() throws Exception {
        User user1 = users.get(0);
        User user2 = users.get(1);
        userRepository.save(user1);
        userRepository.save(user2);

        UserDto userDto1 = new UserDto(user1.getFirstName(), user1.getLastName(), user1.getEmail(),
                                       user1.getBirthDate().toString());

        UserDto userDto2 = new UserDto(user2.getFirstName(), user2.getLastName(), user2.getEmail(),
                                       user2.getBirthDate().toString());

        MvcResult mvcResult = mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();
        String expectedResponseBody = objectMapper.writeValueAsString(Arrays.asList(userDto1, userDto2));
        assertThat(expectedResponseBody).isEqualToIgnoringWhitespace(actualResponseBody);
    }

    @Test
    void testDeleteUser() throws Exception {
        User user1 = users.get(0);
        userRepository.save(user1);

        mockMvc.perform(delete("/api/users/" + user1.getEmail())).andExpect(status().isOk());
        assertThat(userRepository.existsByEmail(user1.getEmail())).isFalse();
    }

    @Transactional
    @Test
    void testGetUser() throws Exception {
        User user1 = users.get(0);
        userRepository.save(user1);

        MvcResult mvcResult = mockMvc.perform(get("/api/users/" + user1.getEmail()))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andReturn();

        UserDto userDto1 = new UserDto(user1.getFirstName(), user1.getLastName(), user1.getEmail(),
                                       user1.getBirthDate().toString());

        String actualResponseBody = mvcResult.getResponse().getContentAsString();
        String expectedResponseBody = objectMapper.writeValueAsString(userDto1);
        assertThat(expectedResponseBody).isEqualToIgnoringWhitespace(actualResponseBody);
    }

    @Test
    void testEditUser() throws Exception {
        User user1 = users.get(0);
        userRepository.save(user1);

        UserDto userDto1 = new UserDto(user1.getFirstName(), user1.getLastName(), user1.getEmail(),
                                       user1.getBirthDate().toString());
        userDto1.setFirstName("Test1");
        userDto1.setLastName("LastName");
        userDto1.setBirthDate("1999-10-10");

        mockMvc.perform(put("/api/users/").contentType("application/json")
                                .content(objectMapper.writeValueAsString(userDto1))).andExpect(status().isOk());
        User savedUser = userRepository.findByEmail(user1.getEmail());

        assertThat(userDto1.getFirstName()).isEqualTo(savedUser.getFirstName());
        assertThat(userDto1.getLastName()).isEqualTo(savedUser.getLastName());
        assertThat(userDto1.getBirthDate()).isEqualTo(savedUser.getBirthDate().toString());
    }

}
