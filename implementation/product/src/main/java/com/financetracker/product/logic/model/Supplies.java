package com.financetracker.product.logic.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Table(name = "supplies")
@Entity
public class Supplies extends ProductEntryCollection {

}
