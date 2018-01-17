package com.storytelling.rest;

import com.storytelling.helpers.RequestStoryProp;
import com.storytelling.model.StoryProposition;
import com.storytelling.service.StoryPropService;
import com.storytelling.service.UserService;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/story")
public class StoryPropRest {

  private final StoryPropService storyPropService;

  private final UserService userService;

  @Autowired
  public StoryPropRest(StoryPropService storyPropService, UserService userService) {
    this.storyPropService = storyPropService;
    this.userService = userService;
  }

  /**
   * Create a new Story proposition on the database.
   *
   * @param requestStory An object that has text and userId.
   * @return Status 200 (OK) if the story was correctly saved on the database, 400 (BAD REQUEST) if
   * there is no userId or text or the text is empty, and 404 if a user with the passed userId
   * doesn't exists.
   */
  @RequestMapping(value = "/new", method = RequestMethod.POST)
  public ResponseEntity newStory(@RequestBody RequestStoryProp requestStory) {
    if (requestStory.getUserId() == null || requestStory.getText() == null || requestStory.getText()
        .isEmpty()) {
      return ResponseEntity.badRequest().build();
    }
    if (this.userService.findById(requestStory.getUserId()) == null) {
      return ResponseEntity.notFound().build();
    }

    StoryProposition story = new StoryProposition();
    story.setPropText(requestStory.getText());
    story.setUser(this.userService.findById(requestStory.getUserId()));
    story.setPubDate(new Date());

    this.storyPropService.save(story);

    return ResponseEntity.ok(story);
  }

  /**
   * Find a story proposition by its id.
   *
   * @param id Story's id.
   * @return Found story and http status, 404 (NOT FOUND) if a story proposition with the passed id
   * doesn't exists, 200 (OK) if the story proposition was found.
   */
  @RequestMapping(value = "/{id}", method = RequestMethod.GET)
  public ResponseEntity storyId(@PathVariable("id") Long id) {
    StoryProposition story = this.storyPropService.findById(id);

    if (story == null) {
      return ResponseEntity.notFound().build();
    } else {
      return ResponseEntity.ok(story);
    }
  }

  /**
   * Find a random story proposition.
   *
   * @return A random story proposition.
   */
  @RequestMapping(value = "random", method = RequestMethod.GET)
  public ResponseEntity storyRandom() {
    return ResponseEntity.ok(this.storyPropService.findRandom());
  }

  /**
   * Find all the story propositions from an user.
   *
   * @param username User's username.
   * @return Status 200 (OK) and list of story propositions if an user with the passed username
   * exists or Status 404 (NOT FOUND) if the user was not found.
   */
  @RequestMapping(value = "/", method = RequestMethod.GET)
  public ResponseEntity storyUsername(@RequestParam(value = "user") String username) {
    if (this.userService.findByUsername(username) == null) {
      return ResponseEntity.notFound().build();
    } else {
      return ResponseEntity.ok(this.storyPropService.findByUser_Username(username));
    }
  }

  /**
   * Find all the story propositions from an user.
   *
   * @param userId User's id.
   * @return Status 200 (OK) and list of story propositions if an user with the passed id exists or
   * Status 404 (NOT FOUND) if the user was not found.
   */
  @RequestMapping(value = "/", method = RequestMethod.GET)
  public ResponseEntity storyUserId(@RequestParam(value = "user_id") Long userId) {
    if (this.userService.findById(userId) == null) {
      return ResponseEntity.notFound().build();
    } else {
      return ResponseEntity.ok(this.storyPropService.findByUser_Id(userId));
    }
  }

  /**
   * Find all the story propositions that where created between {@code start} and {@code end}.
   *
   * @param start Start date, represented as "yyyy-MM-dd".
   * @param end End date, represented as "yyyy-MM-dd".
   * @return Status 200 (OK) and a list of story propositions, or 400 (BAD REQUEST) if the dates are
   * wrongly formatted.
   */
  @RequestMapping(value = "/between", method = RequestMethod.GET)
  public ResponseEntity storyBetweenDates(@RequestParam(value = "start") String start,
      @RequestParam(value = "end") String end) {
    List<StoryProposition> stories;
    try {
      stories = this.storyPropService.findByPubDateBetween(start, end);
    } catch (ParseException e) {
      // TODO: logger
      e.printStackTrace();
      return ResponseEntity.badRequest().build();
    }

    return ResponseEntity.ok(stories);
  }

  /**
   * Update an existing story proposition.
   *
   * @param id Story proposition's id.
   * @param requestStory An object that has text and userId.
   * @return Status 200 (OK) and the story proposition that where updated, or status 404 (NOT FOUND)
   * if the story proposition with the passed id doesn't exist.
   */
  @RequestMapping(value = "/{id}/update", method = RequestMethod.POST)
  public ResponseEntity update(@PathVariable("id") Long id,
      @RequestBody RequestStoryProp requestStory) {
    StoryProposition story = this.storyPropService.findById(id);
    if (story == null) {
      return ResponseEntity.notFound().build();
    } else {
      story.setPropText(requestStory.getText());
      return ResponseEntity.ok(story);
    }
  }

  /**
   * Find a story proposition by it's title (text).
   *
   * @param text The story proposition text.
   * @return Status 200 (OK) and list of story propositions that match with the query, or status 400
   * (BAD REQUEST) if the text is empty.
   */
  @RequestMapping(value = "/find", method = RequestMethod.GET)
  public ResponseEntity find(@RequestParam(value = "text") String text) {
    if (text.isEmpty()) {
      return ResponseEntity.badRequest().build();
    }
    System.out.println(text);
    return ResponseEntity.ok(this.storyPropService.findByPropTextContaining(text));
  }

  // TODO: vote requests
}
