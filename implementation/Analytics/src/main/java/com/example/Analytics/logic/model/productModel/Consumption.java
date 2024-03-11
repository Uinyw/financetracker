package com.example.Analytics.logic.model.productModel;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.time.LocalDate;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class Consumption {
    private LocalDate date;
    private double amount;
}
