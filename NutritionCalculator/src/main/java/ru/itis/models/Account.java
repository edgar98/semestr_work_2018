package ru.itis.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Account {
    private Long id;
    private String username;
    private String passwordHash;
    private String firstname;
    private String lastname;
    private Float weight;
    private Integer height;
    private Integer age;
    private Float activity;
    private Boolean is_woman;
    private List<Product> prefs;
    private Need needs;
    private List<Nutrition> nutritions;
}
