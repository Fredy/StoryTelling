package com.storytelling.controller;

import com.storytelling.helpers.RequestUser;
import com.storytelling.model.User;
import com.storytelling.service.UserService;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UserController {

  @Autowired
  UserService userService;

  @RequestMapping(value = "/users")
  public String index(Model model) {

    model.addAttribute("users", this.userService.findAll());
    model.addAttribute("newUser", new RequestUser());
    return "user/userlist";
  }

  @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
  public String userId(@PathVariable("id") Long id, Model model) {
    User user = this.userService.findById(id);
    if (user == null) {
      model.addAttribute("messageWarn", String.format("User with id %d doesn't exists.", id));
    } else {
      model.addAttribute("user", user);
    }
    return "user/user";
  }

  @RequestMapping(value = "/users", method = RequestMethod.POST)
  public String newUser(@ModelAttribute("newUser") RequestUser requestUser,
      RedirectAttributes redirectAtt) {

    if (this.userService.usernameExist(requestUser.getUsername())) {
      redirectAtt.addFlashAttribute("messageWarn",
          String.format("An user with the username &s already esxists.",
              requestUser.getUsername()));
      return "redirect:/users";
    }
    User user = new User();
    user.setEmail(requestUser.getEmail());
    user.setFirstName(requestUser.getFirstName());
    user.setLastName(requestUser.getLastName());
    user.setUsername(requestUser.getUsername());
    user.setSignDate(new Date());

    this.userService.save(user);
    redirectAtt.addFlashAttribute("messageOk", "User saved");
    return "redirect:/users";
  }


}
