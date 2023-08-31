package com.example.springthymeleafvalidation;

import com.example.springthymeleafvalidation.domain.User;
import com.example.springthymeleafvalidation.dto.ResponseDTO;
import com.example.springthymeleafvalidation.dto.UserEditDTO;
import com.example.springthymeleafvalidation.dto.UserSaveDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ApiController {

    private final SingleRepository repository;
    @GetMapping("/user/{id}")
    public ResponseEntity getUser(@PathVariable String id) {
        User user = repository.find(id);
        //유저를 찾으면 200코드 반환
        return new ResponseEntity(new ResponseDTO(EnumStatus.OK, user), OK);
    }

    @GetMapping("/users")
    public ResponseEntity getUsers() {
        List<User> users = repository.findAll();
        Map<String, Object> data= new HashMap<>();
        data.put("users", users);
        data.put("count", users.size());
        //유저를 찾으면 200코드 반환
        return new ResponseEntity(new ResponseDTO(EnumStatus.OK, data), OK);
    }

    @PostMapping("/user")
    public ResponseEntity postUser(@Validated UserSaveDTO userSaveDTO, BindingResult bindingResult) {
        System.out.println(userSaveDTO);
        //유효성검사 실패시 400코드 반환
        if (bindingResult.hasErrors()) {
            return new ResponseEntity(new ResponseDTO(EnumStatus.BAD_REQUEST, getErrors(bindingResult)), BAD_REQUEST);
        }

        User user = repository.save(userSaveDTO.toEntity());

        //생성 실패시 500코드 반환
        if (user == null) {
            return new ResponseEntity(new ResponseDTO(EnumStatus.INTERNAL_SERVER_ERROR, null), INTERNAL_SERVER_ERROR);
        }

        //생성 성공시 201코드 반환
        return new ResponseEntity(new ResponseDTO(EnumStatus.CREATED, user), CREATED);
    }

    @PutMapping("/user")
    public ResponseEntity putUser(@Validated UserEditDTO userDto, BindingResult bindingResult) {

        //유효성검사 실패시 400코드 반환
        if (bindingResult.hasErrors()) {
            return new ResponseEntity(new ResponseDTO(EnumStatus.BAD_REQUEST, getErrors(bindingResult)), BAD_REQUEST);
        }
        //기존 생성된 유저가 없으면, 생성하고 201코드 반환
        if (repository.findNoException(userDto.getId()) == null) {
            User user = repository.save(userDto.toEntity());
            return new ResponseEntity(new ResponseDTO(EnumStatus.NO_CONTENT, user), CREATED);
        }
        //기존 생성된 유저가 있으면, 대체하고 204코드 반환
        User user = repository.put(userDto.toEntity());
        return new ResponseEntity(new ResponseDTO(EnumStatus.SUCCESS, user), OK);
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity deleteUser(@PathVariable String id) {

        //유저를 찾을 수 없으면 404코드 반환
        if (repository.find(id) == null) {
            return new ResponseEntity(new ResponseDTO(EnumStatus.RESOURCE_NOT_FOUND, new JSONObject()), NOT_FOUND);
        }

        //삭제 실패시 500코드 반환
        if (repository.remove(id) == null) {
            return new ResponseEntity(new ResponseDTO(EnumStatus.INTERNAL_SERVER_ERROR, id), INTERNAL_SERVER_ERROR);
        }
        //삭제 성공시 204코드 반환
        return new ResponseEntity(new ResponseDTO(EnumStatus.NO_CONTENT, EnumStatus.SUCCESS), OK);
    }

    private static List<Map<String, String>> getErrors(BindingResult bindingResult) {
        List<Map<String, String>> errors = bindingResult.getFieldErrors().stream()
                .map(error -> {
                    Map<String, String> errorMap = new HashMap<>();
                    errorMap.put("field", error.getField());
                    errorMap.put("message", error.getDefaultMessage());
                    return errorMap;
                })
                .collect(Collectors.toList());
        return errors;
    }


}
