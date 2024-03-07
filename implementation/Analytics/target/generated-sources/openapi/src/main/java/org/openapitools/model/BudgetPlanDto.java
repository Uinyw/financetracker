package org.openapitools.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import org.openapitools.model.BudgetAchievementStatusDto;
import org.openapitools.model.BudgetElementDto;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * BudgetPlanDto
 */
@lombok.Builder @lombok.AllArgsConstructor @lombok.NoArgsConstructor

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-03-05T07:47:16.544140400+01:00[Europe/Berlin]")
public class BudgetPlanDto {

  private UUID id;

  private BudgetAchievementStatusDto achievementStatus;

  @Valid
  private List<@Valid BudgetElementDto> plan;

  private String date;

  public BudgetPlanDto id(UUID id) {
    this.id = id;
    return this;
  }

  /**
   * Get id
   * @return id
  */
  @Valid 
  @Schema(name = "id", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("id")
  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public BudgetPlanDto achievementStatus(BudgetAchievementStatusDto achievementStatus) {
    this.achievementStatus = achievementStatus;
    return this;
  }

  /**
   * Get achievementStatus
   * @return achievementStatus
  */
  @Valid 
  @Schema(name = "achievementStatus", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("achievementStatus")
  public BudgetAchievementStatusDto getAchievementStatus() {
    return achievementStatus;
  }

  public void setAchievementStatus(BudgetAchievementStatusDto achievementStatus) {
    this.achievementStatus = achievementStatus;
  }

  public BudgetPlanDto plan(List<@Valid BudgetElementDto> plan) {
    this.plan = plan;
    return this;
  }

  public BudgetPlanDto addPlanItem(BudgetElementDto planItem) {
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
  public List<@Valid BudgetElementDto> getPlan() {
    return plan;
  }

  public void setPlan(List<@Valid BudgetElementDto> plan) {
    this.plan = plan;
  }

  public BudgetPlanDto date(String date) {
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
    BudgetPlanDto budgetPlanDto = (BudgetPlanDto) o;
    return Objects.equals(this.id, budgetPlanDto.id) &&
        Objects.equals(this.achievementStatus, budgetPlanDto.achievementStatus) &&
        Objects.equals(this.plan, budgetPlanDto.plan) &&
        Objects.equals(this.date, budgetPlanDto.date);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, achievementStatus, plan, date);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class BudgetPlanDto {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    achievementStatus: ").append(toIndentedString(achievementStatus)).append("\n");
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

