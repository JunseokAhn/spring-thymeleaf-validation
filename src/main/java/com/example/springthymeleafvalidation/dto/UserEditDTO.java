package com.example.springthymeleafvalidation.dto;

import com.example.springthymeleafvalidation.domain.ClassType;
import com.example.springthymeleafvalidation.domain.StudyType;
import com.example.springthymeleafvalidation.domain.User;
import jakarta.annotation.Nullable;
import lombok.Data;

import java.util.List;

@Data
public class UserEditDTO {
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

    public User toEntity() {
        return new User(id, grade, age, name, email, tel, majorId, classType, studyTypes);
    }
}
