package com.storytelling.service;

import com.storytelling.model.StoryFragment;
import com.storytelling.model.StoryProposition;
import com.storytelling.repository.StoryPropRepository;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StoryPropService {

  private final StoryPropRepository repository;

  @Autowired
  public StoryPropService(StoryPropRepository repository) {
    this.repository = repository;
  }

  private void sortFragmentList(StoryProposition story) {
    if (story != null) {
      story.getFragmentList().sort(Comparator.comparing(StoryFragment::getPubDate));
    }
  }

  public List<StoryProposition> findAll() {
    return this.repository.findAll();
  }

  public StoryProposition findById(Long id) {
    StoryProposition story = this.repository.findOne(id);
    this.sortFragmentList(story);
    return this.repository.findOne(id);
  }

  public StoryProposition save(StoryProposition storyProposition) {
    return this.repository.save(storyProposition);
  }

  public StoryProposition findRandom() {
    List<Long> ids = this.repository.findAllIds();
    int random = ThreadLocalRandom.current().nextInt(0, ids.size());
    return this.findById(ids.get(random));
  }

  public List<StoryProposition> findByPropTextContaining(String text) {
    return this.repository.findByPropTextContaining(text);
  }

  // This is the same as UserService.storyPropsByUserId(id)
  public List<StoryProposition> findByUser_Id(Long id) {
    return this.repository.findByUser_Id(id);
  }

  public List<StoryProposition> findByUser_Username(String username) {
    return this.repository.findByUser_Username(username);
  }

  /**
   * Receives two strings, formatted as "yyyy-MM-dd", that are parsed to dates. Return all the
   * stories propositions that where created between {@code start}  and {@code end}.
   *
   * @param start Start date, represented as "yyyy-MM-dd".
   * @param end End date, represented as "yyyy-MM-dd".
   * @return List of stories propositions.
   * @throws ParseException if the parameters are wrong formatted.
   */
  public List<StoryProposition> findByPubDateBetween(String start, String end)
      throws ParseException {

    SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
    Date dateStart = formater.parse(start);
    Date dateEnd = formater.parse(end);
    return this.repository.findByPubDateBetween(dateStart, dateEnd);
  }

  public List<StoryFragment> storyFragsByPropId(Long id) {
    return this.repository.storyFragsByPropId(id);
  }

}
