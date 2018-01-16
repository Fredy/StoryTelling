package com.storytelling.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "vote_prop")
public class VoteProp extends Vote {

  @ManyToOne
  @JoinColumn(name = "story_prop_id")
  @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
  @JsonIdentityReference(alwaysAsId = true)
  private StoryProposition storyProposition;

  public StoryProposition getStoryProposition() {
    return storyProposition;
  }

  public void setStoryProposition(StoryProposition storyProposition) {
    this.storyProposition = storyProposition;
  }
}
