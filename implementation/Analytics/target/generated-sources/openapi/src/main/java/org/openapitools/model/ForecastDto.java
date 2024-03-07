package org.openapitools.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.openapitools.model.ForecastElementDto;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * ForecastDto
 */
@lombok.Builder @lombok.AllArgsConstructor @lombok.NoArgsConstructor

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-03-05T07:47:16.544140400+01:00[Europe/Berlin]")
public class ForecastDto {

  @Valid
  private List<@Valid ForecastElementDto> plan;

  private String date;

  public ForecastDto plan(List<@Valid ForecastElementDto> plan) {
    this.plan = plan;
    return this;
  }

  public ForecastDto addPlanItem(ForecastElementDto planItem) {
    if (this.plan == null) {
      this.plan = new ArrayList<>();
    }
    this.plan.add(planItem);
    return this;
  }

  /**
   * Get plan
   * @return plan
  */
  @Valid 
  @Schema(name = "plan", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("plan")
  public List<@Valid ForecastElementDto> getPlan() {
    return plan;
  }

  public void setPlan(List<@Valid ForecastElementDto> plan) {
    this.plan = plan;
  }

  public ForecastDto date(String date) {
    this.date = date;
    return this;
  }

  /**
   * Get date
   * @return date
  */
  
  @Schema(name = "date", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("date")
  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ForecastDto forecastDto = (ForecastDto) o;
    return Objects.equals(this.plan, forecastDto.plan) &&
        Objects.equals(this.date, forecastDto.date);
  }

  @Override
  public int hashCode() {
    return Objects.hash(plan, date);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ForecastDto {\n");
    sb.append("    plan: ").append(toIndentedString(plan)).append("\n");
    sb.append("    date: ").append(toIndentedString(date)).append("\n");
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

