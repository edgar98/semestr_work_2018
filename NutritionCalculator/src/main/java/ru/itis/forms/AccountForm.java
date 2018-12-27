package ru.itis.forms;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountForm {
    private Long id;
    private String firstname;
    private String lastname;
    private Float weight;
    private Integer height;
    private Integer age;
    private Float activity;
    private Boolean is_woman;
}
