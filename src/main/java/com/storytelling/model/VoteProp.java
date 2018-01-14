package com.storytelling.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "vote_prop")
public class VoteProp extends Vote {

  @ManyToOne
  @JoinColumn(name = "story_prop_id")
  private StoryProposition storyProposition;

  public StoryProposition getStoryProposition() {
    return storyProposition;
  }

  public void setStoryProposition(StoryProposition storyProposition) {
    this.storyProposition = storyProposition;
  }
}
