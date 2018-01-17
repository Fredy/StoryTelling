package com.storytelling.helpers;

public class RequestStoryFrag {

  private String text;
  private Long propId;
  private Long userId;

  public RequestStoryFrag() {
  }

  public RequestStoryFrag(String text, Long propId, Long userId) {
    this.text = text;
    this.propId = propId;
    this.userId = userId;
  }

  public RequestStoryFrag(String text) {
    this.text = text;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public Long getPropId() {
    return propId;
  }

  public void setPropId(Long propId) {
    this.propId = propId;
  }

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }
}
