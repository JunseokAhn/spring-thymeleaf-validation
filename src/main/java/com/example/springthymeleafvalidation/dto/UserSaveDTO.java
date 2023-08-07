package com.example.springthymeleafvalidation.dto;

import com.example.springthymeleafvalidation.directvalidator.REGEX;
import com.example.springthymeleafvalidation.domain.ClassType;
import com.example.springthymeleafvalidation.domain.StudyType;
import com.example.springthymeleafvalidation.domain.User;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import java.util.List;

import static com.example.springthymeleafvalidation.directvalidator.REGEX.*;

@Data
public class UserSaveDTO {
    private int grade = 1;

    @Min(value = 19)
    @NotNull
    private Integer age;

    @NotEmpty(message = "이것은 디폴트 메시지이며, 디폴트 메시지는 매칭대기열에서 가장 후순위로 밀려난다.")
    private String name;
    @Nullable
    @Email
    private String email;
    @Nullable
    @Pattern(regexp = TEL_REGEX)
    private String tel;

    @NotEmpty
    private String majorId;

    @NotNull
    private ClassType classType;

    @NotNull
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
