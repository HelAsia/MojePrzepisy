package com.moje.przepisy.mojeprzepisy.data.model;

public class License {
  int licenseId;
  private String licenseName;
  private String licenseAuthor;
  private String licenseDescription;

  public License(String licenseName, String licenseAuthor, String licenseDescription) {
    this.licenseName = licenseName;
    this.licenseAuthor = licenseAuthor;
    this.licenseDescription = licenseDescription;
  }

  public License(int licenseId, String licenseName, String licenseAuthor,
      String licenseDescription) {
    this.licenseId = licenseId;
    this.licenseName = licenseName;
    this.licenseAuthor = licenseAuthor;
    this.licenseDescription = licenseDescription;
  }

  public String getLicenseName() {
    return licenseName;
  }

  public void setLicenseName(String licenseName) {
    this.licenseName = licenseName;
  }

  public String getLicenseAuthor() {
    return licenseAuthor;
  }

  public void setLicenseAuthor(String licenseAuthor) {
    this.licenseAuthor = licenseAuthor;
  }

  public String getLicenseDescription() {
    return licenseDescription;
  }

  public void setLicenseDescription(String licenseDescription) {
    this.licenseDescription = licenseDescription;
  }

  public int getLicenseId() {
    return licenseId;
  }

  public void setLicenseId(int licenseId) {
    this.licenseId = licenseId;
  }
}
