package com.storytelling.rest;

import com.storytelling.helpers.RequestStoryFrag;
import com.storytelling.model.StoryFragment;
import com.storytelling.model.StoryProposition;
import com.storytelling.model.User;
import com.storytelling.service.StoryFragService;
import com.storytelling.service.StoryPropService;
import com.storytelling.service.UserService;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/frag")
public class StoryFragRest {

  private final StoryFragService fragService;

  private final StoryPropService propService;

  private final UserService userService;

  @Autowired
  public StoryFragRest(StoryFragService fragService, StoryPropService propService,
      UserService userService) {
    this.fragService = fragService;
    this.propService = propService;
    this.userService = userService;
  }

  /**
   * Create a new Story fragment on the database.
   *
   * @param requestFrag An object that has text, propId and userId.
   * @return Status 200 (OK) if the fragment was correctly saved on the database, 400 (BAD REQUEST)
   * if there is no propId, userId or text or the text is empty, and 404 if a user with the passed
   * userId doesn't exists or if a story proposition with the passed propId doesn't exists.
   */
  @RequestMapping(value = "/new", method = RequestMethod.POST)
  public ResponseEntity newStory(@RequestBody RequestStoryFrag requestFrag) {
    if (requestFrag.getPropId() == null || requestFrag.getUserId() == null
        || requestFrag.getText() == null || requestFrag.getText().isEmpty()) {
      return ResponseEntity.badRequest().build();
    }

    StoryProposition proposition = this.propService.findById(requestFrag.getPropId());
    User user = this.userService.findById(requestFrag.getUserId());

    if (user == null || proposition == null) {
      return ResponseEntity.notFound().build();
    }

    StoryFragment fragment = new StoryFragment();
    fragment.setFragText(requestFrag.getText());
    fragment.setStoryProposition(proposition);
    fragment.setUser(user);
    fragment.setPubDate(new Date());

    this.fragService.save(fragment);

    return ResponseEntity.ok(fragment);
  }

  /**
   * Find a story fragment by its id.
   *
   * @param id Story's id.
   * @return Found fragment and 200 (OK) if the story fragment was found, http status 404 (NOT
   * FOUND) if a story fragment with the passed id doesn't exists.
   */
  @RequestMapping(value = "/{id}", method = RequestMethod.GET)
  public ResponseEntity storyId(@PathVariable("id") Long id) {
    StoryFragment fragment = this.fragService.findById(id);

    if (fragment == null) {
      return ResponseEntity.notFound().build();
    } else {
      return ResponseEntity.ok(fragment);
    }
  }

  /**
   * Update an existing story fragment.
   *
   * @param id Story fragment's id.
   * @param requestFrag An object that has text, propId, and userId.
   * @return Status 200 (OK) and the story fragment that where updated, or status 404 (NOT FOUND)
   * if the story fragment with the passed id doesn't exist.
   */
  @RequestMapping(value = "/{id}/update", method = RequestMethod.POST)
  public ResponseEntity update(@PathVariable("id") Long id,
      @RequestBody RequestStoryFrag requestFrag) {
    StoryFragment fragment = this.fragService.findById(id);
    if (fragment == null) {
      return ResponseEntity.notFound().build();
    } else {
      fragment.setFragText(requestFrag.getText());
      this.fragService.save(fragment);

      return ResponseEntity.ok(fragment);
    }
  }

  // TODO: votes request

}
