package com.example.springthymeleafvalidation;

import com.example.springthymeleafvalidation.directvalidator.UserDirectValidator;
import com.example.springthymeleafvalidation.domain.ClassType;
import com.example.springthymeleafvalidation.domain.StudyType;
import com.example.springthymeleafvalidation.domain.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class validationTest {
    private UserDirectValidator userDirectValidator = new UserDirectValidator();
    private MessageSource messageSource = (MessageSource) new AnnotationConfigApplicationContext(AppConfig.class).getBean("messageSource");
    private User user;
    private BindingResult bindingResult;

    @BeforeEach
    void init(){
        List<StudyType> studyTypes= new ArrayList<>();
        studyTypes.add(StudyType.DayShift);
        user = new User("id", 1, 20, "name", "email@email.com", "02-1234-1234", "majorId", ClassType.Online, studyTypes);
        bindingResult = new BeanPropertyBindingResult(user, "user");
    }

    @Test
    void idNullValid() {
        user.setId(null);
        userDirectValidator.validate(user, bindingResult);
        for (ObjectError error : bindingResult.getAllErrors()) {
            String message = messageSource.getMessage(error, Locale.ROOT);
            Assertions.assertThat(message).isEqualTo("필수값입니다.");
        }
    }
    @Test
    void ageNullValid() {
        user.setAge(null);
        userDirectValidator.validate(user, bindingResult);
        for (ObjectError error : bindingResult.getAllErrors()) {
            String message = messageSource.getMessage(error, Locale.ROOT);
            Assertions.assertThat(message).isEqualTo("필수값입니다.");
        }
    }

    @Test
    void ageRangeValid() {
        user.setAge(18);
        userDirectValidator.validate(user, bindingResult);
        for (ObjectError error : bindingResult.getAllErrors()) {
            String message = messageSource.getMessage(error, Locale.ROOT);
            Assertions.assertThat(message).isEqualTo("20세 이상이어야 합니다.");
        }
    }
    @Test
    void nameNullValid() {
        user.setName(null);
        userDirectValidator.validate(user, bindingResult);
        for (ObjectError error : bindingResult.getAllErrors()) {
            String message = messageSource.getMessage(error, Locale.ROOT);
            Assertions.assertThat(message).isEqualTo("필수값입니다.");
        }
    }

    @Test
    void emailTypeValid() {
        user.setEmail("this is an invalid email");
        userDirectValidator.validate(user, bindingResult);
        for (ObjectError error : bindingResult.getAllErrors()) {
            String message = messageSource.getMessage(error, Locale.ROOT);
            Assertions.assertThat(message).isEqualTo("이메일 형식이 올바르지 않습니다.");
        }
    }

    @Test
    void telTypeValid() {
        user.setTel("1234-78910-12345678910");
        userDirectValidator.validate(user, bindingResult);
        for (ObjectError error : bindingResult.getAllErrors()) {
            String message = messageSource.getMessage(error, Locale.ROOT);
            Assertions.assertThat(message).isEqualTo("전화번호 형식이 올바르지 않습니다.");
        }
    }

    @Test
    void majorIdNullValid() {
        user.setMajorId(null);
        userDirectValidator.validate(user, bindingResult);
        for (ObjectError error : bindingResult.getAllErrors()) {
            String message = messageSource.getMessage(error, Locale.ROOT);
            Assertions.assertThat(message).isEqualTo("필수값입니다.");
        }
    }
    @Test
    void classTypeNullValid() {
        user.setClassType(null);
        userDirectValidator.validate(user, bindingResult);
        for (ObjectError error : bindingResult.getAllErrors()) {
            String message = messageSource.getMessage(error, Locale.ROOT);
            Assertions.assertThat(message).isEqualTo("필수값입니다.");
        }
    }
    @Test
    void studyTypeNullValid() {
        user.setStudyTypes(null);
        userDirectValidator.validate(user, bindingResult);
        for (ObjectError error : bindingResult.getAllErrors()) {
            String message = messageSource.getMessage(error, Locale.ROOT);
            Assertions.assertThat(message).isEqualTo("필수값입니다.");
        }
    }

}
