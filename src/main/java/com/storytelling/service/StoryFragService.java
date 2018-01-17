package com.storytelling.service;

import com.storytelling.model.StoryFragment;
import com.storytelling.repository.StoryFragRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StoryFragService {

  private final StoryFragRepository repository;

  @Autowired
  public StoryFragService(StoryFragRepository repository) {
    this.repository = repository;
  }

  public StoryFragment save(StoryFragment fragment) {
    return this.repository.save(fragment);
  }

  public StoryFragment findById(Long id) {
    return this.repository.findOne(id);
  }

  public List<StoryFragment> findAll() {
    return this.repository.findAll();
  }

  public List<StoryFragment> findByStoryProposition(Long id) {
    return this.repository.findByStoryProposition_IdOrderByPubDate(id);
  }

}
