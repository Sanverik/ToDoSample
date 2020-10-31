package com.softserve.itacademy.dto.task;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class TaskUpdateForm {

    private long id;

    @NotBlank(message = "The name cannot be empty")
    private String name;

    private long priorityId;

    private long statusId;
}
