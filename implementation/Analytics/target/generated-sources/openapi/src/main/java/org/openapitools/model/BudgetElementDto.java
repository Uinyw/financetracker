package org.openapitools.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import org.openapitools.model.MonetaryAmountDto;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * BudgetElementDto
 */
@lombok.Builder @lombok.AllArgsConstructor @lombok.NoArgsConstructor

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-03-05T07:47:16.544140400+01:00[Europe/Berlin]")
public class BudgetElementDto {

  private String category;

  private MonetaryAmountDto monetaryAmount;

  public BudgetElementDto category(String category) {
    this.category = category;
    return this;
  }

  /**
   * Get category
   * @return category
  */
  
  @Schema(name = "category", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("category")
  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public BudgetElementDto monetaryAmount(MonetaryAmountDto monetaryAmount) {
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
    BudgetElementDto budgetElementDto = (BudgetElementDto) o;
    return Objects.equals(this.category, budgetElementDto.category) &&
        Objects.equals(this.monetaryAmount, budgetElementDto.monetaryAmount);
  }

  @Override
  public int hashCode() {
    return Objects.hash(category, monetaryAmount);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class BudgetElementDto {\n");
    sb.append("    category: ").append(toIndentedString(category)).append("\n");
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

