package com.storytelling.rest;

import com.storytelling.model.User;
import com.storytelling.service.UserService;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserRest {

  @Autowired
  UserService service;

  /**
   * Create a new user in the database.
   *
   * @param email User's email.
   * @param username User's username.
   * @param firstName User's first name.
   * @param lastName User's last name.
   * @return Status 200 (OK) if the new user is correctly saved on the database, or 409 (CONFLICT)
   * if there is an existing user with the same username in the database.
   */
  @RequestMapping(value = "/new", method = RequestMethod.POST)
  public ResponseEntity newUser(@RequestParam(value = "email") String email,
      @RequestParam(value = "username") String username,
      @RequestParam(value = "firstName") String firstName,
      @RequestParam(value = "lastName") String lastName) {

    if (this.service.usernameExist(username)) {
      return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }
    User user = new User();
    user.setEmail(email);
    user.setFirstName(firstName);
    user.setLastName(lastName);
    user.setUsername(username);
    user.setSignDate(new Date());

    this.service.save(user);
    return ResponseEntity.ok(null);
  }

  /**
   * Find an user by its id.
   *
   * @param id User's id.
   * @return Found user and http status, 404 (NOT FOUND) if a user with the passed id doesn't
   * exists, 202 (OK) if the user was found.
   */
  @RequestMapping(value = "/{id}", method = RequestMethod.GET)
  public ResponseEntity userId(@PathVariable("id") Long id) {
    User user = this.service.findById(id);
    if (user == null) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    } else {
      return ResponseEntity.ok(user);
    }
  }

  /**
   * Find an user by its username.
   *
   * @param username User's username.
   * @return Found user and http status, 404 (NOT FOUND) if a user with the passed username doesn't
   * exists, 202 (OK) if the user was found.
   */
  @RequestMapping(value = "/", method = RequestMethod.GET)
  public ResponseEntity userUsername(@RequestParam(value = "username") String username) {
    User user = this.service.findByUsername(username);
    if (user == null) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    } else {
      return ResponseEntity.ok(user);
    }
  }

  /**
   * Find an user's story propositions.
   *
   * @param id User's id.
   * @return List of story propositions or an empty list if the user doesn't exists, and http
   * status, 404 (NOT FOUND) if a user with the passed id doesn't exists, 202 (OK) if the user was
   * found.
   */
  @RequestMapping(value = "/{id}/props", method = RequestMethod.GET)
  public ResponseEntity storyProps(@PathVariable("id") Long id) {
    if (this.service.findById(id) == null) {
      return ResponseEntity.notFound().build();
    } else {
      return ResponseEntity.ok(this.service.storyPropsByUserId(id));
    }
  }

  /**
   * Update an existing user.
   *
   * @param id User's id.
   * @param email User's email.
   * @param username User's username.
   * @param firstName User's first name.
   * @param lastName User's last name.
   * @return The updated user and http status, 404 (NOT FOUND) means that a user with the passed id
   * doesn't exist, 409 (CONFLICT) means that there is an existing user with the same username in
   * the database, 200 (OK) if the changes where saved in the database.
   */
  @RequestMapping(value = "/{id}/update", method = RequestMethod.POST)
  public ResponseEntity update(@PathVariable("id") Long id,
      @RequestParam(value = "email", required = false, defaultValue = "") String email,
      @RequestParam(value = "username", required = false, defaultValue = "") String username,
      @RequestParam(value = "firstName", required = false, defaultValue = "") String firstName,
      @RequestParam(value = "lastName", required = false, defaultValue = "") String lastName) {
    User user = this.service.findById(id);
    if (user == null) {
      return ResponseEntity.notFound().build();
    }

    if (!email.isEmpty()) {
      user.setEmail(email);
    }
    if (!username.isEmpty()) {
      if (this.service.usernameExist(username)) {
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
      }
      user.setUsername(username);
    }
    if (!firstName.isEmpty()) {
      user.setFirstName(firstName);
    }
    if (!lastName.isEmpty()) {
      user.setLastName(lastName);
    }

    this.service.save(user);
    return ResponseEntity.ok(user);
  }


}
