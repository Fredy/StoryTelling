package com.storytelling.rest;

import com.storytelling.model.StoryProposition;
import com.storytelling.service.StoryPropService;
import com.storytelling.service.UserService;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/story")
public class StoryPropRest {

  @Autowired
  private StoryPropService storyPropService;

  @Autowired
  private UserService userService;

  @RequestMapping(value = "/new", method = RequestMethod.POST)
  public ResponseEntity newStory(@RequestParam(value = "text") String text,
      @RequestParam(value = "userId") Long userId) {
    if (this.userService.findById(userId) == null) {
      return ResponseEntity.notFound().build();
    }

    StoryProposition story = new StoryProposition();
    story.setPropText(text);
    story.setUser(this.userService.findById(userId));
    story.setPubDate(new Date());

    this.storyPropService.save(story);

    return ResponseEntity.ok(story);
  }

  @RequestMapping(value = "/{id}", method = RequestMethod.GET)
  public ResponseEntity storyId(@PathVariable("id") Long id) {
    StoryProposition story = this.storyPropService.findById(id);

    if (story == null) {
      return ResponseEntity.notFound().build();
    } else {
      return ResponseEntity.ok(story);
    }
  }

  @RequestMapping(value = "random", method = RequestMethod.GET)
  public ResponseEntity storyRandom() {
    return ResponseEntity.ok(this.storyPropService.findRandom());
  }

  @RequestMapping(value = "/", method = RequestMethod.GET)
  public ResponseEntity storyUsername(@RequestParam(value = "user") String username) {
    if (this.userService.findByUsername(username) == null) {
      return ResponseEntity.notFound().build();
    } else {
      return ResponseEntity.ok(this.storyPropService.findByUser_Username(username));
    }
  }

  @RequestMapping(value = "/between", method = RequestMethod.GET)
  public ResponseEntity storyBetweenDates(@RequestParam(value = "start") String start,
      @RequestParam(value = "end") String end) {
    List<StoryProposition> stories;
    try {
      stories = this.storyPropService.findByPubDateBetween(start, end);
    } catch (ParseException e) {
      // TODO: logger
      e.printStackTrace();
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    return ResponseEntity.ok(stories);
  }

  @RequestMapping(value = "/{id}/update", method = RequestMethod.POST)
  public ResponseEntity update(@PathVariable("id") Long id,
      @RequestParam(value = "text") String text) {
    StoryProposition story = this.storyPropService.findById(id);
    if (story == null) {
      return ResponseEntity.notFound().build();
    } else {
      story.setPropText(text);
      return ResponseEntity.ok(story);
    }
  }

  // TODO: javadoc
  // TODO: vote requests
}
