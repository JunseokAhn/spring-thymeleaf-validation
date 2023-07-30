package com.example.springthymeleafvalidation;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@Controller
public class SingleController {
    private Map<String, User> userStore = new HashMap<>();
    private List<Major> majors = new ArrayList<>();

    @GetMapping("/signUp")
    public String signUp(Model model) {
        model.addAttribute("user", new User());
        return "/signUp";
    }

    @PutMapping("/signUp")
//    public String save(@ModelAttribute("user") User user){
    public String save(User user) {
        userStore.put(user.getId(), user);
        return "redirect:/students";
    }


    @GetMapping("/students")
    public String users(Model model) {
        model.addAttribute("users", userStore.values());
        return "students";
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
