package com.softserve.itacademy.dto.todo;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ToDoCreateForm {

    @NotBlank(message = "The 'title' cannot be empty")
    private String title;

    private long ownerId;
}
