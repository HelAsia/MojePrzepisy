package com.moje.przepisy.mojeprzepisy.data.model;

import java.sql.Timestamp;

public class Comment {
  private int commentId;
  private int recipeId;
  private String comment;
  private String authorName;
  private Timestamp createTime;

  public Comment(){

  }

  public int getCommentId() {
    return commentId;
  }

  public int getRecipeId() {
    return recipeId;
  }

  public String getComment() {
    return comment;
  }

  public String getAuthorName() {
    return authorName;
  }

  public void setCommentId(int commentId) {
    this.commentId = commentId;
  }

  public void setRecipeId(int recipeId) {
    this.recipeId = recipeId;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }

  public void setAuthorName(String authorName) {
    this.authorName = authorName;
  }

  public Timestamp getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Timestamp createTime) {
    this.createTime = createTime;
  }
}
