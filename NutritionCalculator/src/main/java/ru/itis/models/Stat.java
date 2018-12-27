package ru.itis.models;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Stat {
    private Long id;
    private Long user_id;
    private Float kal;
    private Float fat;
    private Float prot;
    private Float carbon;
    private Float water;
    private LocalDate date;
}
