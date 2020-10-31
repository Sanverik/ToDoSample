package com.softserve.itacademy.dto.todo;

import lombok.Data;

@Data
public class ToDoInfoForm {

    private long id;

    private String title;

    private String createdAt;

    private String owner;
}
