package com.example.springthymeleafvalidation.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Major {
    private String id;
    private String name;
    private String teacherName;
}
