package com.storytelling.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.GenericGenerator;

@MappedSuperclass
public abstract class Vote {

  @Id
  @GeneratedValue(generator = "native")
  @GenericGenerator(name = "native", strategy = "native")
  protected Long id;

  @ManyToOne
  @JoinColumn(name = "user_id")
  @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
  @JsonIdentityReference(alwaysAsId = true)
  protected User user;

  @Column(name = "vote_date")
  @Temporal(TemporalType.TIMESTAMP)
  protected Date voteDate;

  @Column(name = "upvoted")
  protected Boolean upVoted;

  public Vote() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public Date getVoteDate() {
    return voteDate;
  }

  public void setVoteDate(Date voteDate) {
    this.voteDate = voteDate;
  }

  public Boolean getUpVoted() {
    return upVoted;
  }

  public void setUpVoted(Boolean upVoted) {
    this.upVoted = upVoted;
  }
}
