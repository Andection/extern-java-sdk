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

/**
 * GenerateReplyDocumentRequestData
 */

public class GenerateReplyDocumentRequestData {
  @SerializedName("certificate-base64")
  private String certificateBase64 = null;

  public GenerateReplyDocumentRequestData certificateBase64(String certificateBase64) {
    this.certificateBase64 = certificateBase64;
    return this;
  }

   /**
   * Get certificateBase64
   * @return certificateBase64
  **/
  @ApiModelProperty(value = "")
  public String getCertificateBase64() {
    return certificateBase64;
  }

  public void setCertificateBase64(String certificateBase64) {
    this.certificateBase64 = certificateBase64;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    GenerateReplyDocumentRequestData generateReplyDocumentRequestData = (GenerateReplyDocumentRequestData) o;
    return Objects.equals(this.certificateBase64, generateReplyDocumentRequestData.certificateBase64);
  }

  @Override
  public int hashCode() {
    return Objects.hash(certificateBase64);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class GenerateReplyDocumentRequestData {\n");
    
    sb.append("    certificateBase64: ").append(toIndentedString(certificateBase64)).append("\n");
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

