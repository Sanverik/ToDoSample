package com.softserve.itacademy.controller;

import com.softserve.itacademy.dto.user.UserCreateForm;
import com.softserve.itacademy.dto.user.UserInfoForm;
import com.softserve.itacademy.dto.user.UserUpdateForm;
import com.softserve.itacademy.mapper.UserMapper;
import com.softserve.itacademy.model.Role;
import com.softserve.itacademy.model.User;
import com.softserve.itacademy.service.RoleService;
import com.softserve.itacademy.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;
    private final RoleService roleService;
    private final UserMapper userMapper;

    @GetMapping("/create")
    public String create(Model model) {

        model.addAttribute("user", new UserCreateForm());
        return "create-user";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute("user") @Valid UserCreateForm userCreateForm,
                         BindingResult bindingResult) {

        // confirm password check
        if (!userCreateForm.getPassword().equals(userCreateForm.getConfirmPassword())) {
            bindingResult.addError(new FieldError("equalPasswords", "confirmPassword", "Passwords dont match"));
        }

        if (bindingResult.hasErrors()) {
            return "create-user";
        }

        // register user
        Role defaultRole = roleService.readById(2L);
        User user = userMapper.convertUserCreateFormToEntity(userCreateForm, defaultRole);
        userService.create(user);

        return "redirect:/todos/all/users/" + user.getId();
    }


    @GetMapping("/{id}/read")
    public String read(@PathVariable("id") long id,
                       Model model) {

        User user = userService.readById(id);

        model.addAttribute("user", user);

        return "user-info";
    }

    @GetMapping("/{id}/update")
    public String update(@PathVariable("id") long id,
                         Model model) {

        User user = userService.readById(id);
        model.addAttribute("user", userMapper.convertEntityToUserUpdateForm(user));

        List<Role> allRoles = roleService.getAll();
        model.addAttribute("roles", allRoles);

        return "update-user";
    }

    @PostMapping("/{id}/update")
    public String update(@PathVariable("id") long id,
                         @ModelAttribute("user") @Valid UserUpdateForm userUpdateForm,
                         BindingResult bindingResult,
                         Model model) {

        // old password check
        String userPassword = userService.readPassword(userUpdateForm.getId());
        if (!userPassword.equals(userUpdateForm.getOldPassword())) {
            bindingResult.addError(new FieldError("checkPasswords", "oldPassword", "Invalid user password!"));
        }

        if (bindingResult.hasErrors()) {
            List<Role> allRoles = roleService.getAll();
            model.addAttribute("roles", allRoles);
            return "update-user";
        }

        long roleId = userUpdateForm.getRoleId();
        Role userRole = roleService.readById(roleId);
        User user = userMapper.convertUserUpdateFormToEntity(userUpdateForm, userRole);

        userService.update(user);

        return "redirect:/home";
    }


    @GetMapping("/{id}/delete")
    public String delete(@PathVariable("id") long id) {

        userService.delete(id);

        return "redirect:/home";
    }

    @GetMapping("/all")
    public String getAll(Model model) {

        List<User> users = userService.getAll();

        List<UserInfoForm> usersList = users.stream()
                .map(userMapper::convertEntityToUserInfoForm)
                .collect(Collectors.toList());

        model.addAttribute("users", usersList);

        return "users-list";
    }
}
