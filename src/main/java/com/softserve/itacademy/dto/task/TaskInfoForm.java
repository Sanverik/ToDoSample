package com.softserve.itacademy.dto.task;

import lombok.Data;

@Data
public class TaskInfoForm {

    private long id;

    private String name;

    private String priority;

    private String state;
}
