package org.openapitools.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import org.openapitools.model.DurationDto;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * ReportFilterDto
 */
@lombok.Builder @lombok.AllArgsConstructor @lombok.NoArgsConstructor

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-03-05T07:47:16.544140400+01:00[Europe/Berlin]")
public class ReportFilterDto {

  @Valid
  private List<String> categories;

  @Valid
  private List<UUID> bankAccounts;

  private DurationDto durationDto;

  public ReportFilterDto categories(List<String> categories) {
    this.categories = categories;
    return this;
  }

  public ReportFilterDto addCategoriesItem(String categoriesItem) {
    if (this.categories == null) {
      this.categories = new ArrayList<>();
    }
    this.categories.add(categoriesItem);
    return this;
  }

  /**
   * Get categories
   * @return categories
  */
  
  @Schema(name = "categories", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("categories")
  public List<String> getCategories() {
    return categories;
  }

  public void setCategories(List<String> categories) {
    this.categories = categories;
  }

  public ReportFilterDto bankAccounts(List<UUID> bankAccounts) {
    this.bankAccounts = bankAccounts;
    return this;
  }

  public ReportFilterDto addBankAccountsItem(UUID bankAccountsItem) {
    if (this.bankAccounts == null) {
      this.bankAccounts = new ArrayList<>();
    }
    this.bankAccounts.add(bankAccountsItem);
    return this;
  }

  /**
   * Get bankAccounts
   * @return bankAccounts
  */
  @Valid 
  @Schema(name = "bankAccounts", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("bankAccounts")
  public List<UUID> getBankAccounts() {
    return bankAccounts;
  }

  public void setBankAccounts(List<UUID> bankAccounts) {
    this.bankAccounts = bankAccounts;
  }

  public ReportFilterDto durationDto(DurationDto durationDto) {
    this.durationDto = durationDto;
    return this;
  }

  /**
   * Get durationDto
   * @return durationDto
  */
  @Valid 
  @Schema(name = "DurationDto", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("DurationDto")
  public DurationDto getDurationDto() {
    return durationDto;
  }

  public void setDurationDto(DurationDto durationDto) {
    this.durationDto = durationDto;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ReportFilterDto reportFilterDto = (ReportFilterDto) o;
    return Objects.equals(this.categories, reportFilterDto.categories) &&
        Objects.equals(this.bankAccounts, reportFilterDto.bankAccounts) &&
        Objects.equals(this.durationDto, reportFilterDto.durationDto);
  }

  @Override
  public int hashCode() {
    return Objects.hash(categories, bankAccounts, durationDto);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ReportFilterDto {\n");
    sb.append("    categories: ").append(toIndentedString(categories)).append("\n");
    sb.append("    bankAccounts: ").append(toIndentedString(bankAccounts)).append("\n");
    sb.append("    durationDto: ").append(toIndentedString(durationDto)).append("\n");
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

