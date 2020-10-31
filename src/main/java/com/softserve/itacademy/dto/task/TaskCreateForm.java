package com.softserve.itacademy.dto.task;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class TaskCreateForm {

    @NotBlank(message = "The name cannot be empty")
    private String name;

    @NotNull
    private String priority;
}
