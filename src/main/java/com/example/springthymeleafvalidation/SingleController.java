package com.example.springthymeleafvalidation;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Slf4j
@Controller
@RequiredArgsConstructor
public class SingleController {
    private Map<String, User> userStore = new HashMap<>();
    private List<Major> majors = new ArrayList<>();
    private final UserValidator userValidator;

    @InitBinder
    public void init(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(userValidator);
    }

    @GetMapping("/user")
    public String signUp(Model model) {
        model.addAttribute("user", new User());
        return "signUpForm";
    }

    @PutMapping("/user")
    public String save(@Validated User user, BindingResult bindingResult) {

        if(bindingResult.hasErrors()){
            log.info("errors = {}", bindingResult);
            return "signUpForm";
        }
        userStore.put(user.getId(), user);
        return "redirect:/users";
    }


    @GetMapping("/users")
    public String users(Model model) {
        model.addAttribute("users", userStore.values());
        return "userListView";
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
