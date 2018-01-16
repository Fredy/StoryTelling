package com.storytelling.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "vote_frag")
public class VoteFrag extends Vote {

  @ManyToOne
  @JoinColumn(name = "story_frag_id")
  @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
  @JsonIdentityReference(alwaysAsId = true)
  private StoryFragment storyFragment;

  public StoryFragment getStoryFragment() {
    return storyFragment;
  }

  public void setStoryFragment(StoryFragment storyFragment) {
    this.storyFragment = storyFragment;
  }
}
