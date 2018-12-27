package ru.itis.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Nutrition {
    private Long id;
    private Long user_id;
    private Product product;
    private Float amount;
    private LocalDate date;
}
