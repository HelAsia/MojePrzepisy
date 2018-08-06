package com.moje.przepisy.mojeprzepisy.data.model;

public class Comment {
  public int commentId;
  public int recipeId;
  public String comment;
  public String authorName;

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
}
