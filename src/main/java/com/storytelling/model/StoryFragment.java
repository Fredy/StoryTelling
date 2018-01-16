package com.storytelling.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "story_fragment")
public class StoryFragment {

  @Id
  @GeneratedValue(generator = "native")
  @GenericGenerator(name = "native", strategy = "native")
  private Long id;

  @ManyToOne
  @JoinColumn(name = "user_id")
  @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
  @JsonIdentityReference(alwaysAsId = true)
  private User user;

  @ManyToOne
  @JoinColumn(name = "story_prop_id")
  @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
  @JsonIdentityReference(alwaysAsId = true)
  private StoryProposition storyProposition;

  @OneToMany(mappedBy = "storyFragment")
  private List<VoteFrag> voteList;

  @Column(name = "frag_text", length = 300)
  private String fragText;

  @Column(name = "pub_date")
  @Temporal(TemporalType.TIMESTAMP)
  private Date pubDate;

  public StoryFragment() {
    this.voteList = new ArrayList<>();
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getFragText() {
    return fragText;
  }

  public void setFragText(String fragText) {
    this.fragText = fragText;
  }

  public Date getPubDate() {
    return pubDate;
  }

  public void setPubDate(Date pubDate) {
    this.pubDate = pubDate;
  }

  public StoryProposition getStoryProposition() {
    return storyProposition;
  }

  public void setStoryProposition(StoryProposition storyProposition) {
    this.storyProposition = storyProposition;
  }

  public List<VoteFrag> getVoteList() {
    return voteList;
  }

  public void setVoteList(List<VoteFrag> voteList) {
    this.voteList = voteList;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }
}
