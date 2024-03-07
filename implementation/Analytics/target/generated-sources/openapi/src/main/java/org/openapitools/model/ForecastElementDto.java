package org.openapitools.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.UUID;
import org.openapitools.model.MonetaryAmountDto;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * ForecastElementDto
 */
@lombok.Builder @lombok.AllArgsConstructor @lombok.NoArgsConstructor

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-03-05T07:47:16.544140400+01:00[Europe/Berlin]")
public class ForecastElementDto {

  private UUID bankAccount;

  private MonetaryAmountDto monetaryAmount;

  public ForecastElementDto bankAccount(UUID bankAccount) {
    this.bankAccount = bankAccount;
    return this;
  }

  /**
   * Get bankAccount
   * @return bankAccount
  */
  @Valid 
  @Schema(name = "bankAccount", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("bankAccount")
  public UUID getBankAccount() {
    return bankAccount;
  }

  public void setBankAccount(UUID bankAccount) {
    this.bankAccount = bankAccount;
  }

  public ForecastElementDto monetaryAmount(MonetaryAmountDto monetaryAmount) {
    this.monetaryAmount = monetaryAmount;
    return this;
  }

  /**
   * Get monetaryAmount
   * @return monetaryAmount
  */
  @Valid 
  @Schema(name = "monetaryAmount", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("monetaryAmount")
  public MonetaryAmountDto getMonetaryAmount() {
    return monetaryAmount;
  }

  public void setMonetaryAmount(MonetaryAmountDto monetaryAmount) {
    this.monetaryAmount = monetaryAmount;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ForecastElementDto forecastElementDto = (ForecastElementDto) o;
    return Objects.equals(this.bankAccount, forecastElementDto.bankAccount) &&
        Objects.equals(this.monetaryAmount, forecastElementDto.monetaryAmount);
  }

  @Override
  public int hashCode() {
    return Objects.hash(bankAccount, monetaryAmount);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ForecastElementDto {\n");
    sb.append("    bankAccount: ").append(toIndentedString(bankAccount)).append("\n");
    sb.append("    monetaryAmount: ").append(toIndentedString(monetaryAmount)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

