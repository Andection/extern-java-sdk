/*
 * Kontur.Extern.Api.Public
 * No description provided (generated by Swagger Codegen https://github.com/swagger-api/swagger-codegen)
 *
 * OpenAPI spec version: v1
 * 
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */


package ru.skbkontur.sdk.extern.service.transport.swagger.model;

import java.util.Objects;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import ru.skbkontur.sdk.extern.service.transport.swagger.model.CertificateDto;

/**
 * CertificateList
 */

public class CertificateList {
  @SerializedName("certificates")
  private List<CertificateDto> certificates = null;

  @SerializedName("total-count")
  private Integer totalCount = null;

  @SerializedName("page-index")
  private Integer pageIndex = null;

  @SerializedName("page-size")
  private Integer pageSize = null;

  public CertificateList certificates(List<CertificateDto> certificates) {
    this.certificates = certificates;
    return this;
  }

  public CertificateList addCertificatesItem(CertificateDto certificatesItem) {
    if (this.certificates == null) {
      this.certificates = new ArrayList<CertificateDto>();
    }
    this.certificates.add(certificatesItem);
    return this;
  }

   /**
   * Get certificates
   * @return certificates
  **/
  @ApiModelProperty(value = "")
  public List<CertificateDto> getCertificates() {
    return certificates;
  }

  public void setCertificates(List<CertificateDto> certificates) {
    this.certificates = certificates;
  }

  public CertificateList totalCount(Integer totalCount) {
    this.totalCount = totalCount;
    return this;
  }

   /**
   * Get totalCount
   * @return totalCount
  **/
  @ApiModelProperty(value = "")
  public Integer getTotalCount() {
    return totalCount;
  }

  public void setTotalCount(Integer totalCount) {
    this.totalCount = totalCount;
  }

  public CertificateList pageIndex(Integer pageIndex) {
    this.pageIndex = pageIndex;
    return this;
  }

   /**
   * Get pageIndex
   * @return pageIndex
  **/
  @ApiModelProperty(value = "")
  public Integer getPageIndex() {
    return pageIndex;
  }

  public void setPageIndex(Integer pageIndex) {
    this.pageIndex = pageIndex;
  }

  public CertificateList pageSize(Integer pageSize) {
    this.pageSize = pageSize;
    return this;
  }

   /**
   * Get pageSize
   * @return pageSize
  **/
  @ApiModelProperty(value = "")
  public Integer getPageSize() {
    return pageSize;
  }

  public void setPageSize(Integer pageSize) {
    this.pageSize = pageSize;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CertificateList certificateList = (CertificateList) o;
    return Objects.equals(this.certificates, certificateList.certificates) &&
        Objects.equals(this.totalCount, certificateList.totalCount) &&
        Objects.equals(this.pageIndex, certificateList.pageIndex) &&
        Objects.equals(this.pageSize, certificateList.pageSize);
  }

  @Override
  public int hashCode() {
    return Objects.hash(certificates, totalCount, pageIndex, pageSize);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CertificateList {\n");
    
    sb.append("    certificates: ").append(toIndentedString(certificates)).append("\n");
    sb.append("    totalCount: ").append(toIndentedString(totalCount)).append("\n");
    sb.append("    pageIndex: ").append(toIndentedString(pageIndex)).append("\n");
    sb.append("    pageSize: ").append(toIndentedString(pageSize)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
  
}

