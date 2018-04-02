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
 * DocumentSender
 */

public class DocumentSender {
//  @SerializedName("sender-full-name")
  private String senderFullName = null;

  public DocumentSender senderFullName(String senderFullName) {
    this.senderFullName = senderFullName;
    return this;
  }

   /**
   * Get senderFullName
   * @return senderFullName
  **/
  @ApiModelProperty(value = "")
  public String getSenderFullName() {
    return senderFullName;
  }

  public void setSenderFullName(String senderFullName) {
    this.senderFullName = senderFullName;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DocumentSender documentSender = (DocumentSender) o;
    return Objects.equals(this.senderFullName, documentSender.senderFullName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(senderFullName);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DocumentSender {\n");
    
    sb.append("    senderFullName: ").append(toIndentedString(senderFullName)).append("\n");
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

