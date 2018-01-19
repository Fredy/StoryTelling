package com.storytelling.rest;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.storytelling.StorytellingApplication;
import com.storytelling.helpers.RequestUser;
import com.storytelling.model.User;
import com.storytelling.repository.StoryFragRepository;
import com.storytelling.repository.StoryPropRepository;
import com.storytelling.repository.UserRepository;
import com.storytelling.service.UserService;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = StorytellingApplication.class)
@WebAppConfiguration
public class UserRestTest {

  private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
      MediaType.APPLICATION_JSON.getSubtype(),
      Charset.forName("utf8"));

  private MockMvc mockMvc;

  private ObjectMapper objectMapper;

  private User user;

  @Autowired
  private WebApplicationContext webApplicationContext;

  @Autowired
  private UserService userService;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private StoryFragRepository fragRepository;

  @Autowired
  private StoryPropRepository propRepository;

  @Before
  public void setup() throws Exception {
    this.fragRepository.deleteAll();
    this.propRepository.deleteAll();
    this.userRepository.deleteAll();

    this.objectMapper = new ObjectMapper();

    User tmpUser = new User();
    tmpUser.setUsername("username");
    tmpUser.setFirstName("first");
    tmpUser.setLastName("last");
    tmpUser.setEmail("email");
    tmpUser.setSignDate(new Date());

    this.user = this.userRepository.save(tmpUser);

    this.mockMvc = webAppContextSetup(this.webApplicationContext).build();
  }

  @Test
  public void newUserOk() throws Exception {
    RequestUser requestUser = new RequestUser("otherUsername",
        "first", "last", "email");

    String userJson = objectMapper.writeValueAsString(requestUser);

    this.mockMvc.perform(post("/api/user/new")
        .contentType(contentType)
        .content(userJson))
        .andExpect(status().isOk())
        .andExpect(content().contentType(contentType))
        .andExpect(jsonPath("$.username", is(requestUser.getUsername())))
        .andExpect(jsonPath("$.firstName", is(requestUser.getFirstName())))
        .andExpect(jsonPath("$.lastName", is(requestUser.getLastName())))
        .andExpect(jsonPath("$.email", is(requestUser.getEmail())));
  }

  @Test
  public void newUserConflict() throws Exception {
    String userJson = objectMapper
        .writeValueAsString(
            new RequestUser("username", "first", "last", "email"));

    this.mockMvc.perform(post("/api/user/new")
        .contentType(this.contentType)
        .content(userJson))
        .andExpect(status().isConflict());
  }

  @Test
  public void userIdOk() throws Exception {
    this.mockMvc.perform(get("/api/user/" + this.user.getId())
        .contentType(this.contentType))
        .andExpect(status().isOk())
        .andExpect(content().contentType(contentType))
        .andExpect(jsonPath("$.id", is(this.user.getId().intValue())))
        .andExpect(jsonPath("$.username", is(this.user.getUsername())));
  }

  @Test
  public void userIdNotFound() throws Exception {
    this.mockMvc.perform(get("/api/user/" + 9000)
        .contentType(this.contentType))
        .andExpect(status().isNotFound());
  }

  @Test
  public void userUsernameOk() throws Exception {
    this.mockMvc.perform(get("/api/user/")
        .contentType(this.contentType)
        .param("username", this.user.getUsername()))
        .andExpect(status().isOk())
        .andExpect(content().contentType(contentType))
        .andExpect(jsonPath("$.id", is(this.user.getId().intValue())));
  }

  @Test
  public void userUsernameNotFound() throws Exception {
    this.mockMvc.perform(get("/api/user/")
        .contentType(this.contentType)
        .param("username", "nothing"))
        .andExpect(status().isNotFound());
  }

  @Test
  public void storyPropsOk() throws Exception {
    this.mockMvc.perform(get("/api/user/" + this.user.getId() + "/props")
        .contentType(this.contentType))
        .andExpect(status().isOk())
        .andExpect(content().contentType(contentType))
        .andExpect(jsonPath("$", is(new ArrayList<>())));
  }

  @Test
  public void storyPropsNotFound() throws Exception {
    this.mockMvc.perform(get("/api/user/" + 1000 + "/props")
        .contentType(this.contentType))
        .andExpect(status().isNotFound());
  }

  @Test
  public void updateOk() throws Exception {
    RequestUser requestUser = new RequestUser("otherUsername",
        "first", "last", "email");

    String userJson = objectMapper.writeValueAsString(requestUser);

    this.mockMvc.perform(post("/api/user/" + this.user.getId() + "/update")
        .contentType(contentType)
        .content(userJson))
        .andExpect(status().isOk())
        .andExpect(content().contentType(contentType))
        .andExpect(jsonPath("$.username", is(requestUser.getUsername())))
        .andExpect(jsonPath("$.firstName", is(requestUser.getFirstName())))
        .andExpect(jsonPath("$.lastName", is(requestUser.getLastName())))
        .andExpect(jsonPath("$.email", is(requestUser.getEmail())));
  }

  @Test
  public void updateConflict() throws Exception {
    RequestUser requestUser = new RequestUser("other",
        "first", "last", "email");
    String userJson = objectMapper.writeValueAsString(requestUser);

    User tmpUser = new User();
    tmpUser.setUsername("other");
    this.userService.save(tmpUser);

    this.mockMvc.perform(post("/api/user/" + this.user.getId() + "/update")
        .contentType(contentType)
        .content(userJson))
        .andExpect(status().isConflict());
  }

  @Test
  public void updateNotFound() throws Exception {
    RequestUser requestUser = new RequestUser("other",
        "first", "last", "email");
    String userJson = objectMapper.writeValueAsString(requestUser);

    this.mockMvc.perform(post("/api/user/" + 1000 + "/update")
        .contentType(contentType)
        .content(userJson))
        .andExpect(status().isNotFound());
  }

  @Test
  public void all() throws Exception {
    this.mockMvc.perform(get("/api/user/all")
        .contentType(this.contentType))
        .andExpect(status().isOk())
        .andExpect(content().contentType(contentType))
        .andExpect(jsonPath("$[0].id", is(this.user.getId().intValue())))
        .andExpect(jsonPath("$[0].username", is(this.user.getUsername())))
        .andExpect(jsonPath("$[0].firstName", is(this.user.getFirstName())))
        .andExpect(jsonPath("$[0].lastName", is(this.user.getLastName())))
        .andExpect(jsonPath("$[0].email", is(this.user.getEmail())));
  }

}