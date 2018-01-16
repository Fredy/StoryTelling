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
@Table(name = "story_proposition")
public class StoryProposition {

  @Id
  @GeneratedValue(generator = "native")
  @GenericGenerator(name = "native", strategy = "native")
  private Long id;

  @ManyToOne
  @JoinColumn(name = "user_id")
  @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
  @JsonIdentityReference(alwaysAsId = true)
  private User user;

  @OneToMany(mappedBy = "storyProposition")
  private List<StoryFragment> fragmentList;

  @OneToMany(mappedBy = "storyProposition")
  private List<VoteProp> voteList;

  @Column(name = "prop_text", length = 300)
  private String propText;

  @Column(name = "pub_date")
  @Temporal(TemporalType.TIMESTAMP)
  private Date pubDate;

  public StoryProposition() {
    this.fragmentList = new ArrayList<>();
    this.voteList = new ArrayList<>();
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getPropText() {
    return propText;
  }

  public void setPropText(String propText) {
    this.propText = propText;
  }

  public Date getPubDate() {
    return pubDate;
  }

  public void setPubDate(Date pubDate) {
    this.pubDate = pubDate;
  }

  public List<StoryFragment> getFragmentList() {
    return fragmentList;
  }

  public void setFragmentList(List<StoryFragment> fragmentList) {
    this.fragmentList = fragmentList;
  }

  public List<VoteProp> getVoteList() {
    return voteList;
  }

  public void setVoteList(List<VoteProp> voteList) {
    this.voteList = voteList;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }
}
