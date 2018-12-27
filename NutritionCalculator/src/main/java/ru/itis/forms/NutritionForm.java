package ru.itis.forms;

import ru.itis.models.Product;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NutritionForm {
    private Long user_id;
    private Product product;
    private Float amount;
    private LocalDate date;
}
