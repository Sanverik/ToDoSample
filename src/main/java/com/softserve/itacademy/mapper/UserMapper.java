package com.softserve.itacademy.mapper;

import com.softserve.itacademy.dto.user.UserCreateForm;
import com.softserve.itacademy.dto.user.UserInfoForm;
import com.softserve.itacademy.dto.user.UserUpdateForm;
import com.softserve.itacademy.model.Role;
import com.softserve.itacademy.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserInfoForm convertEntityToUserInfoForm(User user) {

        UserInfoForm userInfoForm = new UserInfoForm();

        userInfoForm.setId(user.getId());
        userInfoForm.setEmail(user.getEmail());
        userInfoForm.setFullName(user.getFirstName() + " " + user.getLastName());
        userInfoForm.setEmail(user.getEmail());

        return userInfoForm;
    }

    public User convertUserCreateFormToEntity(UserCreateForm userCreateForm, Role defaultRole) {

        User user = new User();

        user.setFirstName(userCreateForm.getFirstName());
        user.setLastName(userCreateForm.getLastName());
        user.setEmail(userCreateForm.getEmail());
        user.setPassword(userCreateForm.getPassword());

        // default
        user.setRole(defaultRole);

        return user;
    }


    public UserUpdateForm convertEntityToUserUpdateForm(User user) {

        UserUpdateForm userUpdateForm = new UserUpdateForm();

        userUpdateForm.setId(user.getId());
        userUpdateForm.setFirstName(user.getFirstName());
        userUpdateForm.setLastName(user.getLastName());
        userUpdateForm.setEmail(user.getEmail());
        userUpdateForm.setOldPassword(user.getPassword());
        userUpdateForm.setNewPassword("");
        userUpdateForm.setRoleId(user.getRole().getId());

        return userUpdateForm;
    }

    public User convertUserUpdateFormToEntity(UserUpdateForm userUpdateForm, Role role) {

        User user = new User();

        user.setId(userUpdateForm.getId());
        user.setFirstName(userUpdateForm.getFirstName());
        user.setLastName(userUpdateForm.getLastName());
        user.setEmail(userUpdateForm.getEmail());
        user.setPassword(userUpdateForm.getNewPassword());
        user.setRole(role);

        return user;
    }
}
