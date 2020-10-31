package com.softserve.itacademy.controller;

import com.softserve.itacademy.dto.user.UserInfoForm;
import com.softserve.itacademy.mapper.UserMapper;
import com.softserve.itacademy.model.User;
import com.softserve.itacademy.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
public class HomeController {

    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping({"/", "home"})
    public String home(Model model) {

        List<User> users = userService.getAll();

        List<UserInfoForm> usersList = users.stream()
                .map(userMapper::convertEntityToUserInfoForm)
                .collect(Collectors.toList());

        model.addAttribute("users", usersList);

        return "home";
    }
}
