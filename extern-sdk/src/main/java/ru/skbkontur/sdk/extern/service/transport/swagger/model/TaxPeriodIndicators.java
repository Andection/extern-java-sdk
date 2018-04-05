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
import ru.skbkontur.sdk.extern.service.transport.swagger.model.MerchantTax;

/**
 * TaxPeriodIndicators
 */

public class TaxPeriodIndicators {
  @SerializedName("nal-pumin")
  private String nalPumin = null;

  @SerializedName("oktmo")
  private String oktmo = null;

  @SerializedName("av-pu")
  private String avPu = null;

  @SerializedName("dohod")
  private String dohod = null;

  @SerializedName("rashod")
  private String rashod = null;

  @SerializedName("nal-baza-ubyt")
  private String nalBazaUbyt = null;

  @SerializedName("stavka")
  private String stavka = null;

  @SerializedName("ischisl")
  private String ischisl = null;

  @SerializedName("umen-nal")
  private String umenNal = null;

  @SerializedName("rasch-torg-sbor")
  private MerchantTax raschTorgSbor = null;

  public TaxPeriodIndicators nalPumin(String nalPumin) {
    this.nalPumin = nalPumin;
    return this;
  }

   /**
   * Get nalPumin
   * @return nalPumin
  **/
  @ApiModelProperty(value = "")
  public String getNalPumin() {
    return nalPumin;
  }

  public void setNalPumin(String nalPumin) {
    this.nalPumin = nalPumin;
  }

  public TaxPeriodIndicators oktmo(String oktmo) {
    this.oktmo = oktmo;
    return this;
  }

   /**
   * Get oktmo
   * @return oktmo
  **/
  @ApiModelProperty(value = "")
  public String getOktmo() {
    return oktmo;
  }

  public void setOktmo(String oktmo) {
    this.oktmo = oktmo;
  }

  public TaxPeriodIndicators avPu(String avPu) {
    this.avPu = avPu;
    return this;
  }

   /**
   * Get avPu
   * @return avPu
  **/
  @ApiModelProperty(value = "")
  public String getAvPu() {
    return avPu;
  }

  public void setAvPu(String avPu) {
    this.avPu = avPu;
  }

  public TaxPeriodIndicators dohod(String dohod) {
    this.dohod = dohod;
    return this;
  }

   /**
   * Get dohod
   * @return dohod
  **/
  @ApiModelProperty(value = "")
  public String getDohod() {
    return dohod;
  }

  public void setDohod(String dohod) {
    this.dohod = dohod;
  }

  public TaxPeriodIndicators rashod(String rashod) {
    this.rashod = rashod;
    return this;
  }

   /**
   * Get rashod
   * @return rashod
  **/
  @ApiModelProperty(value = "")
  public String getRashod() {
    return rashod;
  }

  public void setRashod(String rashod) {
    this.rashod = rashod;
  }

  public TaxPeriodIndicators nalBazaUbyt(String nalBazaUbyt) {
    this.nalBazaUbyt = nalBazaUbyt;
    return this;
  }

   /**
   * Get nalBazaUbyt
   * @return nalBazaUbyt
  **/
  @ApiModelProperty(value = "")
  public String getNalBazaUbyt() {
    return nalBazaUbyt;
  }

  public void setNalBazaUbyt(String nalBazaUbyt) {
    this.nalBazaUbyt = nalBazaUbyt;
  }

  public TaxPeriodIndicators stavka(String stavka) {
    this.stavka = stavka;
    return this;
  }

   /**
   * Get stavka
   * @return stavka
  **/
  @ApiModelProperty(value = "")
  public String getStavka() {
    return stavka;
  }

  public void setStavka(String stavka) {
    this.stavka = stavka;
  }

  public TaxPeriodIndicators ischisl(String ischisl) {
    this.ischisl = ischisl;
    return this;
  }

   /**
   * Get ischisl
   * @return ischisl
  **/
  @ApiModelProperty(value = "")
  public String getIschisl() {
    return ischisl;
  }

  public void setIschisl(String ischisl) {
    this.ischisl = ischisl;
  }

  public TaxPeriodIndicators umenNal(String umenNal) {
    this.umenNal = umenNal;
    return this;
  }

   /**
   * Get umenNal
   * @return umenNal
  **/
  @ApiModelProperty(value = "")
  public String getUmenNal() {
    return umenNal;
  }

  public void setUmenNal(String umenNal) {
    this.umenNal = umenNal;
  }

  public TaxPeriodIndicators raschTorgSbor(MerchantTax raschTorgSbor) {
    this.raschTorgSbor = raschTorgSbor;
    return this;
  }

   /**
   * Get raschTorgSbor
   * @return raschTorgSbor
  **/
  @ApiModelProperty(value = "")
  public MerchantTax getRaschTorgSbor() {
    return raschTorgSbor;
  }

  public void setRaschTorgSbor(MerchantTax raschTorgSbor) {
    this.raschTorgSbor = raschTorgSbor;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TaxPeriodIndicators taxPeriodIndicators = (TaxPeriodIndicators) o;
    return Objects.equals(this.nalPumin, taxPeriodIndicators.nalPumin) &&
        Objects.equals(this.oktmo, taxPeriodIndicators.oktmo) &&
        Objects.equals(this.avPu, taxPeriodIndicators.avPu) &&
        Objects.equals(this.dohod, taxPeriodIndicators.dohod) &&
        Objects.equals(this.rashod, taxPeriodIndicators.rashod) &&
        Objects.equals(this.nalBazaUbyt, taxPeriodIndicators.nalBazaUbyt) &&
        Objects.equals(this.stavka, taxPeriodIndicators.stavka) &&
        Objects.equals(this.ischisl, taxPeriodIndicators.ischisl) &&
        Objects.equals(this.umenNal, taxPeriodIndicators.umenNal) &&
        Objects.equals(this.raschTorgSbor, taxPeriodIndicators.raschTorgSbor);
  }

  @Override
  public int hashCode() {
    return Objects.hash(nalPumin, oktmo, avPu, dohod, rashod, nalBazaUbyt, stavka, ischisl, umenNal, raschTorgSbor);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TaxPeriodIndicators {\n");
    
    sb.append("    nalPumin: ").append(toIndentedString(nalPumin)).append("\n");
    sb.append("    oktmo: ").append(toIndentedString(oktmo)).append("\n");
    sb.append("    avPu: ").append(toIndentedString(avPu)).append("\n");
    sb.append("    dohod: ").append(toIndentedString(dohod)).append("\n");
    sb.append("    rashod: ").append(toIndentedString(rashod)).append("\n");
    sb.append("    nalBazaUbyt: ").append(toIndentedString(nalBazaUbyt)).append("\n");
    sb.append("    stavka: ").append(toIndentedString(stavka)).append("\n");
    sb.append("    ischisl: ").append(toIndentedString(ischisl)).append("\n");
    sb.append("    umenNal: ").append(toIndentedString(umenNal)).append("\n");
    sb.append("    raschTorgSbor: ").append(toIndentedString(raschTorgSbor)).append("\n");
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
