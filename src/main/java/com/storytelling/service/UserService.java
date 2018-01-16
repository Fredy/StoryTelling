package com.storytelling.service;

import com.storytelling.model.StoryProposition;
import com.storytelling.model.User;
import com.storytelling.repository.UserRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  @Autowired
  UserRepository repository;

  public List<User> findAll() {
    return this.repository.findAll();
  }

  public User findById(Long id) {
    return this.repository.findOne(id);
  }

  public User findByUsername(String username) {
    return this.repository.findByUsername(username);
  }

  public User save(User user) {
    return this.repository.save(user);
  }

  public boolean usernameExist(String username) {
    User user = this.repository.findByUsername(username);
    if (user == null) {
      return false;
    } else {
      return true;
    }
  }

  public List<StoryProposition> storyPropsByUserId(Long id) {
    return this.repository.storyPropsByUserId(id);
  }

}
