package com.softserve.itacademy.mapper;

import com.softserve.itacademy.dto.todo.ToDoCreateForm;
import com.softserve.itacademy.model.ToDo;
import com.softserve.itacademy.model.User;
import org.springframework.stereotype.Component;

@Component
public class ToDoMapper {

    public ToDo convertTodoCreateFormToEntity(ToDoCreateForm toDoCreateForm, User owner) {

        ToDo toDo = new ToDo();
        toDo.setTitle(toDoCreateForm.getTitle());
        toDo.setOwner(owner);

        return toDo;
    }
}
