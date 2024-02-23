package com.example.Analytics;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;

@Getter
@Entity
public class Category {
    @Id
    private String name;
}
