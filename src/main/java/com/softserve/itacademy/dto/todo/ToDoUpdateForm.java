package com.softserve.itacademy.dto.todo;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ToDoUpdateForm {

    private long id;

    @NotBlank(message = "The 'title' cannot be empty")
    private String todoTitle;

    private String owner;

    private long ownerId;
}
