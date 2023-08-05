package com.example.springthymeleafvalidation;

import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private String id;
    private int grade =1;
    private Integer age;
    private String name;
    @Nullable
    private String email;
    @Nullable
    private String tel;
    private String majorId;
    private ClassType classType;
    private List<StudyType> studyTypes;
}
