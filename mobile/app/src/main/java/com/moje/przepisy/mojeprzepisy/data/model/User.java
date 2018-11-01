package com.moje.przepisy.mojeprzepisy.data.model;

public class User {
  private String firstName;
  private String lastName;
  private String login;
  private String password;
  private String email;
  private String userID;
  private int photoId;
  private String userPhoto;
  public int status;

  public User(String login, String password, String dataType){
    if(dataType.equals("loginData")){
      this.login = login;
      this.password = password;
    }
  }

  public User(){

  }

  public User(String data, String dataType){
    if(dataType.equals("userId")){
      this.userID = data;
    }else if(dataType.equals("userPhoto")){
      this.userPhoto = data;
    }
  }

  public User(int status) {
    this.status = status;
  }


  public User(String userID){
    this.userID = userID;
  }



  public User(String firstName, String lastName, String login, String password,
      String email){
    this.firstName = firstName;
    this.lastName = lastName;
    this.login = login;
    this.password = password;
    this.email = email;
  }

  public User(String firstName, String lastName, String login, String password,
      String email, int photoId) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.login = login;
    this.password = password;
    this.email = email;
    this.photoId = photoId;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }
  public String getLastName(){
    return lastName;
  }

  public void setLastName(String lastName){
    this.lastName = lastName;
  }

  public String getLogin(){
    return login;
  }

  public void setLogin(String login){
    this.login = login;
  }

  public String getPassword(){
    return password;
  }

  public void setPassword(String password){
    this.password = password;
  }

  public String getEmail(){
    return email;
  }

  public void setEmail(String email){
    this.email = email;
  }

  public String getUserID(){
    return userID;
  }

  public void setUserID(String userID){
    this.userID = userID;
  }

  public int getPhotoId() {
    return photoId;
  }

  public void setPhotoId(int photoId) {
    this.photoId = photoId;
  }

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }
}
