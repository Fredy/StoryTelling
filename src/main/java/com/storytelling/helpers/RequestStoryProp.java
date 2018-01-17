package com.storytelling.helpers;

public class RequestStoryProp {

  private String text;
  private Long userId;

  public RequestStoryProp() {
  }

  public RequestStoryProp(String text) {
    this.text = text;
  }

  public RequestStoryProp(String text, Long userId) {
    this.text = text;
    this.userId = userId;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }
}
