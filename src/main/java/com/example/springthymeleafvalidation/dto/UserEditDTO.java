package com.example.springthymeleafvalidation.dto;

import com.example.springthymeleafvalidation.directvalidator.REGEX;
import com.example.springthymeleafvalidation.domain.ClassType;
import com.example.springthymeleafvalidation.domain.StudyType;
import com.example.springthymeleafvalidation.domain.User;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.util.List;

@Data
public class UserEditDTO {

    @NotEmpty
    private String id;
    private int grade =1;

    //수정 화면에서는 학과ㆍ나이ㆍ주야구분ㆍ학습형태 제한을 해제함.
    @Nullable
    private Integer age;

    @NotEmpty(message = "이것은 디폴트 메시지이며, 디폴트 메시지는 매칭대기열에서 가장 후순위로 밀려난다.")
    private String name;
    @Nullable
    @Email
    private String email;
    @Nullable
    @Pattern(regexp = REGEX.TEL_REGEX)
    private String tel;
    @Nullable
    private String majorId;
    @Nullable
    private ClassType classType;
    @Nullable
    private List<StudyType> studyTypes;

    public User toEntity() {
        return new User(id, grade, age, name, email, tel, majorId, classType, studyTypes);
    }
}
