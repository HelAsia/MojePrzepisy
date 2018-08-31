package com.moje.przepisy.mojeprzepisy.data.model;

import java.sql.Timestamp;

public class Comment {
  private int commentId;
  private int recipeId;
  private String comment;
  private String authorName;
  private Timestamp createdDate;

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

  public Timestamp getCreatedDate() {
    return createdDate;
  }

  public void setCreatedDate(Timestamp createdDate) {
    this.createdDate = createdDate;
  }
}
