package com.moje.przepisy.mojeprzepisy.data.model;

public class Comment {
  private int commentId;
  private int recipeId;
  private String comment;
  private String authorName;
  private String createdDate;

  public Comment(){

  }

  public Comment(String authorName, String createdDate, String comment){
    this.authorName = authorName;
    this.createdDate = createdDate;
    this.comment = comment;
  }

  public Comment(int recipeId, String comment) {
    this.recipeId = recipeId;
    this.comment = comment;
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

  public String getCreatedDate() {
    return createdDate;
  }

  public void setCreatedDate(String createdDate) {
    this.createdDate = createdDate;
  }
}
