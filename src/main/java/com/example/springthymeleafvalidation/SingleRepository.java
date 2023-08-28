package com.example.springthymeleafvalidation;

import com.example.springthymeleafvalidation.domain.Major;
import com.example.springthymeleafvalidation.domain.User;
import com.example.springthymeleafvalidation.dto.UserEditDTO;
import com.example.springthymeleafvalidation.dto.UserSaveDTO;
import jakarta.annotation.PostConstruct;
import lombok.Data;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Repository
@Data
public class SingleRepository {
    private Map<String, User> userStore = new HashMap<>();
    private List<Major> majors = new ArrayList<>();
    private int userId= 1;

    @PostConstruct
    public void init() {
        majors.add(new Major("1", "강의1", "교수1"));
        majors.add(new Major("2", "강의2", "교수2"));
        majors.add(new Major("3", "강의3", "교수3"));
        majors.add(new Major("4", "강의4", "교수4"));
        majors.add(new Major("5", "강의5", "교수5"));
    }

    public User save(UserSaveDTO userDTO) {
        User user = userDTO.toEntity();
        user.setId("user" + userId++);
        userStore.put(user.getId(), user);
        return user;
    }

    public List<Major> getMajors(){
        return new ArrayList<>(majors);
    }

    public List<User> getUsers(){
        return new ArrayList<>(userStore.values());
    }

    public void overWrite(UserEditDTO userDTO) {
        User user= userDTO.toEntity();
        userStore.put(user.getId(), user);
    }
}
