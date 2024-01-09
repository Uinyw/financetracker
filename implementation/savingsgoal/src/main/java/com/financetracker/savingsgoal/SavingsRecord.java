package com.financetracker.savingsgoal;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.openapitools.model.AchievementStatus;

import java.sql.Date;
import java.time.LocalDate;
import java.util.UUID;

@Builder(builderMethodName = "with")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "savings_record")
@Entity
public class SavingsRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private LocalDate date;

    @Embedded
    @AttributeOverride(name = "amount", column = @Column(name = "amount", precision = 27, scale = 6))
    private MonetaryAmount amount;

    @JsonProperty("achievementStatus")
    private AchievementStatus achievementStatus;

}
