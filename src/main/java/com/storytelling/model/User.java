package com.storytelling.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "user")
public class User {

  @Id
  @GeneratedValue(generator = "native")
  @GenericGenerator(name = "native", strategy = "native")
  private Long id;

  @OneToMany(mappedBy = "user")
  private List<VoteProp> votePropList;

  @OneToMany(mappedBy = "user")
  private List<VoteFrag> voteFragList;

  @OneToMany(mappedBy = "user")
  private List<StoryProposition> propList;

  @OneToMany(mappedBy = "user")
  private List<StoryFragment> fragList;

  @ManyToMany
  @JoinTable(
      name = "following",
      joinColumns = @JoinColumn(name = "follower_id", referencedColumnName = "id"),
      inverseJoinColumns = @JoinColumn(name = "followed_id", referencedColumnName = "id"))
  private List<User> following;

  @Column(name = "username", unique = true)
  private String username;

  @Column(name = "first_name")
  private String firstName;

  @Column(name = "last_name")
  private String lastName;

  @Column(name = "email")
  private String email;

  @Column(name = "sign_date")
  @Temporal(TemporalType.TIMESTAMP)
  private Date signDate;

  public User() {
    this.voteFragList = new ArrayList<>();
    this.votePropList = new ArrayList<>();
    this.propList = new ArrayList<>();
    this.fragList = new ArrayList<>();
    this.following = new ArrayList<>();
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public Date getSignDate() {
    return signDate;
  }

  public void setSignDate(Date signDate) {
    this.signDate = signDate;
  }

  public List<VoteProp> getVotePropList() {
    return votePropList;
  }

  public void setVotePropList(List<VoteProp> votePropList) {
    this.votePropList = votePropList;
  }

  public List<VoteFrag> getVoteFragList() {
    return voteFragList;
  }

  public void setVoteFragList(List<VoteFrag> voteFragList) {
    this.voteFragList = voteFragList;
  }

  public List<User> getFollowing() {
    return following;
  }

  public void setFollowing(List<User> following) {
    this.following = following;
  }

  public List<StoryProposition> getPropList() {
    return propList;
  }

  public void setPropList(List<StoryProposition> propList) {
    this.propList = propList;
  }

  public List<StoryFragment> getFragList() {
    return fragList;
  }

  public void setFragList(List<StoryFragment> fragList) {
    this.fragList = fragList;
  }
}
