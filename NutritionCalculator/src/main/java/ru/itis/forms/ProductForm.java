package ru.itis.forms;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductForm {
    private String name;
    private Float kal;
    private Float fat;
    private Float prot;
    private Float carbon;
    private Float water;

}
