package com.example.springthymeleafvalidation.directvalidator;

import com.example.springthymeleafvalidation.domain.User;
import com.example.springthymeleafvalidation.dto.UserEditDTO;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class UserEditDirectValidator implements Validator {

    private static final String EMAIL_REGEX =
            "^[a-zA-Z0-9_+&*-]+(?:\\." +
                    "[a-zA-Z0-9_+&*-]+)*@" +
                    "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                    "A-Z]{2,7}$";
    private static final String TEL_REGEX = "\\d{2,3}-\\d{3,4}-\\d{4}";
    private static final String ID_REGEX = "^[a-zA-Z0-9]*$";

    @Override
    public boolean supports(Class<?> clazz) {
        return UserEditDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserEditDTO user = (UserEditDTO) target;

        //아이디가 없음
        if (user.getId()==null) {
            errors.rejectValue("id", "required");
        }

        //아이디가 영문과 숫자로 이뤄져있지 않음
        if (user.getId() != null && !validateFromRegex(user.getId(), ID_REGEX)) {
            errors.rejectValue("id", "type");
        }

        /** 수정 화면에서는 학과ㆍ나이ㆍ주야구분ㆍ학습형태 제한을 해제함.

        //학과를 선택하지않았음
        if (!StringUtils.hasText(user.getMajorId())) {
            errors.rejectValue("majorId", "required");
        }

        //19세 미만은 입학불가
        if (user.getAge() != null && user.getAge() < 19) {
            errors.rejectValue("age", "min", new Integer[]{19}, null);
        }
        //주야구분을 입력하지 않았음
        if (ObjectUtils.isEmpty(user.getClassType())) {
            errors.rejectValue("classType", "required");
        }

        //학습형태는 반드시 하나이상 입력해야함
        if (ObjectUtils.isEmpty(user.getStudyTypes())) {
            errors.rejectValue("studyTypes", "required");
        }
         */
        //이메일 형식 오류
        if (user.getEmail() != null && !validateFromRegex(user.getEmail(), EMAIL_REGEX)) {
            errors.rejectValue("email", "type");
        }

        //전화번호 형식 오류
        if (user.getTel() != null && !validateFromRegex(user.getTel(), TEL_REGEX)) {
            errors.rejectValue("tel", "type");
        }

    }

    private boolean validateFromRegex(String target, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(target);
        return matcher.matches();
    }
}
