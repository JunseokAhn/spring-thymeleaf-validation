package com.example.springthymeleafvalidation;

import com.example.springthymeleafvalidation.domain.ClassType;
import com.example.springthymeleafvalidation.domain.Major;
import com.example.springthymeleafvalidation.domain.StudyType;
import com.example.springthymeleafvalidation.domain.User;
import com.example.springthymeleafvalidation.dto.UserEditDTO;
import com.example.springthymeleafvalidation.dto.UserSaveDTO;
import com.example.springthymeleafvalidation.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;


@Slf4j
@Controller
@RequiredArgsConstructor
public class ResourceController {

    private final SingleRepository repository;
    //화면별 유효성검증 분리에 따라 제거
    //private final UserDirectValidator userDirectValidator;

    //Bean Validator 도입에 따라 제거
    //private final UserSaveDirectValidator userSaveDirectValidator;
    //private final UserEditDirectValidator userEditDirectValidator;

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

    //Bean Validator에 의해 BindingResult에 Error가 생성되고, 페이지에 검증오류값을 전달 ( status : 200 )
    @PostMapping("/user/form")
    public String saveUser(@Validated @ModelAttribute("user") UserSaveDTO userDTO, BindingResult bindingResult) {

        //Bean Validator 사용에 따라 제거
        //userSaveDirectValidator.validate(userDTO, bindingResult);
        if(bindingResult.hasErrors()){
            log.info("errors = {}", bindingResult);
            return "userSaveForm";
        }
        User user = repository.save(userDTO);
        return "redirect:/user/list";
    }
    @GetMapping("/user/list")
    public String toUserListView(Model model) {
        model.addAttribute("users", repository.getUsers());
        return "userListView";
    }

    @GetMapping("/user/edit")
    public String toUserEdit(Model model) {
        model.addAttribute("user", new UserEditDTO());
        return "userEditForm";
    }
    //Bean Validator에 의해 BindingResult에 Error가 생성되고, 페이지에 검증오류값을 전달 ( status : 200 )
    @PutMapping("/user/edit")
    public String updateUser(@Validated @ModelAttribute("user") UserEditDTO userDTO, BindingResult bindingResult) {

        //Bean Validator 사용에 따라 제거
        //userEditDirectValidator.validate(userDTO, bindingResult);
        if(bindingResult.hasErrors()){
            log.info("errors = {}", bindingResult);
            return "userEditForm";
        }
        repository.overWrite(userDTO);
        return "redirect:/user/list";
    }

    //BasicErrorController에 의해 4xx.html페이지로 리다이렉트 ( status : 404 )
    @GetMapping("/error/notfound")
    public String toNotFoundPage(){
        return "redirect:/notfoundpage";
    }


    //BasicErrorController에 의해 5xx.html페이지로 리다이렉트 ( status : 500 )
    @SneakyThrows
    @GetMapping("/error/exception")
    public void throwException(){
        throw new CustomException("throw new CustomException");
    }


    @ModelAttribute("majors")
    public List<Major> lectures() {
        return repository.getMajors();
    }

    @ModelAttribute("classTypes")
    public ClassType[] classTypes() {
        return ClassType.values();
    }

    @ModelAttribute("studyTypes")
    public StudyType[] studyTypes() {
        return StudyType.values();
    }

}
