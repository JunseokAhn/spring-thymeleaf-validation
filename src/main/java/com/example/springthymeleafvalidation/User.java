package com.example.springthymeleafvalidation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private String id;
    private int grade =1;
    private int age;
    private String name;
    private String email;
    private String tel;
    private String majorId;
    private List<ClassType> classTypes;
    private List<StudyType> studyTypes;
}
