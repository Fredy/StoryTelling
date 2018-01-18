package com.storytelling.controller;

import com.storytelling.helpers.RequestStoryFrag;
import com.storytelling.helpers.RequestStoryProp;
import com.storytelling.model.StoryFragment;
import com.storytelling.model.StoryProposition;
import com.storytelling.model.User;
import com.storytelling.service.StoryFragService;
import com.storytelling.service.StoryPropService;
import com.storytelling.service.UserService;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class StoryController {

  private final StoryPropService storyPropService;
  private final UserService userService;
  private final StoryFragService storyFragService;

  @Autowired
  public StoryController(StoryPropService storyPropService, UserService userService,
      StoryFragService storyFragService) {
    this.storyPropService = storyPropService;
    this.userService = userService;
    this.storyFragService = storyFragService;
  }


  @RequestMapping(value = "/", method = RequestMethod.GET)
  public String home(Model model) {
    model.addAttribute("newProp", new RequestStoryProp());
    model.addAttribute("stories", this.storyPropService.findAll());
    return "story/home";
  }

  @RequestMapping(value = "/", params = "text", method = RequestMethod.GET)
  public String search(Model model, RedirectAttributes redirectAtt,
      @RequestParam(value = "text") String text) {
    if (text.isEmpty()) {
      redirectAtt.addFlashAttribute("messageWarn", "The search query is empty!");
      return "redirect:/";
    } else {
      model.addAttribute("newProp", new RequestStoryProp());
      model.addAttribute("stories", this.storyPropService.findByPropTextContaining(text));
      return "story/home";
    }
  }

  @RequestMapping(value = "/", method = RequestMethod.POST)
  public String newStoryProp(@ModelAttribute("newProp") RequestStoryProp requestStory,
      RedirectAttributes redirectAtt) {
    if (requestStory.getUserId() == null || requestStory.getText() == null
        || requestStory.getText().isEmpty()) {
      redirectAtt.addFlashAttribute("messageNew", "Fill all the fields");
      return "redirect:/";
    }

    User user = this.userService.findById(requestStory.getUserId());
    if (user == null) {
      redirectAtt.addFlashAttribute("messageNew", "User was not found");
      return "redirect:/";
    }

    StoryProposition story = new StoryProposition();
    story.setPropText(requestStory.getText());
    story.setUser(user);
    story.setPubDate(new Date());

    this.storyPropService.save(story);

    redirectAtt.addFlashAttribute("messageOk", "Story saved.");
    return "redirect:/";
  }


  @RequestMapping(value = "/story/{id}", method = RequestMethod.GET)
  public String story(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAtt) {
    StoryProposition story = this.storyPropService.findById(id);

    if (story == null) {
      redirectAtt
          .addFlashAttribute("messageWarn", String.format("Story with id %d doesn't exists.", id));
      return "redirect:/";
    } else {
      model.addAttribute("story", story);
      model.addAttribute("newFrag", new RequestStoryFrag());
      return "story/story";
    }
  }

  @RequestMapping(value = "/story/{id}", method = RequestMethod.POST)
  public String newStoryFrag(@ModelAttribute("newFrag") RequestStoryFrag requestFrag,
      @PathVariable("id") Long id, RedirectAttributes redirectAtt) {

    if (requestFrag.getUserId() == null || requestFrag.getText() == null
        || requestFrag.getText().isEmpty()) {
      redirectAtt.addFlashAttribute("messageWarn", "Fill all the fields.");
      return String.format("redirect:/story/%d", id);
    }

    User user = this.userService.findById(requestFrag.getUserId());

    StoryProposition proposition = this.storyPropService.findById(id);
    if (user == null || proposition == null) {
      redirectAtt.addFlashAttribute("messageWarn", "User was not found.");
      return String.format("redirect:/story/%d", id);
    }

    StoryFragment fragment = new StoryFragment();
    fragment.setFragText(requestFrag.getText());
    fragment.setStoryProposition(proposition);
    fragment.setUser(user);
    fragment.setPubDate(new Date());

    this.storyFragService.save(fragment);

    redirectAtt.addFlashAttribute("messageOk", "Story fragment was saved.");
    return String.format("redirect:/story/%d", id);


  }


}
