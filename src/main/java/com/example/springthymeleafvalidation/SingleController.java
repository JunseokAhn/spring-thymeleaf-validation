package com.example.springthymeleafvalidation;

import com.example.springthymeleafvalidation.directvalidator.UserDirectValidator;
import com.example.springthymeleafvalidation.directvalidator.UserEditDirectValidator;
import com.example.springthymeleafvalidation.directvalidator.UserSaveDirectValidator;
import com.example.springthymeleafvalidation.dto.UserEditDTO;
import com.example.springthymeleafvalidation.dto.UserSaveDTO;
import com.example.springthymeleafvalidation.domain.ClassType;
import com.example.springthymeleafvalidation.domain.Major;
import com.example.springthymeleafvalidation.domain.StudyType;
import com.example.springthymeleafvalidation.domain.User;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@Slf4j
@Controller
@RequiredArgsConstructor
public class SingleController {
    private Map<String, User> userStore = new HashMap<>();
    private List<Major> majors = new ArrayList<>();
    private final UserDirectValidator userDirectValidator;
    private final UserSaveDirectValidator userSaveDirectValidator;
    private final UserEditDirectValidator userEditDirectValidator;
    private int userId= 1;

    //활성화하면 요청이 들어올 떄마다 자동으로 모든 Validator를 실행
//    @InitBinder
//    public void init(WebDataBinder webDataBinder) {
//        webDataBinder.addValidators(userDirectValidator);
//    }

    @GetMapping("/user/form")
    public String toUserForm(Model model) {
        model.addAttribute("user", new UserSaveDTO());
        return "userSaveForm";
    }

    @PostMapping("/user/form")
    public String saveUser(@Validated @ModelAttribute("user") UserSaveDTO userDTO, BindingResult bindingResult) {
        userSaveDirectValidator.validate(userDTO, bindingResult);
        if(bindingResult.hasErrors()){
            log.info("errors = {}", bindingResult);
            return "userSaveForm";
        }

        User user = userDTO.toEntity();
        user.setId("user" + userId++);
        userStore.put(user.getId(), user);
        return "redirect:/user/list";
    }
    @GetMapping("/user/list")
    public String toUserListView(Model model) {
        model.addAttribute("users", userStore.values());
        return "userListView";
    }

    @GetMapping("/user/edit")
    public String toUserEdit(Model model) {
        model.addAttribute("user", new UserEditDTO());
        return "userEditForm";
    }
    @PutMapping("/user/edit")
    public String updateUser(@Validated @ModelAttribute("user") UserEditDTO userDTO, BindingResult bindingResult) {
        userEditDirectValidator.validate(userDTO, bindingResult);
        if(bindingResult.hasErrors()){
            log.info("errors = {}", bindingResult);
            return "userEditForm";
        }
        User user= userDTO.toEntity();
        userStore.put(user.getId(), user);
        return "redirect:/user/list";
    }


    @ModelAttribute("majors")
    public List<Major> lectures() {
        return majors;
    }

    @ModelAttribute("classTypes")
    public ClassType[] classTypes() {
        return ClassType.values();
    }

    @ModelAttribute("studyTypes")
    public StudyType[] studyTypes() {
        return StudyType.values();
    }

    @PostConstruct
    public void init() {
        majors.add(new Major("1", "강의1", "교수1"));
        majors.add(new Major("2", "강의2", "교수2"));
        majors.add(new Major("3", "강의3", "교수3"));
        majors.add(new Major("4", "강의4", "교수4"));
        majors.add(new Major("5", "강의5", "교수5"));
    }

}
