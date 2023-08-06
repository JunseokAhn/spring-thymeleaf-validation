package com.example.springthymeleafvalidation.dto;

import com.example.springthymeleafvalidation.domain.ClassType;
import com.example.springthymeleafvalidation.domain.StudyType;
import com.example.springthymeleafvalidation.domain.User;
import jakarta.annotation.Nullable;
import lombok.Data;

import java.util.List;

@Data
public class UserSaveDTO {
    private int grade = 1;
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
        User user = new User();
        user.setGrade(this.grade);
        user.setAge(this.age);
        user.setName(this.name);
        user.setEmail(this.email);
        user.setTel(this.tel);
        user.setMajorId(this.majorId);
        user.setClassType(this.classType);
        user.setStudyTypes(this.studyTypes);
        return user;
    }
}
