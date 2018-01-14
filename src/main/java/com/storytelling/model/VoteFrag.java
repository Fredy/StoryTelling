package com.storytelling.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "vote_frag")
public class VoteFrag extends Vote {

  @ManyToOne
  @JoinColumn(name = "story_frag_id")
  private StoryFragment storyFragment;

  public StoryFragment getStoryFragment() {
    return storyFragment;
  }

  public void setStoryFragment(StoryFragment storyFragment) {
    this.storyFragment = storyFragment;
  }
}
