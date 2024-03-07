package org.openapitools.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * NutritionDto
 */
@lombok.Builder @lombok.AllArgsConstructor @lombok.NoArgsConstructor

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-03-05T07:47:16.544140400+01:00[Europe/Berlin]")
public class NutritionDto {

  private Double servingSize;

  private Double calories;

  private Double carbohydrates;

  private Double protein;

  private Double fat;

  private Double sugar;

  public NutritionDto servingSize(Double servingSize) {
    this.servingSize = servingSize;
    return this;
  }

  /**
   * Get servingSize
   * @return servingSize
  */
  
  @Schema(name = "servingSize", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("servingSize")
  public Double getServingSize() {
    return servingSize;
  }

  public void setServingSize(Double servingSize) {
    this.servingSize = servingSize;
  }

  public NutritionDto calories(Double calories) {
    this.calories = calories;
    return this;
  }

  /**
   * Get calories
   * @return calories
  */
  
  @Schema(name = "calories", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("calories")
  public Double getCalories() {
    return calories;
  }

  public void setCalories(Double calories) {
    this.calories = calories;
  }

  public NutritionDto carbohydrates(Double carbohydrates) {
    this.carbohydrates = carbohydrates;
    return this;
  }

  /**
   * Get carbohydrates
   * @return carbohydrates
  */
  
  @Schema(name = "carbohydrates", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("carbohydrates")
  public Double getCarbohydrates() {
    return carbohydrates;
  }

  public void setCarbohydrates(Double carbohydrates) {
    this.carbohydrates = carbohydrates;
  }

  public NutritionDto protein(Double protein) {
    this.protein = protein;
    return this;
  }

  /**
   * Get protein
   * @return protein
  */
  
  @Schema(name = "protein", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("protein")
  public Double getProtein() {
    return protein;
  }

  public void setProtein(Double protein) {
    this.protein = protein;
  }

  public NutritionDto fat(Double fat) {
    this.fat = fat;
    return this;
  }

  /**
   * Get fat
   * @return fat
  */
  
  @Schema(name = "fat", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("fat")
  public Double getFat() {
    return fat;
  }

  public void setFat(Double fat) {
    this.fat = fat;
  }

  public NutritionDto sugar(Double sugar) {
    this.sugar = sugar;
    return this;
  }

  /**
   * Get sugar
   * @return sugar
  */
  
  @Schema(name = "sugar", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("sugar")
  public Double getSugar() {
    return sugar;
  }

  public void setSugar(Double sugar) {
    this.sugar = sugar;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    NutritionDto nutritionDto = (NutritionDto) o;
    return Objects.equals(this.servingSize, nutritionDto.servingSize) &&
        Objects.equals(this.calories, nutritionDto.calories) &&
        Objects.equals(this.carbohydrates, nutritionDto.carbohydrates) &&
        Objects.equals(this.protein, nutritionDto.protein) &&
        Objects.equals(this.fat, nutritionDto.fat) &&
        Objects.equals(this.sugar, nutritionDto.sugar);
  }

  @Override
  public int hashCode() {
    return Objects.hash(servingSize, calories, carbohydrates, protein, fat, sugar);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class NutritionDto {\n");
    sb.append("    servingSize: ").append(toIndentedString(servingSize)).append("\n");
    sb.append("    calories: ").append(toIndentedString(calories)).append("\n");
    sb.append("    carbohydrates: ").append(toIndentedString(carbohydrates)).append("\n");
    sb.append("    protein: ").append(toIndentedString(protein)).append("\n");
    sb.append("    fat: ").append(toIndentedString(fat)).append("\n");
    sb.append("    sugar: ").append(toIndentedString(sugar)).append("\n");
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

